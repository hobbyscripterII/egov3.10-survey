package egovframework.example.sample.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
	private final DataSourceTransactionManager txManager;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public PhotoServiceImpl(PhotoMapper photoMapper, FileUtils fileUtils, DataSourceTransactionManager txManager) {
		this.photoMapper = photoMapper;
		this.fileUtils = fileUtils;
		this.txManager = txManager;
	}

	@Override
	public void unInsertBoardDeleteTaskScheduler() {
		log.info("unInsertBoardDeleteTaskScheduler Run!");

		photoMapper.getPhotoBoardNullInsertIboard().forEach(vo -> { delPhotoBoard(vo.getIboard()); }); // 메소드 재사용
	}
	
	@Override
	public int delPhotoBoardFile(int iboard) {
		return photoMapper.delPhotoBoard(iboard);
	}
	
	public int delPhotoBoard(int iboard) {
		List<PhotoBoardFileNameVo> getPhotoBoardFileNameList = getPhotoBoardFileNameList(iboard);
		getPhotoBoardFileNameList.forEach(vo -> { fileUtils.deleteFile(vo.getFileName()); }); // 로컬 파일 삭제
		int delPhotoBoardRows = delPhotoBoardFile(iboard); // 게시글 삭제

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
	public int updPhotoBoard(PhotoUpdDto dto) throws Exception {
	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	TransactionStatus txStatus = txManager.getTransaction(def);
	
		try {
			// 썸네일로 지정한 이미지의 pk를 들고와 썸네일 플래그 컬럼을 'Y'로 업데이트함
			photoMapper.updPhotoBoardFileThumbnailFl(dto.getThumbnail());
			
			// 썸네일로 지정된 이미지를 제외한 해당 게시글의 이미지의 썸네일 플래그 컬럼을 'N'으로 업데이트함
			photoMapper.updPhotoBoardFileThumbnailUnFl(new UpdPhotoBoardFileThumbnailUnFlDto(dto.getIboard(), dto.getThumbnail()));
			
			// 게시글 수정
			int updPhotoBoardRows = photoMapper.updPhotoBoard(dto);
			
			if(Utils.isNull(updPhotoBoardRows)) {
				throw new Exception();
			}
			
			txManager.commit(txStatus);
			
			return Const.SUCCESS;
		} catch(Exception e) {
			txManager.rollback(txStatus);
			return Const.FAIL;
		}
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
	public List<getPhotoBoardNullInsertIboardVo> getPhotoBoardNullInsertIboard() {
		return photoMapper.getPhotoBoardNullInsertIboard();
	}

	@Override
	public int updView(int iboard) {
		return photoMapper.updView(iboard);
	}
}
