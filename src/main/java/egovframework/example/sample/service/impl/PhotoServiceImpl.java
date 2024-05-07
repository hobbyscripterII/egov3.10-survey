package egovframework.example.sample.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.FileUtils;
import egovframework.example.cmmn.Utils;
import egovframework.example.cmmn.Pagination.Criteria;
import egovframework.example.sample.service.PhotoService;
import egovframework.example.sample.service.model.BoardFileInsDto;
import egovframework.example.sample.service.model.PhotoBoardFileNameVo;
import egovframework.example.sample.service.model.PhotoInsNullDto;
import egovframework.example.sample.service.model.PhotoListGetVo;
import egovframework.example.sample.service.model.PhotoSelVo;
import egovframework.example.sample.service.model.PhotoUpdDto;
import egovframework.example.sample.service.model.UpdPhotoBoardFileThumbnailUnFlDto;
import egovframework.example.sample.service.model.getPhotoBoardNullInsertIboardVo;

@Service("photoServiceImpl") // xml 빈 주입 시 value 명시 필수
public class PhotoServiceImpl implements PhotoService {
	private final PhotoMapper photoMapper;
	private final FileUtils fileUtils;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public PhotoServiceImpl(PhotoMapper photoMapper, FileUtils fileUtils) { this.photoMapper = photoMapper; this.fileUtils = fileUtils; }

	@Override
	public void unInsertBoardDeleteTaskScheduler() {
		log.info("unInsertBoardDeleteTaskScheduler Run!");

		photoMapper.getPhotoBoardNullInsertIboard().forEach(vo -> { delPhotoBoard(vo.getIboard()); }); // 메소드 재사용
	}
	
	public int delPhotoBoard(int iboard) {
		List<PhotoBoardFileNameVo> getPhotoBoardFileNameList = getPhotoBoardFileNameList(iboard);
		getPhotoBoardFileNameList.forEach(vo -> { fileUtils.deleteFile(vo.getFileName()); }); // 로컬 파일 삭제
		int delPhotoBoardRows = photoMapper.delPhotoBoard(iboard); // 게시글 삭제

		if(Utils.isNotNull(delPhotoBoardRows)) { return Const.SUCCESS; }
		
		return Const.FAIL;
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
		photoMapper.updPhotoBoardFileThumbnailFl(dto.getThumbnail());
		updPhotoBoardFileThumbnailUnFl(new UpdPhotoBoardFileThumbnailUnFlDto(dto.getIboard(), dto.getThumbnail()));
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
	public List<PhotoBoardFileNameVo> getPhotoBoardFileNameList(int iboard) {
		return photoMapper.getPhotoBoardFileNameList(iboard);
	}

	@Override
	public int updPhotoBoardFileThumbnailFl(int ifile) {
		return photoMapper.updPhotoBoardFileThumbnailFl(ifile);
	}

	@Override
	public int updPhotoBoardFileThumbnailUnFl(UpdPhotoBoardFileThumbnailUnFlDto dto) {
		return photoMapper.updPhotoBoardFileThumbnailUnFl(dto);
	}

	@Override
	public List<getPhotoBoardNullInsertIboardVo> getPhotoBoardNullInsertIboard() {
		return photoMapper.getPhotoBoardNullInsertIboard();
	}
}
