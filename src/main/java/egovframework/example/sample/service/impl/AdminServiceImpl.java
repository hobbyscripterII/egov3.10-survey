package egovframework.example.sample.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.FileUtils;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.AdminService;
import egovframework.example.sample.service.model.ResultVo;
import egovframework.example.sample.service.model.admin.AdminSurveyInsDto;
import egovframework.example.sample.service.model.admin.AdminSurveyInsDto.AdminSurveyQuestionInsDto;
import egovframework.example.sample.service.model.admin.AdminSurveyInsDto.AdminSurveyResponseInsDto;
import egovframework.example.sample.service.model.admin.AdminSurveyStatsSelVo.AdminSurveyQuestionStatsGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyListGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyQuestionGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyResponseOptionGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyStatsSelVo;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto.AdminSurveyQuestionUpdDto;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto.AdminSurveyResponseFormatUpdDto;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto.AdminSurveyResponseUpdDto;
import egovframework.example.sample.service.model.board.BoardFileInsDto;


@Service
public class AdminServiceImpl implements AdminService {
	private final AdminMapper adminMapper;
	private final FileUtils fileUtils;
	private Logger log = LoggerFactory.getLogger(getClass());

	public AdminServiceImpl(AdminMapper adminMapper, SurveyMapper surveyMapper, FileUtils fileUtils) {
		this.adminMapper = adminMapper;
		this.fileUtils = fileUtils;
	}
	
	// 리팩토링 작업중(분기문 정리 / 메소드 분리 / 예외처리 및 트랜잭션 공부)
	// 2024-05-22 todolist - 질문 수정 및 질문 옵션 추가 / 수정 / 삭제 작업 진행 ~
	@Override
	public ResultVo updSurvey(AdminSurveyUpdDto dto) {
		try {
			int updSurveyRows = adminMapper.updSurvey(dto); // 설문조사 정보(제목 / 개요 / 날짜 등) 수정

			if (Utils.isNull(updSurveyRows)) {
				throw new NullPointerException();
			} else {
				List<AdminSurveyQuestionGetVo> savedQuestionList = adminMapper.getQuestion(dto.getIsurvey());
				final int QUESTION_LENGTH = dto.getList().size();

				/*
				 * 설문조사 수정 페이지에서 질문을 수정 / 등록 / 삭제한다.
				 * 먼저 삭제된 질문이 있는지 확인한다.
				 * db에는 있으나 클라이언트에서 삭제된 질문은 미리 삭제시킨다.
				 * 이후 for문을 돌려 db에 있던 질문은 수정을.. 새로 추가해서 pk 값이 '0'인 질문은 등록을 진행한다.
				 */

				List<Integer> delQuestionList = savedQuestionList.stream()
																 .map(saved -> saved.getIquestion()) // db에 저장된 질문 pk 추출
																 // map에서 추출한 데이터를 돌리면서 클라이언트 측에서 넘어온 pk가 없을 경우엔 list에 담음(삭제 용도)
																 .filter(savedQuestion -> !dto.getList().stream()
																		 								.anyMatch(questionDto -> questionDto.getIquestion() == savedQuestion))
																 .collect(Collectors.toList()); // list로 변환

				log.info("[알림] 삭제해야 할 질문은 {}입니다.", delQuestionList);
				
				// 클라이언트 측에서 삭제한 질문 삭제 실패 시 예외 처리
				for (Integer iquestion : delQuestionList) {
					int delQuestionRows = adminMapper.delQuestion(iquestion);
					log.info("[알림] 삭제 플래그 = {}, {}번 질문이 삭제되었습니다.", delQuestionRows, iquestion);

					if (Utils.isNull(delQuestionRows)) { // '1' 아니면 예외 던짐
						throw new RuntimeException();
					}
				}

				// 새로 추가된 질문인지 아닌지 여부 판단
				for (int i = 0; i < QUESTION_LENGTH; i++) {
					AdminSurveyQuestionUpdDto questionDto = dto.getList().get(i);

					// 질문 pk가 0이면 새로 추가된 질문
					if (Utils.isNull(questionDto.getIquestion())) {
						log.info("[알림] 새로 추가된 질문입니다. 정보: {}", questionDto);

						// 질문 / 옵션 등록 메소드 호출
						// 한 메소드 안에 역할을 많이 넣는 것은 좋지않음 따라서 메소드 분리
						// upd dto -> ins dto 변환 작업 메소드 호출
						AdminSurveyQuestionInsDto questionInsDto = adminSurveyQuestionUpdDtoToAdminSurveyQuestionInsDto(questionDto);
						insSurveyQuestionTask(dto.getIsurvey(), questionInsDto); // 테이블 등록 / 이미지 업로드 실패 시 예외 던짐
					} else {
						// 질문 pk가 0이 아닐 경우에는 질문 / 질문 옵션 수정 작업

						// 질문에 담겨있는 질문 옵션 pk 리스트
						List<AdminSurveyResponseOptionGetVo> savedResponseOptionList = adminMapper.getResponseOption(questionDto.getIquestion());

						// 질문 수정 전 필요한 값 세팅
						String requiredFl = questionDto.getRequiredFl() == null ? "N" : "Y"; // 필수 문항 컬럼에 들어갈 값
						questionDto.setRequiredFl(requiredFl);

						int updQuestionRows = adminMapper.updQuestion(questionDto); // 질문 수정

						// 질문 수정 완료 시 질문 옵션 추가 / 수정 / 삭제 작업
						if (Utils.isNull(updQuestionRows)) {
							throw new NullPointerException(); // 만약 질문 수정 rows가 1이 아닐 경우 예외 던져서 트랜잭션 처리
						} else {
							final int RESPONSE_LENGTH = questionDto.getList().size();

							List<Integer> delResponseList = savedResponseOptionList.stream()
																					.map(saved -> saved.getIoption()) // db에 저장된 질문 옵션 pk 추출
																					// map에서 추출한 데이터를 돌리면서 클라이언트 측에서 넘어온 pk가 없을 경우엔 list에 담음(삭제 용도)
																					.filter(savedResponse -> !questionDto.getList().stream()
																																   .anyMatch(responseDto -> responseDto.getIoption() != 0 && responseDto.getIoption() == savedResponse))
																					.collect(Collectors.toList()); // list로 변환

							for (Integer ioption : delResponseList) {
								int delResponseRows = adminMapper.delResponse(ioption);

								// 클라이언트 측에서 삭제한 질문 옵션 삭제 실패 시 예외 처리
								if (Utils.isNull(delResponseRows)) { // '1' 아니면 예외 던짐
									throw new RuntimeException();
								}
							}
							
							for(int j = 0; j < RESPONSE_LENGTH; j++) {
								AdminSurveyResponseUpdDto responseDto = questionDto.getList().get(j);
								int ioption = responseDto.getIoption();
								
								// 질문 옵션 pk가 0일 경우에는 질문 옵션 등록
								if(Utils.isNull(ioption)) {
									AdminSurveyResponseInsDto responseInsDto = new AdminSurveyResponseInsDto();
									responseInsDto.setIuser(dto.getIuser());
									responseInsDto.setIquestion(questionDto.getIquestion());
									responseInsDto.setIresponseformat(questionDto.getIresponseformat());
									responseInsDto.setOptionName(responseDto.getOptionName());
									insSurveyQuestionOptionTask(responseInsDto);
									
								// 질문 옵션 pk가 0이 아닐 경우에는 질문 옵션 수정 작업
								} else {
									updResponseTask(j, questionDto); // 실패 시 NPE 던짐
								}
							}
						}
					}
				}
			}

			return new ResultVo(Const.SUCCESS, Const.SURVEY_UPDATE_SUCCESS);
			
			// 실패 시 예외 던지면 여기서 처리함
			// 예외 던지면 트랙잭션 처리하고 사용자한테 보여 줄 문구를 보내줌
		} catch(RuntimeException e) {
			return new ResultVo(Const.FAIL, Const.SURVEY_UPDATE_FAIL); // 클라이언트 측에서 삭제한 질문 삭제 실패 시 여기서 예외 처리함
		} catch(Exception e) {
			return new ResultVo(Const.FAIL, Const.SURVEY_IMAGE_UPLOAD_FAIL); // 이미지 업로드 실패 시 여기서 예외 처리함
		}
	}
	
	private void updResponseTask(int idx, AdminSurveyQuestionUpdDto questionDto) {
		AdminSurveyResponseUpdDto responseDto = questionDto.getList().get(idx);
		int updResponseRows = adminMapper.updResponse(responseDto);

		if(Utils.isNull(updResponseRows)) {
			throw new NullPointerException();
		} else {
			AdminSurveyResponseFormatUpdDto formatUpdDto = new AdminSurveyResponseFormatUpdDto();
			formatUpdDto.setIquestion(questionDto.getIquestion());
			formatUpdDto.setIresponseformat(questionDto.getIresponseformat());
			int updResponseFormatRows = adminMapper.updResponseFormat(formatUpdDto);
			
			if(Utils.isNull(updResponseFormatRows)) { throw new NullPointerException(); }
		}
	}

	@Override
	public ResultVo insSurvey(AdminSurveyInsDto dto) {
		try { insSurveyTask(dto); return new ResultVo(Const.SUCCESS, Const.SURVEY_CREATE_SUCCESS); } 
		catch (NullPointerException e) { return new ResultVo(Const.FAIL, Const.SURVEY_CREATE_FAIL); }
		catch (Exception e) { return new ResultVo(Const.FAIL, Const.SURVEY_IMAGE_UPLOAD_FAIL); }
	}

	private void insSurveyTask(AdminSurveyInsDto dto) throws Exception {
		// 설문조사 테이블에 insert 완료 시 설문조사 pk 참조하는 질문 등록
		if (Utils.isNotNull(adminMapper.insSurvey(dto))) {
			for (int i = 0; i < dto.getList().size(); i++) {
				AdminSurveyQuestionInsDto questionDto = dto.getList().get(i);
				insSurveyQuestionTask(dto.getIsurvey(), questionDto); // 질문 & 질문 옵션 등록 메소드 호출
			}
		} else {
			throw new NullPointerException();
		}
	}

	private void insSurveyQuestionTask(int isurvey, AdminSurveyQuestionInsDto questionDto) throws Exception {
		String requiredFl = questionDto.getRequiredFl() == null ? "N" : "Y"; // 질문 응답 필수 여부 세팅
		questionDto.setRequiredFl(requiredFl);

		// 질문 등록 전 질문에 있는 file(MultipartFile)을 업로드함
		if (!questionDto.getFile().getOriginalFilename().equals("")) {
			BoardFileInsDto boardFileInsDto = fileUtils.fileUpload(questionDto.getFile()); // 예외 던져지면 호출한 메소드에서 처리함
			String savedPath = boardFileInsDto.getSavedName() + boardFileInsDto.getExt();
			questionDto.setImgPath(savedPath);
		}

		questionDto.setIsurvey(isurvey); // pk 참조위해 넣어줌(pk는 insert 시 useGeneratedKeys 사용으로 반환됨)
		int insQuestionRows = adminMapper.insQuestion(questionDto); // 이미지 경로까지 세팅 후 mapper 호출 해 list에 담겨있는 질문 등록

		if (Utils.isNull(insQuestionRows)) {
			throw new NullPointerException(); // 질문 insert 실패 시 예외 던짐
		} else {
			// 질문 테이블에 insert 완료 시 질문 pk 참조하는 질문 옵션 등록
			for (int j = 0; j < questionDto.getList().size(); j++) {
				AdminSurveyResponseInsDto responseDto = questionDto.getList().get(j);
				responseDto.setIquestion(questionDto.getIquestion());
				responseDto.setIresponseformat(questionDto.getIresponseformat());
				
				insSurveyQuestionOptionTask(responseDto); // 아래 메소드 호출
			}
		}
	}
	
	private void insSurveyQuestionOptionTask(AdminSurveyResponseInsDto responseDto) {
		int insResponseRows = adminMapper.insResponse(responseDto);
		if (Utils.isNull(insResponseRows)) { throw new NullPointerException(); }
	}
	
	@Override
	public List<AdminSurveyListGetVo> getSurveyList() {
		return adminMapper.getSurveyList();
	}
	
	@Override
	public AdminSurveyStatsSelVo selSurveyStats(int isurvey) {
		AdminSurveyStatsSelVo vo = adminMapper.selSurveyStats(isurvey); // 설문조사 정보
		List<AdminSurveyQuestionStatsGetVo> questionList = adminMapper.getSurveyQuestionStats(isurvey); // 질문 정보
		vo.setList(questionList);

		// 질문 정보에 질문 응답 정보를 담음
		for (int i = 0; i < vo.getList().size(); i++) {
			AdminSurveyQuestionStatsGetVo questionVo = vo.getList().get(i);
			questionVo.setMultipleResponseList(adminMapper.getMultipleQuestionStats(questionVo.getIquestion()));
			questionVo.setAnswerResponseList(adminMapper.getAnswerResponseStats(questionVo.getIquestion()));
		}

		return vo;
	}
	
	// upd -> ins dto 변환 작업
	public AdminSurveyQuestionInsDto adminSurveyQuestionUpdDtoToAdminSurveyQuestionInsDto(AdminSurveyQuestionUpdDto dto) {
		List<AdminSurveyResponseInsDto> responseInsDtoList = new ArrayList();
		AdminSurveyQuestionInsDto questionInsDto = new AdminSurveyQuestionInsDto();
		questionInsDto.setFile(dto.getFile());
		questionInsDto.setName(dto.getName());
		questionInsDto.setRequiredFl(dto.getRequiredFl());
		questionInsDto.setIresponseformat(dto.getIresponseformat());

		for (int j = 0; j < dto.getList().size(); j++) {
			AdminSurveyResponseUpdDto responseDto = dto.getList().get(j);
			AdminSurveyResponseInsDto responseInsDto = new AdminSurveyResponseInsDto();
			responseInsDto.setOptionName(responseDto.getOptionName());
			responseInsDtoList.add(responseInsDto);
		}

		questionInsDto.setList(responseInsDtoList); // 질문에 질문 옵션 넣음
		
		return questionInsDto; // 변환 작업 세팅 완료 후 반환
	}
	
	@Override
	public ResultVo delSurvey(int isurvey) {
		try {
			if (Utils.isNotNull(adminMapper.delSurvey(isurvey))) { return new ResultVo(Const.FAIL, Const.SURVEY_DELETE_SUCCESS); }
			else { throw new RuntimeException(); }
		} catch (RuntimeException e) {
			return new ResultVo(Const.FAIL, Const.SURVEY_DELETE_FAIL);
		}
	}
}