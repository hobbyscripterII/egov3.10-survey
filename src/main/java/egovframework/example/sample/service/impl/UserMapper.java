package egovframework.example.sample.service.impl;

import egovframework.example.sample.service.model.UserDetailVo;
import egovframework.example.sample.service.model.UserSignInDto;
import egovframework.example.sample.service.model.UserSignUpDto;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("userMapper")
public interface UserMapper {
	int signUp(UserSignUpDto dto);
	UserDetailVo signIn(UserSignInDto dto);
}