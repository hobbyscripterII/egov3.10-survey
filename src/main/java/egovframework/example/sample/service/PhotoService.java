package egovframework.example.sample.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.impl.PhotoMapper;
import egovframework.example.sample.service.model.PhotoListGetVo;

public interface PhotoService {
	public List<PhotoListGetVo> getPhotoBoardList();
}