package egovframework.example.sample.service;

import java.util.List;

import egovframework.example.cmmn.Pagination.Criteria;
import egovframework.example.sample.service.model.BoardFileInsDto;
import egovframework.example.sample.service.model.PhotoBoardFileNameVo;
import egovframework.example.sample.service.model.PhotoInsNullDto;
import egovframework.example.sample.service.model.PhotoListGetVo;
import egovframework.example.sample.service.model.PhotoSelVo;
import egovframework.example.sample.service.model.PhotoUpdDto;
import egovframework.example.sample.service.model.UpdPhotoBoardFileThumbnailUnFlDto;

public interface PhotoService {
	public List<PhotoListGetVo> getPhotoBoardList(Criteria criteria);
	int getPhotoBoardListCnt(Criteria criteria);
	int insPhotoBoardNull(PhotoInsNullDto dto);
	int updPhotoBoard(PhotoUpdDto dto);
	PhotoSelVo selPhotoBoard(int iboard);
	int insPhotoBoardFile(BoardFileInsDto dto);
	List<PhotoBoardFileNameVo> getPhotoBoardFileNameList(int iboard);
	int delPhotoBoard(int iboard);
	int updPhotoBoardFileThumbnailFl(int ifile);
	int updPhotoBoardFileThumbnailUnFl(UpdPhotoBoardFileThumbnailUnFlDto dto);
}