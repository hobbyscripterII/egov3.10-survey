package egovframework.example.sample.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.sample.service.PhotoService;

@Controller
@RequestMapping("/photo")
public class PhotoController {
	private final PhotoService photoService;
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public PhotoController(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	@GetMapping("/list.do")
	public String getPhotoList(Model model) {
		model.addAttribute("list", photoService.getPhotoBoardList());
		return "photo/list";
	}
}