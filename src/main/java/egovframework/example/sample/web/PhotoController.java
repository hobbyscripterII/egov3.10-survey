package egovframework.example.sample.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.cmmn.Pagination;
import egovframework.example.sample.service.PhotoService;
import egovframework.example.sample.service.model.BoardInsDto;

@Controller
@RequestMapping("/photo")
public class PhotoController {
	private final PhotoService photoService;
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public PhotoController(PhotoService photoService) { this.photoService = photoService; }
	
	@GetMapping("/list.do")
	public String getPhotoList(Pagination.Criteria criteria, Model model) {
		Pagination pagination = new Pagination(criteria, photoService.getPhotoBoardListCnt(criteria));
		model.addAttribute("list", photoService.getPhotoBoardList(criteria));
		model.addAttribute("pagination", pagination);
		model.addAttribute("criteria", criteria);
		return "photo/list";
	}
	
	@GetMapping("/write.do")
	public String insBoard(@RequestParam(required = false, defaultValue = "0") int iboard, Model model) {
		model.addAttribute("dto", new BoardInsDto()); // write.jsp를 게시글 작성 / 수정 시 재사용하기 위해 jsp 파일에 새로운 dto를 보냄
		model.addAttribute("iboard", iboard); // 쿼리 스트링으로 게시글의 pk를 보냄('0'이라면 게시글 / '0'이 아니라면 답변글로 인식)
		return "photo/write";
	}
}