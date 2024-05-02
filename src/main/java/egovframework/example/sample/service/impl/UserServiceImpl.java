package egovframework.example.sample.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.UserService;
import egovframework.example.sample.service.model.UserDetailVo;
import egovframework.example.sample.service.model.UserSignInDto;
import egovframework.example.sample.service.model.UserSignUpDto;

@Service
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public UserServiceImpl(UserMapper userMapper) { this.userMapper = userMapper; }

	@Override
	public int signUp(UserSignUpDto dto) {
		dto.setPwd(BCrypt.hashpw(dto.getPwd(), BCrypt.gensalt()));
		return userMapper.signUp(dto);
	}

	@Override
	public UserDetailVo signIn(UserSignInDto dto) {
		return userMapper.signIn(dto);
	}
}
