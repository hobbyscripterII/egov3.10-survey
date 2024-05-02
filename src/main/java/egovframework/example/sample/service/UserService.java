package egovframework.example.sample.service;

import egovframework.example.sample.service.model.UserDetailVo;
import egovframework.example.sample.service.model.UserSignInDto;
import egovframework.example.sample.service.model.UserSignUpDto;

public interface UserService {
	int signUp(UserSignUpDto dto);
	UserDetailVo signIn(UserSignInDto dto);
}