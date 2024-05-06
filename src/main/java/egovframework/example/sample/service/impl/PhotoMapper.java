package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.example.cmmn.Pagination.Criteria;
import egovframework.example.sample.service.model.PhotoInsNullDto;
import egovframework.example.sample.service.model.PhotoListGetVo;
import egovframework.example.sample.service.model.PhotoUpdDto;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("photoMapper")
public interface PhotoMapper {
	List<PhotoListGetVo> getPhotoBoardList(Criteria criteria);
	int getPhotoBoardListCnt(Criteria criteria);
	int insPhotoBoardNull(PhotoInsNullDto dto);
	int updPhotoBoard(PhotoUpdDto dto);
}