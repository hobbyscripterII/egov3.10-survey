package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.example.cmmn.Pagination.Criteria;
import egovframework.example.sample.service.model.board.BoardFileInsDto;
import egovframework.example.sample.service.model.photo.PhotoBoardFileNameVo;
import egovframework.example.sample.service.model.photo.PhotoInsNullDto;
import egovframework.example.sample.service.model.photo.PhotoListGetVo;
import egovframework.example.sample.service.model.photo.PhotoSelVo;
import egovframework.example.sample.service.model.photo.PhotoUpdDto;
import egovframework.example.sample.service.model.photo.UpdPhotoBoardFileThumbnailUnFlDto;
import egovframework.example.sample.service.model.photo.getPhotoBoardNullInsertIboardVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("photoMapper")
public interface PhotoMapper {
	List<PhotoListGetVo> getPhotoBoardList(Criteria criteria);
	int getPhotoBoardListCnt(Criteria criteria);
	int insPhotoBoardNull(PhotoInsNullDto dto);
	int updPhotoBoard(PhotoUpdDto dto);
	PhotoSelVo selPhotoBoard(int iboard);
	int insPhotoBoardFile(BoardFileInsDto dto);
	List<PhotoBoardFileNameVo> getPhotoBoardFileNameList(int iboard);
	int delPhotoBoard(int iboard);
	int updPhotoBoardFileThumbnailFl(int ifile);
	int updPhotoBoardFileThumbnailUnFl(UpdPhotoBoardFileThumbnailUnFlDto dto);
	List<getPhotoBoardNullInsertIboardVo> getPhotoBoardNullInsertIboard();
	int updView(int iboard);
}