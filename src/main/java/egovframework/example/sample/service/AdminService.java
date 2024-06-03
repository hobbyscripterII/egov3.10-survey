package egovframework.example.sample.service;

import java.util.List;

import egovframework.example.sample.service.model.ResultVo;
import egovframework.example.sample.service.model.admin.AdminSurveyInsDto;
import egovframework.example.sample.service.model.admin.AdminSurveyListGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyStatsSelVo;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto;

public interface AdminService {
	List<AdminSurveyListGetVo> getSurveyList();
	ResultVo insSurvey(AdminSurveyInsDto dto);
	ResultVo updSurvey(AdminSurveyUpdDto dto);
	AdminSurveyStatsSelVo selSurveyStats(int isurvey);
	ResultVo delSurvey(int isurvey);
}