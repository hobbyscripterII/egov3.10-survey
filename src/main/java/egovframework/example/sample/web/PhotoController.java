package egovframework.example.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.Pagination;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.PhotoService;
import egovframework.example.sample.service.model.BoardInsDto;
import egovframework.example.sample.service.model.PhotoInsNullDto;

@Controller
@RequestMapping("/photo")
public class PhotoController {
	private final PhotoService photoService;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public PhotoController(PhotoService photoService) {
		this.photoService = photoService;
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
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute(Const.USER_NAME);
		int iuser = (int) session.getAttribute(Const.USER_IUSER);
		
		log.info("name = {}", name);
		log.info("iuser = {}", iuser);
		
		PhotoInsNullDto insNullDto = new PhotoInsNullDto();
		insNullDto.setCategory(category);
		insNullDto.setIuser(iuser);
		int iboard = photoService.insPhotoBoardNull(insNullDto);
		
		if (Utils.isNotNull(iboard)) {
			BoardInsDto dto = new BoardInsDto();
			dto.setName(name);
			model.addAttribute("dto", dto);
			model.addAttribute("iboard", iboard);
			return "photo/write";
		} else {
			throw new RuntimeException();
		}
	}
}