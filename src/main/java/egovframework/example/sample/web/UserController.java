package egovframework.example.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.UserService;
import egovframework.example.sample.service.UserSignUpValidator;
import egovframework.example.sample.service.model.UserDetailVo;
import egovframework.example.sample.service.model.UserSignInDto;
import egovframework.example.sample.service.model.UserSignUpDto;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	Logger log = LoggerFactory.getLogger(getClass());

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/signup.do")
	public String signUp(Model model) {
		model.addAttribute("dto", new UserSignUpDto());
		return "user/signup";
	}

	@PostMapping("/signup.do")
	public String signUp(@ModelAttribute("dto") UserSignUpDto dto, BindingResult bindingResult) {
		log.info("dto = {}", dto);
		
		new UserSignUpValidator().validate(dto, bindingResult);
		if (bindingResult.hasErrors()) {
			return "user/signup";
		} else {
			int signUpRows = userService.signUp(dto);
			log.info("signUpRows = {}", signUpRows);
			return "cmmn/home";
		}
	}

	@GetMapping("/signin.do")
	public String signIn(Model model) {
		model.addAttribute("dto", new UserSignInDto());
		return "user/signin";
	}

	@PostMapping("/signin.do")
	public String signIn(@ModelAttribute("dto") UserSignInDto dto, BindingResult bindingResult, HttpServletRequest request) {
		UserDetailVo userDetail = userService.signIn(dto); // 아이디, 비밀번호 일치 시 회원 테이블의 pk 들고 옴
		log.info("userDetail = {}", userDetail);

		if (Utils.isNotNull(userDetail)) {
			HttpSession session = request.getSession();
			session.setAttribute(Const.IUSER, userDetail.getIuser());
			session.setAttribute(Const.ROLE, userDetail.getRole());
			return "cmmn/home";
		} else {
			bindingResult.addError(new ObjectError("id", "아이디 혹은 비밀번호가 일치하지 않습니다. 확인 후 다시 로그인해주세요."));
			return "user/signin";
		}
	}

	@GetMapping("/signout.do")
	public String signOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate(); // 세션 무효화
		return "cmmn/home";
	}
}
