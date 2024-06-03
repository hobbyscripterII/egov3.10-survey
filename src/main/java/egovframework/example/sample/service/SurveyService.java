package egovframework.example.sample.service;

import java.util.List;

import egovframework.example.sample.service.model.ResultVo;
import egovframework.example.sample.service.model.survey.SurveyListGetVo;
import egovframework.example.sample.service.model.survey.SurveyResponseInsDto;
import egovframework.example.sample.service.model.survey.SurveySelVo;

public interface SurveyService {
	List<SurveyListGetVo> getSurveyList();
	SurveySelVo selSurvey(int isurvey);
	ResultVo insResponse(SurveyResponseInsDto dto);
}