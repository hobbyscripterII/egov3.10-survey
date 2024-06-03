package egovframework.example.sample.web;

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
import egovframework.example.sample.service.AdminService;
import egovframework.example.sample.service.SurveyService;
import egovframework.example.sample.service.model.ResultVo;
import egovframework.example.sample.service.model.admin.AdminSurveyInsDto;
import egovframework.example.sample.service.model.admin.AdminSurveyListGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyStatsSelVo;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto;
import egovframework.example.sample.service.model.survey.SurveySelVo;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final AdminService adminService;
	private final SurveyService surveyService;
	Logger log = LoggerFactory.getLogger(getClass());

	public AdminController(AdminService adminService, SurveyService surveyService) { this.adminService = adminService; this.surveyService = surveyService; }

	@GetMapping("/home.do") // 설문지 관리 홈 화면
	public String adminSurveyHome(Model model) {
		List<AdminSurveyListGetVo> list = adminService.getSurveyList();
		model.addAttribute(Const.MODEL_LIST_KEY, list);
		return "admin/home-survey";
	}

	@GetMapping("/create-survey.do") // 설문지 작성 화면
	public String adminCreateSurveyHome() {
		return "admin/create-survey";
	}
	
	@GetMapping("/update-survey.do") // 설문지 수정 화면
	public String adminUpdateSurveyHome(@RequestParam int isurvey, Model model) {
		SurveySelVo vo = surveyService.selSurvey(isurvey);
		model.addAttribute(Const.MODEL_SURVEY_KEY, vo);
		return "admin/update-survey";
	}
	
	@GetMapping("/stats-survey.do") // 설문지 통계 화면
	public String adminSurveyStatsHome(@RequestParam int isurvey, Model model) {
		model.addAttribute(Const.MODEL_VO_KEY, adminService.selSurveyStats(isurvey));
		return "admin/stats-survey";
	}
	
	@PostMapping("/stats-survey.do")
	@ResponseBody
	public AdminSurveyStatsSelVo adminSurveyStats(@RequestParam int isurvey) {
		return adminService.selSurveyStats(isurvey);
	}
	
	@PostMapping("/delete-survey.do")
	@ResponseBody
	public ResultVo delSurvey(@RequestParam int isurvey) throws Exception {
		return adminService.delSurvey(isurvey);
	}
	
	@PostMapping("/update-survey.do")
	@ResponseBody
	public ResultVo updSurvey(AdminSurveyUpdDto dto, HttpServletRequest request) throws Exception {
		try {
			dto.setIuser(getIuser(request));
			return adminService.updSurvey(dto);
		} catch(NullPointerException e) {
			return new ResultVo(Const.FAIL, Const.NOT_FOUND_SESSION);
		}
	}

	@PostMapping("/insert-survey.do")
	@ResponseBody
	public ResultVo insSurvey(AdminSurveyInsDto dto, HttpServletRequest request) throws Exception {
		try {
			dto.setIuser(getIuser(request));
			return adminService.insSurvey(dto);
		} catch(NullPointerException e) { // getIuser의 session에서 NPE 터지면 여기서 받음
			return new ResultVo(Const.FAIL, Const.NOT_FOUND_SESSION);
		}
	}

	public int getIuser(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (Utils.isNull(session)) {
			throw new NullPointerException();
		}

		return (int) request.getSession().getAttribute(Const.USER_IUSER);
	}
}
