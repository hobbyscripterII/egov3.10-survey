package egovframework.example.sample.service.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.FileUtils;
import egovframework.example.cmmn.Pagination;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.BoardService;
import egovframework.example.sample.service.model.BoardChkPwdDto;
import egovframework.example.sample.service.model.BoardFileGetVo;
import egovframework.example.sample.service.model.BoardFileInsDto;
import egovframework.example.sample.service.model.BoardFileSelVo;
import egovframework.example.sample.service.model.BoardInsDto;
import egovframework.example.sample.service.model.BoardListGetVo;
import egovframework.example.sample.service.model.BoardReplyUpdDto;
import egovframework.example.sample.service.model.BoardSelVo;
import egovframework.example.sample.service.model.BoardUpdDto;
import egovframework.example.sample.service.model.UpdPhotoBoardFileThumbnailUnFlDto;

@Service
public class BoardServiceImpl implements BoardService {
	private final BoardMapper boardMapper;
	private final FileUtils fileUtils;
	Logger log = LoggerFactory.getLogger(getClass());

	public BoardServiceImpl(BoardMapper boardMapper, FileUtils fileUtils) { this.boardMapper = boardMapper; this.fileUtils = fileUtils; }

	@Override
	public void insBoardFile(List<BoardFileInsDto> dto, int iboard) {
		dto.forEach(item -> { item.setIboard(iboard); boardMapper.insBoardFile(item); });
	}
	
	@Override
	public List<BoardListGetVo> getBoardList(Pagination.Criteria criteria) {
		List<BoardListGetVo> list = boardMapper.getBoardList(criteria);
		list.forEach(vo -> { vo.setReply(boardMapper.selBoardReply(vo.getIboard())); });
		return list;
	}
	
	@Override
	public int getBoardListCnt(Pagination.Criteria criteria) {
		return boardMapper.getBoardListCnt(criteria);
	}
	
	@Override
	public BoardSelVo selBoard(int iboard) {
		BoardSelVo vo = boardMapper.selBoard(iboard);
		vo.setFiles(boardMapper.getBoardFile(iboard));
		return vo;
	}
	
	@Override
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
	
	// 첨부파일 삭제
	private void delBoardFile(List<BoardFileGetVo> list) {
		if (Utils.isNotNull(list.size())) {
			list.forEach(item -> { String fullPath = item.getSavedName() + item.getExt(); fileUtils.deleteFile(fullPath); });
		}
	}
	
	@Override
	public int delBoard(int iboard) {
		try {
			delBoardFile(boardMapper.getBoardFile(iboard)); // 첨부파일 리스트 가져온 후 있다면 첨부파일 삭제
			int code = boardMapper.getBoardCode(iboard); // 해당 게시글이 답변글인지 확인(code가 있다면 답변글임)

			// 해당 게시글이 답변글일 경우 아래 로직 실행
			if (Utils.isNotNull(code)) {
				// 답변글 여부를 'N'으로 다시 변경
				BoardReplyUpdDto replyUpdDto = new BoardReplyUpdDto();
				replyUpdDto.setIboard(iboard);
				replyUpdDto.setCode(Const.UN_REPLY_CODE);
				replyUpdDto.setReplyFl(Const.UN_REPLY_FL);

				// 예외 처리 필요
				int updBoardReplyFlRows = boardMapper.updBoardReplyFl(replyUpdDto); // 답변글 달았던 원래 글의 답변글 여부 플래그를 'N'으로 변경
				int delBoardReplyRows = boardMapper.delBoardReply(code); // 답변글 삭제
			} else {
				// 답변글이 아닐 경우 해당 게시글에 답변글이 있는지 여부 확인
				Integer boardCodeFromIboard = boardMapper.getBoardCodeFromIboard(iboard);
				
				if(Utils.isNotNull(boardCodeFromIboard)) {
					delBoardFile(boardMapper.getBoardFile(boardCodeFromIboard)); // 첨부파일 리스트 가져온 후 있다면 첨부파일 삭제
					int delBoardReplyRows = boardMapper.delBoardReply(iboard); // 답변글 삭제
				}
			}

			// 2. 테이블 정보 삭제
			int delBoardRows = boardMapper.delBoard(iboard);

			if (Utils.isNotNull(delBoardRows)) { return Const.SUCCESS; }
			else { return Const.FAIL; }
		} catch (Exception e) {
			e.printStackTrace();
			return Const.FAIL;
		}
	}
	
	@Override
	public int insBoard(BoardInsDto dto) {
		try {
			// 답변글 등록이라면 해당 코드를 참조하는 게시글의 '답변글 여부' 플래그를 수정
			if (Utils.isNotNull(dto.getCode())) {
				BoardReplyUpdDto replyUpdDto = new BoardReplyUpdDto();
				replyUpdDto.setIboard(dto.getCode());
				replyUpdDto.setReplyFl(Const.REPLY_FL);
				int updBoardReplyFlRows = boardMapper.updBoardReplyFl(replyUpdDto);

				if (Utils.isNull(updBoardReplyFlRows)) { throw new RuntimeException(); } // 답변글 업데이트 실패 시 예외 발생
			}

			int insBoardRows = boardMapper.insBoard(dto); // 게시글 테이블 등록

			// 게시글 테이블 등록 실패 시 예외 발생
			if (Utils.isNull(insBoardRows)) { throw new NullPointerException(); }
			else {
				// 게시판 테이블에 저장 성공 시
				if (Utils.isNotNull(dto.getFile())) {
					insBoardFile(dto.getFile(), dto.getIboard());

					if (dto.getFile().size() == boardMapper.getBoardFileCnt(dto.getIboard())) { return dto.getIboard(); }
					else {
						// 예외 처리문 작성
						int delFileRows = deleteFile(dto.getIboard()); // 첨부파일 삭제
						int delBoard = boardMapper.delBoard(dto.getIboard()); // 게시판 테이블 및 파일 테이블 데이터 삭제
						throw new RuntimeException(); // 예외 던지기
					}
				}
				return dto.getIboard(); // 게시글 등록 완료 시 게시글 pk를 반환함
			}
			
			/*
			String hashedPwd = BCrypt.hashpw(dto.getPwd(), BCrypt.gensalt());

			if (Utils.isNull(hashedPwd)) { throw new NullPointerException(); }
			else {
				dto.setPwd(hashedPwd);
				
				// 답변글 등록이라면 해당 코드를 참조하는 게시글의 '답변글 여부' 플래그를 수정
				if (Utils.isNotNull(dto.getCode())) {
					BoardReplyUpdDto replyUpdDto = new BoardReplyUpdDto();
					replyUpdDto.setIboard(dto.getCode());
					replyUpdDto.setReplyFl(Const.REPLY_FL);
					int updBoardReplyFlRows = boardMapper.updBoardReplyFl(replyUpdDto);

					if (Utils.isNull(updBoardReplyFlRows)) { throw new RuntimeException(); } // 답변글 업데이트 실패 시 예외 발생
				}

				int insBoardRows = boardMapper.insBoard(dto); // 게시글 테이블 등록

				// 게시글 테이블 등록 실패 시 예외 발생
				if (Utils.isNull(insBoardRows)) { throw new NullPointerException(); }
				else {
					// 게시판 테이블에 저장 성공 시
					if (Utils.isNotNull(dto.getFile())) {
						insBoardFile(dto.getFile(), dto.getIboard());

						if (dto.getFile().size() == boardMapper.getBoardFileCnt(dto.getIboard())) { return dto.getIboard(); }
						else {
							// 예외 처리문 작성
							int delFileRows = deleteFile(dto.getIboard()); // 첨부파일 삭제
							int delBoard = boardMapper.delBoard(dto.getIboard()); // 게시판 테이블 및 파일 테이블 데이터 삭제
							throw new RuntimeException(); // 예외 던지기
						}
					}
					return dto.getIboard(); // 게시글 등록 완료 시 게시글 pk를 반환함
				}
			}
			*/
		} catch (NullPointerException e) {
			return Const.NULL_POINTER_EXCEPTION;
		} catch (RuntimeException e) {
			return Const.INTERNAL_SERVER_ERROR;
		}
	}
	
	@Override
	public BoardFileSelVo selBoardFile(int ifile) {
		return boardMapper.selBoardFile(ifile);
	}

	@Override
	public int updBoard(BoardUpdDto dto) {
		// 삭제된 파일이 있다면 삭제 처리
		if (Utils.isNotNull(dto.getDeleteIfileList().size())) {
			dto.getDeleteIfileList().forEach(ifile -> {
				BoardFileSelVo vo = boardMapper.selBoardFile(ifile);
				String path = vo.getSavedName() + vo.getExt();
				fileUtils.deleteFile(path); // 로컬 파일 삭제
				boardMapper.delBoardFile(ifile); // 테이블 정보 삭제
			});
		}

		// 추가된 파일이 있다면 등록 처리
		// 로컬 파일 등록(controller에서 처리)

		// 파일 테이블 등록
		// 게시글 수정 시 첨부파일 없을 경우 예외 발생 -> 수정 완료
		if (Utils.isNotNull(dto.getFile())) { insBoardFile(dto.getFile(), dto.getIboard()); }
		// 예외처리 필요
		// 게시글 수정 처리
		return boardMapper.updBoard(dto) == Const.SUCCESS ? Const.SUCCESS : Const.FAIL;
	}
	
	@Override
	public int chkPwd(BoardChkPwdDto dto) {
		try {
			String hashedPwd = boardMapper.getHashedPwd(dto.getIboard());
			return BCrypt.checkpw(dto.getPwd(), hashedPwd) == true ? Const.SUCCESS : Const.FAIL;
		} catch (Exception e) {
			return Const.FAIL;
		}
	}
	
	@Override
	public int updView(int iboard) {
		return boardMapper.updView(iboard);
	}
}
