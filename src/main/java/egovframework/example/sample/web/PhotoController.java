package egovframework.example.sample.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.FileUtils;
import egovframework.example.cmmn.Pagination;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.PhotoService;
import egovframework.example.sample.service.model.BoardFileInsDto;
import egovframework.example.sample.service.model.PhotoInsNullDto;
import egovframework.example.sample.service.model.PhotoSelVo;
import egovframework.example.sample.service.model.PhotoUpdDto;

@Controller
@RequestMapping("/photo")
public class PhotoController {
	private final PhotoService photoService;
	private final FileUtils fileUtils;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public PhotoController(PhotoService photoService, FileUtils fileUtils) { this.photoService = photoService; this.fileUtils = fileUtils; }
	
	@PostMapping("/fileupload.do")
	@ResponseBody
	private Map<Integer, String> fileUpload(@RequestPart(name = "iboard") int iboard, @RequestPart(name = "files") MultipartFile[] files) throws Exception {
		List<BoardFileInsDto> fileDtoList = new ArrayList(); // 로컬에 저장된 파일 정보를 담기위한 list 생성
		// 반복문으로 로컬에 첨부파일 저장 후 반환된 dto(테이블에 저장할 파일 정보)를 list에 담음
		// 배열 []은 List<>와 달리 stream 사용 불가능
		for (MultipartFile file : files) {
			BoardFileInsDto dto = fileUtils.fileUpload(file); // 파일 업로드 후 해당 파일의 정보를 dto에 담아서 줌
			dto.setIboard(iboard);
			int insPhotoBoardFileRows = photoService.insPhotoBoardFile(dto); // 파일 테이블에 insert
			fileDtoList.add(dto); // 반환 값으로 경로 추출하기 위해 list에 담음
		}
		
		// stream 사용하여 list 순회하면서 dto에 담겨있던 파일명 + 확장자를 문자열로 반환해 map으로 반환함
		// key: BoardFileInsDto::getIfile - file_tbl에 insert 후 반환된 pk 값
		// value: dto.getSavedName() + dto.getExt() - '로컬에 저장된 실제 이미지 파일명 + 확장자' 문자열
		return fileDtoList.stream().collect(Collectors.toMap(BoardFileInsDto::getIfile, dto -> dto.getSavedName() + dto.getExt()));
	}
	
	@GetMapping("/list.do")
	public String getPhotoList(Pagination.Criteria criteria, Model model) {
		Pagination pagination = new Pagination(criteria, photoService.getPhotoBoardListCnt(criteria));
		model.addAttribute("list", photoService.getPhotoBoardList(criteria));
		model.addAttribute("pagination", pagination);
		model.addAttribute("criteria", criteria);
		return "photo/list";
	}
	
	@GetMapping("/view.do")
	public String selPhotoBoard(@RequestParam int iboard, Model model) {
		photoService.updView(iboard);
		PhotoSelVo vo = photoService.selPhotoBoard(iboard);
		model.addAttribute("vo", vo);
		return "photo/read";
	}

	@GetMapping("/write.do")
	public String insPhotoBoard(@RequestParam int category, Model model, HttpServletRequest request) throws RuntimeException {
		// 이미지 업로드 시 해당 게시글의 pk를 참조해서 insert하기 위해 제목, 내용을 '' 빈 값으로 초기화해서 insert 함
		PhotoInsNullDto insNullDto = new PhotoInsNullDto();
		insNullDto.setCategory(category);
		insNullDto.setIuser(getIuser(request)); // 로그인 시 session에 저장했던 회원 pk
		int insPhotoBoardNullRows = photoService.insPhotoBoardNull(insNullDto); // mybatis에서 반환 값으로 게시글 pk를 들고 옴(insNullDto.getIboard())
		
		// 빈 값으로 초기화한 데이터가 insert 되고 반환 값으로 pk를 들고왔다면 아래 로직을 실행함
		if (Utils.isNotNull(insPhotoBoardNullRows)) {
			PhotoUpdDto dto = new PhotoUpdDto();
			dto.setName(getUserName(request)); // 작성자 칸에 로그인한 회원 이름을 보여주기 위해 setter로 값을 초기화함
			dto.setIboard(insNullDto.getIboard()); // 이미지 업로드 시 파일 테이블에 fk로 넣어주기 위함
			model.addAttribute("dto", dto);
			return "photo/write";
		} else {
			throw new RuntimeException();
		}
	}
	
	@GetMapping("/update.do")
	public String updPhotoBoard(@RequestParam int iboard, Model model) {
		model.addAttribute("dto", photoService.selPhotoBoard(iboard));
		return "photo/write";
	}
	
	@PostMapping("/update.do")
	@ResponseBody
	public int updPhotoBoard(@RequestBody PhotoUpdDto dto, HttpServletRequest request) throws Exception {
		log.info("dto.getDelFileMap() = {}", dto.getDelFileMap());
		return 3;
		
//		dto.setIuser(getIuser(request));
//		int updPhotoBoardRows = photoService.updPhotoBoard(dto);
//		
//		if(Utils.isNotNull(updPhotoBoardRows)) {
//			return Const.SUCCESS;
//		}
//		
//		return Const.FAIL;
	}
	
	@PostMapping("/delete.do")
	@ResponseBody
	public int delPhotoBoard(@RequestParam int iboard) {
		return photoService.delPhotoBoard(iboard);
	}

	@PostMapping("/file-delete.do")
	@ResponseBody
	public int delPhotoBoardFile(@RequestParam int iboard, @RequestParam String src) {
		fileUtils.deleteFile(src); // 예외 처리 필요
		
		return photoService.delPhotoBoardFile(iboard) == Const.SUCCESS ? Const.SUCCESS : Const.FAIL;
	}
	
	// 로그인 시 session에 저장했던 회원 이름
	public String getUserName(HttpServletRequest request) { return (String) request.getSession().getAttribute(Const.USER_NAME); }
	
	// 로그인 시 session에 저장했던 회원 pk
	public int getIuser(HttpServletRequest request) { return (int) request.getSession().getAttribute(Const.USER_IUSER); }
}