package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.example.cmmn.Pagination;
import egovframework.example.sample.service.model.board.BoardFileGetVo;
import egovframework.example.sample.service.model.board.BoardFileInsDto;
import egovframework.example.sample.service.model.board.BoardFileSelVo;
import egovframework.example.sample.service.model.board.BoardInsDto;
import egovframework.example.sample.service.model.board.BoardListGetVo;
import egovframework.example.sample.service.model.board.BoardReplyUpdDto;
import egovframework.example.sample.service.model.board.BoardSelVo;
import egovframework.example.sample.service.model.board.BoardUpdDto;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("boardMapper")
public interface BoardMapper {
	// 게시글 관련
	public List<BoardListGetVo> getBoardList(Pagination.Criteria criteria); // 게시판 가져오기
	public BoardSelVo selBoard(int iboard); // 게시글 상세보기
	int insBoard(BoardInsDto dto); // 게시글 등록
	int updBoard(BoardUpdDto dto); // 게시글 수정
	int delBoard(int iboard); // 게시글 삭제
	public int getBoardListCnt(Pagination.Criteria criteria); // 페이지네이션에 사용될 게시글 총 개수
	
	// 답변글 관련
	public BoardSelVo selBoardReply(int code); // 답변글 상세보기
	int updBoardReplyFl(BoardReplyUpdDto dto); // 답변글 플래그 변경
	int delBoardReply(int code); // 답변글 삭제
	Integer getBoardCodeFromIboard(int code); // 답변글이 참조하는 원래 게시글의 pk 가져오기
	int getBoardCode(int iboard); // 답변글 식별코드 가져오기
	
	// 첨부파일 관련
	int insBoardFile(BoardFileInsDto dto); // 첨부파일 등록
	List<BoardFileGetVo> getBoardFile(int iboard); // 특정 게시글의 첨부파일 가져오기
	BoardFileSelVo selBoardFile(int ifile); // 특정 게시글의 특정 첨부파일 정보 가져오기
	int delBoardFile(int ifile); // 첨부파일 삭제
	int getBoardFileCnt(int iboard); // 첨부파일 개수(예외 처리용)
	
	String getHashedPwd(int iboard); // 해시화된 암호 가져오기

	int updView(int iboard); // 조회수++
	
	int getBoardByIuser(int iboard); // 인터셉터 호출 전용(수정 / 삭제 접근 시 작성한 회원과 비교하기 위함
}
