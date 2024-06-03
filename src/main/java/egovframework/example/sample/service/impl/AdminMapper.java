package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.example.sample.service.model.admin.AdminSurveyInsDto;
import egovframework.example.sample.service.model.admin.AdminSurveyInsDto.AdminSurveyQuestionInsDto;
import egovframework.example.sample.service.model.admin.AdminSurveyInsDto.AdminSurveyResponseInsDto;
import egovframework.example.sample.service.model.admin.AdminSurveyStatsSelVo.AdminAnswereResponseStatsGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyStatsSelVo.AdminMultipleResponseStatsGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyStatsSelVo.AdminSurveyQuestionStatsGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyListGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyQuestionGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyResponseOptionGetVo;
import egovframework.example.sample.service.model.admin.AdminSurveyStatsSelVo;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto.AdminSurveyQuestionImgUpdDto;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto.AdminSurveyQuestionUpdDto;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto.AdminSurveyResponseFormatUpdDto;
import egovframework.example.sample.service.model.admin.AdminSurveyUpdDto.AdminSurveyResponseUpdDto;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("adminMapper")
public interface AdminMapper {
	List<AdminSurveyListGetVo> getSurveyList();
	
	int insSurvey(AdminSurveyInsDto dto);
	int insQuestion(AdminSurveyQuestionInsDto dto);
	int insResponse(AdminSurveyResponseInsDto dto);
	int updQuestionImg(AdminSurveyQuestionImgUpdDto dto);
	int updResponseFormat(AdminSurveyResponseFormatUpdDto dto);
	int delQuestion(int iquestion);
	List<AdminSurveyQuestionGetVo> getQuestion(int isurvey);
	List<AdminSurveyResponseOptionGetVo> getResponseOption(int iquestion);
	
	int updSurvey(AdminSurveyUpdDto dto);
	int updQuestion(AdminSurveyQuestionUpdDto dto);
	int updResponse(AdminSurveyResponseUpdDto dto);
	
	int beforeResponseFormat(int iquestion);
	int delResponse(int ioption);
	
	AdminSurveyStatsSelVo selSurveyStats(int isurvey);
	List<AdminSurveyQuestionStatsGetVo> getSurveyQuestionStats(int isurvey);
	List<AdminMultipleResponseStatsGetVo> getMultipleQuestionStats(int iquestion);
	List<AdminAnswereResponseStatsGetVo> getAnswerResponseStats(int iquestion);
	
	int delSurvey(int isurvey);
	
	int delBeforeResponseFormat(int iquestion);
}