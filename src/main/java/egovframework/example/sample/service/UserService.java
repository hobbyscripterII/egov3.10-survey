package egovframework.example.sample.service;

import java.util.Map;

import egovframework.example.sample.service.model.PhotoSelVo;
import egovframework.example.sample.service.model.UserSignInDto;
import egovframework.example.sample.service.model.UserSignUpDto;

public interface UserService {
	int signUp(UserSignUpDto dto);
	Map<String, Object> signIn(UserSignInDto dto);
	int idChk(String id);
	String getUserHashedPwd(String id);
}