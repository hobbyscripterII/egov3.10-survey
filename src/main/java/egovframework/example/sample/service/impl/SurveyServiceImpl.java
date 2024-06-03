package egovframework.example.sample.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.SurveyService;
import egovframework.example.sample.service.model.ResultVo;
import egovframework.example.sample.service.model.survey.SurveyListGetVo;
import egovframework.example.sample.service.model.survey.SurveyResponseInsDto;
import egovframework.example.sample.service.model.survey.SurveyResponseInsDto.SurveyQuestionResponseDto;
import egovframework.example.sample.service.model.survey.SurveySelVo;
import egovframework.example.sample.service.model.survey.SurveySelVo.SurveyQuestionGetVo;

@Service
public class SurveyServiceImpl implements SurveyService {
	private final SurveyMapper surveyMapper;
	Logger log = LoggerFactory.getLogger(getClass());
	
	public SurveyServiceImpl(SurveyMapper surveyMapper) { this.surveyMapper = surveyMapper; }

	@Override
	public List<SurveyListGetVo> getSurveyList() {
		return surveyMapper.getSurveyList();
	}

	@Override
	public SurveySelVo selSurvey(int isurvey) {
		SurveySelVo vo = surveyMapper.selSurvey(isurvey); // 설문조사 불러오기
		List<SurveyQuestionGetVo> list = surveyMapper.getSurveyQuestion(isurvey); // 설문조사에 등록된 질문 리스트 갖고 옴

		// 질문 리스트에 해당하는 질문 옵션이 있을 경우 담아 줌
		// item - SurveyQuestionGetVo
		list.forEach(item -> { item.setList(surveyMapper.getResponseOption(item.getIquestion())); });
		vo.setList(list);

		return vo;
	}

	// 2024-05-22 todolist - 설문조사 참여 시 필수 문항 체크 작업 수정 진행 ~
	@Override
	public ResultVo insResponse(SurveyResponseInsDto dto) {
		try {
			final int LENGTH = dto.getList().size();
			
			for (int i = 0; i < LENGTH; i++) {
				SurveyQuestionResponseDto responseDto = dto.getList().get(i);
				int SHORT_MAX_LENGTH = 2;
				boolean requiredFl = responseDto.getRequiredFl().equals("Y");

				// 필수 항목일 경우 아래 로직 실행
				if (Utils.isTrue(requiredFl)) {
					int ioption = responseDto.getIoption();
					int iresponseformat = responseDto.getIresponseformat();
					boolean isShortQuestion = iresponseformat <= SHORT_MAX_LENGTH;
					
					if (isShortQuestion) {
						String responseContents = responseDto.getResponseContents().trim(); // 공백 제거
						if (responseContents.isEmpty()) { throw new NullPointerException(); }
					} else {
						if (Utils.isNull(ioption)) { throw new NullPointerException(); }
					}
				}
			}

			int insResponseRows = surveyMapper.insResponse(dto); // mybatis foreach문으로 돌린 후 반환된 row 값
			
			if (Utils.isNotNull(insResponseRows)) { return new ResultVo(Const.SUCCESS, Const.SURVEY_PARTICIPATE_SUCCESS); }
			else { throw new RuntimeException(); }
		} catch (NullPointerException e) {
			return new ResultVo(Const.FAIL, Const.SURVEY_REQUIRED_QUESTION_ERROR);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ResultVo(Const.FAIL, Const.SURVEY_PARTICIPATE_FAIL);
		}
	}
}