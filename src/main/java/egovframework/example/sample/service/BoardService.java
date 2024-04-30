package egovframework.example.sample.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.FileUtils;
import egovframework.example.cmmn.Pagination;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.impl.BoardMapper;

@Service
public class BoardService {
	private final BoardMapper boardMapper;
	private final FileUtils fileUtils;
	Logger log = LoggerFactory.getLogger(getClass());

	public BoardService(BoardMapper boardMapper, FileUtils fileUtils) {
		this.boardMapper = boardMapper;
		this.fileUtils = fileUtils;
	}

	public void insBoardFile(List<BoardFileInsDto> dto, int iboard) {
		dto.forEach(item -> { item.setIboard(iboard); boardMapper.insBoardFile(item); });
	}

	public List<BoardListGetVo> getBoardList(Pagination.Criteria criteria) {
		List<BoardListGetVo> list = boardMapper.getBoardList(criteria);
		list.forEach(vo -> { vo.setReply(boardMapper.selBoardReply(vo.getIboard())); });
		return list;
	}

	public int getBoardListCnt(Pagination.Criteria criteria) {
		return boardMapper.getBoardListCnt(criteria);
	}

	public BoardSelVo selBoard(int iboard) {
		BoardSelVo vo = boardMapper.selBoard(iboard);
		vo.setFiles(boardMapper.getBoardFile(iboard));
		return vo;
	}

	public int deleteFile(int iboard) {
		int idx = 0;
		List<BoardFileGetVo> list = boardMapper.getBoardFile(iboard);
		for (BoardFileGetVo vo : list) {
			String fullPath = vo.getSavedName() + vo.getExt();
			fileUtils.deleteFile(fullPath);
			idx++;
		}
		return idx;
	}

	public int delBoard(int iboard) {
		try {
			// 첨부파일 리스트 가져오기
			List<BoardFileGetVo> list = boardMapper.getBoardFile(iboard);
			// 게시글에 등록한 첨부파일이 있다면 삭제
			if (Utils.isNotNull(list.size())) {
				list.forEach(item -> { String fullPath = item.getSavedName() + item.getExt(); fileUtils.deleteFile(fullPath); });
			}

			// 해당 iboard 컬럼에 답변글 식별코드가 있는지 확인
			int code = boardMapper.getBoardCode(iboard);

			// 해당 게시글이 답변글일 경우 아래 로직 실행
			if (Utils.isNotNull(code)) {
				// 답변글 여부를 'N'으로 다시 변경
				BoardReplyUpdDto replyUpdDto = new BoardReplyUpdDto();
				replyUpdDto.setIboard(iboard);
				replyUpdDto.setCode(Const.UN_REPLY_CODE);
				replyUpdDto.setReplyFl(Const.UN_REPLY_FL);
				// 예외 처리 필요
				int updBoardReplyFlRows = boardMapper.updBoardReplyFl(replyUpdDto);
				int delBoardReplyRows = boardMapper.delBoardReply(code);
			} else {
				// 답변글이 아닐 경우 해당 게시글에 답변글이 있는지 여부 확인
				// 예외 처리 필요
				int getBoardCodeCntRows = boardMapper.getBoardCodeCnt(iboard);
			}

			// 2. 테이블 정보 삭제
//			int delBoardRows = 0; // 예외 테스트용
			int delBoardRows = boardMapper.delBoard(iboard);

			if (Utils.isNotNull(delBoardRows)) {
				return Const.SUCCESS;
			} else {
				return Const.FAIL;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Const.FAIL;
		}
	}

	public int insBoard(BoardInsDto dto) {
		try {
			String hashedPwd = BCrypt.hashpw(dto.getPwd(), BCrypt.gensalt());

			if (Utils.isNull(hashedPwd)) {
				log.info("패스워드 암호화에 실패했습니다.");
				throw new NullPointerException();
			} else {
				dto.setPwd(hashedPwd);

				// 답변글 등록이라면 해당 코드를 참조하는 게시글의 '답변글 여부' 플래그를 수정
				if (Utils.isNotNull(dto.getCode())) {
					BoardReplyUpdDto replyUpdDto = new BoardReplyUpdDto();
					replyUpdDto.setIboard(dto.getCode());
					replyUpdDto.setReplyFl(Const.REPLY_FL);

					int updBoardReplyFlRows = boardMapper.updBoardReplyFl(replyUpdDto);

					// 답변글 업데이트 실패 시 예외 발생
					if (Utils.isNull(updBoardReplyFlRows)) {
						throw new RuntimeException();
					}
				}

				int insBoardRows = boardMapper.insBoard(dto); // 게시글 테이블 등록

				// 게시글 테이블 등록 실패 시 예외 발생
				if (Utils.isNull(insBoardRows)) {
					log.info("게시글 등록에 실패했습니다.");
					throw new NullPointerException();
				} else {
					// 게시판 테이블에 저장 성공 시
					if (Utils.isNotNull(dto.getFile())) {
						insBoardFile(dto.getFile(), dto.getIboard());

						if (dto.getFile().size() == boardMapper.getBoardFileCnt(dto.getIboard())) {
							return dto.getIboard();
						} else {
							// 예외 처리문 작성
							// 첨부파일 삭제
							int delFileRows = deleteFile(dto.getIboard());

							// 게시판 테이블 및 파일 테이블 데이터 삭제
							int delBoard = boardMapper.delBoard(dto.getIboard());

							// 예외 던지기
							throw new RuntimeException();
						}
					}
					return dto.getIboard(); // 게시글 등록 완료 시 게시글 pk를 반환함
				}
			}
		} catch (NullPointerException e) {
			return Const.NULL_POINTER_EXCEPTION;
		} catch (RuntimeException e) {
			return Const.INTERNAL_SERVER_ERROR;
		}
	}

	public BoardFileSelVo selBoardFile(int ifile) {
		return boardMapper.selBoardFile(ifile);
	}

	public int updBoard(BoardUpdDto dto) {
		// 삭제된 파일이 있다면 삭제 처리
		if (Utils.isNotNull(dto.getDeleteIfileList().size())) {
			dto.getDeleteIfileList().forEach(ifile -> {
				BoardFileSelVo vo = boardMapper.selBoardFile(ifile);

				// 로컬 파일 삭제
				String path = vo.getSavedName() + vo.getExt();

				fileUtils.deleteFile(path);

				// 테이블 정보 삭제
				boardMapper.delBoardFile(ifile);
			});
		}

		// 추가된 파일이 있다면 등록 처리
		// 로컬 파일 등록(controller에서 처리)

		// 파일 테이블 등록
		// 게시글 수정 시 첨부파일 없을 경우 예외 발생 -> 수정 완료
		if (Utils.isNotNull(dto.getFile())) {
			insBoardFile(dto.getFile(), dto.getIboard());
		}

		// 예외처리 필요

		// 게시글 수정 처리
		return boardMapper.updBoard(dto) == Const.SUCCESS ? Const.SUCCESS : Const.FAIL;
	}

	public int chkPwd(BoardChkPwdDto dto) {
		try {
			String hashedPwd = boardMapper.getHashedPwd(dto.getIboard());
			return BCrypt.checkpw(dto.getPwd(), hashedPwd) == true ? Const.SUCCESS : Const.FAIL;
		} catch (Exception e) {
			return Const.FAIL;
		}
	}

	public int updView(int iboard) {
		return boardMapper.updView(iboard);
	}
}
