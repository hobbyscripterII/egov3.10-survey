package egovframework.example.sample.service;

import java.util.List;

import egovframework.example.cmmn.Pagination;
import egovframework.example.sample.service.model.BoardChkPwdDto;
import egovframework.example.sample.service.model.BoardFileInsDto;
import egovframework.example.sample.service.model.BoardFileSelVo;
import egovframework.example.sample.service.model.BoardInsDto;
import egovframework.example.sample.service.model.BoardListGetVo;
import egovframework.example.sample.service.model.BoardSelVo;
import egovframework.example.sample.service.model.BoardUpdDto;

public interface BoardService {
	public void insBoardFile(List<BoardFileInsDto> dto, int iboard);
	public List<BoardListGetVo> getBoardList(Pagination.Criteria criteria);
	public int getBoardListCnt(Pagination.Criteria criteria);
	public BoardSelVo selBoard(int iboard);
	public int deleteFile(int iboard);
	public int delBoard(int iboard);
	public int insBoard(BoardInsDto dto);
	public BoardFileSelVo selBoardFile(int ifile);
	public int updBoard(BoardUpdDto dto);
	public int chkPwd(BoardChkPwdDto dto);
	public int updView(int iboard);
}