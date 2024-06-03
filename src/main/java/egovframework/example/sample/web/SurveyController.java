package egovframework.example.sample.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.SurveyService;
import egovframework.example.sample.service.model.ResultVo;
import egovframework.example.sample.service.model.survey.SurveyListGetVo;
import egovframework.example.sample.service.model.survey.SurveyResponseInsDto;
import egovframework.example.sample.service.model.survey.SurveySelVo;

@Controller
@RequestMapping("/survey")
public class SurveyController {
	private final SurveyService surveyService;
	Logger log = LoggerFactory.getLogger(getClass());

	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@GetMapping("/home.do") // 설문지 목록 페이지 홈 화면
	public String serveyHome(Model model) {
		List<SurveyListGetVo> list = surveyService.getSurveyList();
		model.addAttribute(Const.MODEL_LIST_KEY, list);
		return "survey/home";
	}

	@GetMapping("/form.do") // 설문지 참여 페이지 화면
	public String surveyForm(@RequestParam int isurvey, Model model, HttpServletRequest request) throws ParseException {
		SurveySelVo vo = surveyService.selSurvey(isurvey);
		vo.setName(getUserName(request));
		
		String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date finishedAt = new Date(format.parse(vo.getFinishedAt()).getTime());
		Date today = new Date(format.parse(now).getTime());
		int compare = finishedAt.compareTo(today);
		
		if(compare < 0) { return "survey/finished"; }
		
		model.addAttribute(Const.MODEL_SURVEY_KEY, vo);
		return "survey/form";
	}
	
	@PostMapping("/participate.do")
	@ResponseBody
	public ResultVo surveyParticipate(SurveyResponseInsDto dto, HttpServletRequest request) throws Exception {
		int iuser = getIuser(request);
		dto.setIuser(iuser);
		return surveyService.insResponse(dto);
	}

	// 로그인 시 session에 저장했던 회원 이름
	public String getUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (Utils.isNull(session)) { throw new NullPointerException(); }
		return (String) request.getSession().getAttribute(Const.USER_NAME);
	}

	// 로그인 시 session에 저장했던 회원 pk
	public int getIuser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (Utils.isNull(session)) { throw new NullPointerException(); }
		return (int) request.getSession().getAttribute(Const.USER_IUSER);
	}
}