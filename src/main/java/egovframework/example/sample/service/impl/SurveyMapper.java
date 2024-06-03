package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.example.sample.service.model.survey.SurveyListGetVo;
import egovframework.example.sample.service.model.survey.SurveyResponseInsDto;
import egovframework.example.sample.service.model.survey.SurveySelVo;
import egovframework.example.sample.service.model.survey.SurveySelVo.SurveyQuestionGetVo;
import egovframework.example.sample.service.model.survey.SurveySelVo.SurveyQuestionResponseOptionGetVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("surveyMapper")
public interface SurveyMapper {
	List<SurveyListGetVo> getSurveyList();
	SurveySelVo selSurvey(int isurvey);
	List<SurveyQuestionGetVo> getSurveyQuestion(int isurvey);
	List<SurveyQuestionResponseOptionGetVo> getResponseOption(int iquestion);
	int insResponse(SurveyResponseInsDto dto);
}