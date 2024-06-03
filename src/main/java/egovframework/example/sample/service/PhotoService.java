package egovframework.example.sample.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import egovframework.example.cmmn.Pagination.Criteria;
import egovframework.example.sample.service.model.board.BoardFileInsDto;
import egovframework.example.sample.service.model.photo.PhotoBoardFileNameVo;
import egovframework.example.sample.service.model.photo.PhotoInsNullDto;
import egovframework.example.sample.service.model.photo.PhotoListGetVo;
import egovframework.example.sample.service.model.photo.PhotoSelVo;
import egovframework.example.sample.service.model.photo.PhotoUpdDto;
import egovframework.example.sample.service.model.photo.getPhotoBoardNullInsertIboardVo;

public interface PhotoService {
	public List<PhotoListGetVo> getPhotoBoardList(Criteria criteria);
	int getPhotoBoardListCnt(Criteria criteria);
	int insPhotoBoardNull(PhotoInsNullDto dto);
	int updPhotoBoard(PhotoUpdDto dto) throws Exception;
	PhotoSelVo selPhotoBoard(int iboard);
	int insPhotoBoardFile(BoardFileInsDto dto);
	List<PhotoBoardFileNameVo> getPhotoBoardFileNameList(int iboard);
	int delPhotoBoard(int iboard);
	int delPhotoBoardFile(int iboard);
	public void unInsertBoardDeleteTaskScheduler();
	List<getPhotoBoardNullInsertIboardVo> getPhotoBoardNullInsertIboard();
	int updView(int iboard);
}