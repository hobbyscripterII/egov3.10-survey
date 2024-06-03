package egovframework.example.sample.service.impl;

import egovframework.example.sample.service.model.user.UserDetailVo;
import egovframework.example.sample.service.model.user.UserSignInDto;
import egovframework.example.sample.service.model.user.UserSignUpDto;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("userMapper")
public interface UserMapper {
	int signUp(UserSignUpDto dto);
	UserDetailVo getUserDetail(UserSignInDto dto);
	int idChk(String id);
	String getUserHashedPwd(String id);
}