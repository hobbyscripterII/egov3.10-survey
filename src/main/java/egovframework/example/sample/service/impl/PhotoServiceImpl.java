package egovframework.example.sample.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.cmmn.Pagination.Criteria;
import egovframework.example.sample.service.PhotoService;
import egovframework.example.sample.service.model.PhotoInsNullDto;
import egovframework.example.sample.service.model.PhotoListGetVo;
import egovframework.example.sample.service.model.PhotoUpdDto;

@Service
public class PhotoServiceImpl implements PhotoService {
	private final PhotoMapper photoMapper;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public PhotoServiceImpl(PhotoMapper photoMapper) {
		this.photoMapper = photoMapper;
	}

	@Override
	public List<PhotoListGetVo> getPhotoBoardList(Criteria criteria) {
		return photoMapper.getPhotoBoardList(criteria);
	}

	@Override
	public int getPhotoBoardListCnt(Criteria criteria) {
		return photoMapper.getPhotoBoardListCnt(criteria);
	}

	@Override
	public int insPhotoBoardNull(PhotoInsNullDto dto) {
		return photoMapper.insPhotoBoardNull(dto);
	}

	@Override
	public int updPhotoBoard(PhotoUpdDto dto) {
		return photoMapper.updPhotoBoard(dto);
	}
}
