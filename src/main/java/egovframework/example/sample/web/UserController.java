package egovframework.example.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.sample.service.model.UserSignInDto;

@Controller
@RequestMapping("/user")
public class UserController {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/signup.do")
	public String signUp() {
		return "user/signup";
	}
	
	@GetMapping("/signin.do")
	public String signIn() {
		return "user/signin";
	}
	
	@PostMapping("/signin.do")
	public String signIn(@ModelAttribute UserSignInDto dto) {
		log.info("dto = {}", dto);
		return "user/signin";
	}
	
	@GetMapping("/signout.do")
	public String signOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate(); // 세션 무효화
		return "cmmn/home";
	}
}
