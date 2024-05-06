package egovframework.example.sample.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.FileUtils;
import egovframework.example.cmmn.Pagination.Criteria;
import egovframework.example.sample.service.PhotoService;
import egovframework.example.sample.service.model.BoardFileInsDto;
import egovframework.example.sample.service.model.PhotoInsNullDto;
import egovframework.example.sample.service.model.PhotoListGetVo;
import egovframework.example.sample.service.model.PhotoSelVo;
import egovframework.example.sample.service.model.PhotoUpdDto;

@Service
public class PhotoServiceImpl implements PhotoService {
	private final PhotoMapper photoMapper;
	private final FileUtils fileUtils;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public PhotoServiceImpl(PhotoMapper photoMapper, FileUtils fileUtils) {
		this.photoMapper = photoMapper;
		this.fileUtils = fileUtils;
	}

	public int delBoard(int iboard) {
		List<String> getPhotoBoardFileNameList = getPhotoBoardFileNameList(iboard);
		log.info("getPhotoBoardFileNameList = {}", getPhotoBoardFileNameList);
		getPhotoBoardFileNameList.forEach(fileName -> {
			fileUtils.deleteFile(fileName);
		});
		
		return Const.SUCCESS;
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

	@Override
	public PhotoSelVo selPhotoBoard(int iboard) {
		return photoMapper.selPhotoBoard(iboard);
	}

	@Override
	public int insPhotoBoardFile(BoardFileInsDto dto) {
		return photoMapper.insPhotoBoardFile(dto);
	}

	@Override
	public List<String> getPhotoBoardFileNameList(int iboard) {
		return photoMapper.getPhotoBoardFileNameList(iboard);
	}
}
