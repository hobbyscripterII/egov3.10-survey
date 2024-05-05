package egovframework.example.sample.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import egovframework.example.sample.service.model.BoardInsDto;
import egovframework.example.sample.service.model.PhotoInsNullDto;

@Controller
@RequestMapping("/photo")
public class PhotoController {
	private final PhotoService photoService;
	private final FileUtils fileUtils;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public PhotoController(PhotoService photoService, FileUtils fileUtils) {
		this.photoService = photoService;
		this.fileUtils = fileUtils;
	}
	
	@PostMapping("/fileupload.do")
	@ResponseBody
	private List<BoardFileInsDto> fileUpload(@RequestPart(name = "iboard") int iboard, @RequestPart(name = "files") MultipartFile[] files) throws Exception {
		List<BoardFileInsDto> fileDtoList = new ArrayList(); // 로컬에 저장된 파일 정보를 담기위한 list 생성
		// 반복문으로 로컬에 첨부파일 저장 후 반환된 dto(테이블에 저장할 파일 정보)를 list에 담음
		// 배열 []은 List<>와 달리 stream 사용 불가능
		for (MultipartFile file : files) {
			BoardFileInsDto dto = fileUtils.fileUpload(file); // 파일 업로드 후 해당 파일의 정보를 dto에 담아서 줌
			dto.setIboard(iboard);
			fileDtoList.add(dto); // 테이블에 저장하기 위해 list에 담음
		}
		return fileDtoList; // 테이블에 저장하기 위해 해당 List<dto>를 반환
	}
	
	@GetMapping("/list.do")
	public String getPhotoList(Pagination.Criteria criteria, Model model) {
		Pagination pagination = new Pagination(criteria, photoService.getPhotoBoardListCnt(criteria));
		model.addAttribute("list", photoService.getPhotoBoardList(criteria));
		model.addAttribute("pagination", pagination);
		model.addAttribute("criteria", criteria);
		return "photo/list";
	}

	@GetMapping("/write.do")
	public String insBoard(@RequestParam int category, Model model, HttpServletRequest request) throws RuntimeException {
		HttpSession session = request.getSession(); // request에 있는 session을 들고 옴
		String name = (String) session.getAttribute(Const.USER_NAME); // 로그인 시 session에 저장했던 회원 이름
		int iuser = (int) session.getAttribute(Const.USER_IUSER); // 로그인 시 session에 저장했던 회원 pk
		
		// 업로드 시 해당 게시글의 pk를 참조해서 insert하기 위해 제목, 내용을 '' 빈 값으로 초기화해서 넣기 위함
		PhotoInsNullDto insNullDto = new PhotoInsNullDto();
		insNullDto.setCategory(category);
		insNullDto.setIuser(iuser);
		int insPhotoBoardNullRows = photoService.insPhotoBoardNull(insNullDto); // mybatis에서 반환 값으로 게시글 pk를 들고오게 함
		log.info("insNullDto = {}", insNullDto);
		
		// 빈 값으로 초기화한 데이터가 insert 되고 반환 값으로 pk를 들고왔다면 아래 로직을 실행함
		if (Utils.isNotNull(insPhotoBoardNullRows)) {
			BoardInsDto dto = new BoardInsDto();
			dto.setName(name); // 작성자 칸에 로그인한 회원 이름을 보여주기 위해 setter로 값을 초기화함
			model.addAttribute("dto", dto);
			model.addAttribute("iboard", insNullDto.getIboard()); // ${iboard }
			return "photo/write";
		} else {
			throw new RuntimeException();
		}
	}
}