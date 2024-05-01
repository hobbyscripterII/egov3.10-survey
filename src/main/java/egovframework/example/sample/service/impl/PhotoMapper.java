package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.example.sample.service.model.PhotoListGetVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("photoMapper")
public interface PhotoMapper {
	List<PhotoListGetVo> getPhotoBoardList();
}
