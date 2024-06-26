package egovframework.example.sample.service;

import java.util.Map;

import egovframework.example.sample.service.model.photo.PhotoSelVo;
import egovframework.example.sample.service.model.user.UserSignInDto;
import egovframework.example.sample.service.model.user.UserSignUpDto;

public interface UserService {
	int signUp(UserSignUpDto dto);
	Map<String, Object> signIn(UserSignInDto dto);
	int idChk(String id);
	String getUserHashedPwd(String id);
}