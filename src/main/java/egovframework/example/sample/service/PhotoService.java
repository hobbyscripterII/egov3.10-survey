package egovframework.example.sample.service;

import java.util.List;

import egovframework.example.cmmn.Pagination.Criteria;
import egovframework.example.sample.service.model.PhotoInsNullDto;
import egovframework.example.sample.service.model.PhotoListGetVo;
import egovframework.example.sample.service.model.PhotoSelVo;
import egovframework.example.sample.service.model.PhotoUpdDto;

public interface PhotoService {
	public List<PhotoListGetVo> getPhotoBoardList(Criteria criteria);
	int getPhotoBoardListCnt(Criteria criteria);
	int insPhotoBoardNull(PhotoInsNullDto dto);
	int updPhotoBoard(PhotoUpdDto dto);
	PhotoSelVo selPhotoBoard(int iboard);
}