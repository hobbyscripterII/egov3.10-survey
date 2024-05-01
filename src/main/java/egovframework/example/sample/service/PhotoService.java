package egovframework.example.sample.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.impl.PhotoMapper;

@Service
public class PhotoService {
	private final PhotoMapper photoMapper;
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public PhotoService(PhotoMapper photoMapper) { this.photoMapper = photoMapper; }
	
	public List<PhotoListGetVo> getPhotoBoardList() {
		return photoMapper.getPhotoBoardList();
	}
}
