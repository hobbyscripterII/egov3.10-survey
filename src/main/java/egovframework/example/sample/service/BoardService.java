package egovframework.example.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.impl.BoardMapper;

@Service
public class BoardService {
	private final BoardMapper boardMapper;
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	
}
