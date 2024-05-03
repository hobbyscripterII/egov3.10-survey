package egovframework.example.sample.web;

import java.util.Map;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	public UserController(UserService userService) { this.userService = userService; }

	@PostMapping("/id-check.do")
	@ResponseBody
	public int idCheck(@RequestParam String id) {
		// 사용자가 입력한 아이디를 갖고와 유효성 검증을 진행함
		boolean idLength = id.length() > 5 && id.length() < 15; // 5자 이상 15자 이하
		boolean idMatchers = Pattern.matches("[A-Za-z0-9]+", id); // 영문자 대소문자 / 숫자만 가능

		// 유효성 검증 실패 시 플래그 값을 던져줌(화면 출력용)
		if (!idLength) { return Const.ID_LENGTH_ERROR; }
		if (!idMatchers) { return Const.ID_REGEX_ERROR; }

		// 유효성 검증 성공 시에만 테이블의 아이디를 찾음
		int idChkRows = userService.idChk(id); // 1 - 중복 o / 0 - 중복 x
		if (Utils.isNull(idChkRows)) { return Const.SUCCESS; }
		return Const.FAIL;
	}

	@GetMapping("/signup.do")
	public String signUp(Model model) {
		model.addAttribute("dto", new UserSignUpDto());
		return "user/signup";
	}

	@GetMapping("/signup-success.do")
	public String signUpSuccess() { return "user/signup-success"; }

	@PostMapping("/signup.do")
	public String signUp(@ModelAttribute("dto") UserSignUpDto dto, BindingResult bindingResult) {
		new UserSignUpValidator().validate(dto, bindingResult);
		if (bindingResult.hasErrors()) {
			return "user/signup";
		} else {
			int signUpRows = userService.signUp(dto);
			// 회원가입 성공
			if (Utils.isNotNull(signUpRows)) { return "user/signup-success"; }
			// 회원가입 실패
			else {
				bindingResult.addError(new ObjectError("id", "일시적인 오류로 회원가입에 실패했습니다. 잠시 후 다시 시도해주세요."));
				return "user/signup";
			}
		}
	}

	@GetMapping("/signin.do")
	public String signIn(Model model) {
		model.addAttribute("dto", new UserSignInDto());
		return "user/signin";
	}

	@PostMapping("/signin.do")
	public String signIn(@ModelAttribute("dto") UserSignInDto dto, BindingResult bindingResult, HttpServletRequest request) {
		Map<String, Object> resultMap = userService.signIn(dto); // 아이디, 비밀번호 일치 시 회원 테이블의 pk 들고 옴
		int msgKey = (int) resultMap.get(Const.MSG_KEY);

		if (msgKey == Const.SUCCESS) {
			UserDetailVo userDetail = (UserDetailVo) resultMap.get(Const.VO_KEY);
			HttpSession session = request.getSession();
			session.setAttribute(Const.IUSER, userDetail.getIuser());
			session.setAttribute(Const.ROLE, userDetail.getRole());
			return "cmmn/home";
		} else {
			if (msgKey == Const.NOT_FOUND_USER_ID) { bindingResult.addError(new ObjectError("id", "존재하지 않는 아이디입니다. 다시 한번 확인해주세요.")); }
			else { bindingResult.addError(new ObjectError("id", "아이디 혹은 비밀번호가 일치하지 않습니다. 확인 후 다시 시도해주세요.")); }
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
