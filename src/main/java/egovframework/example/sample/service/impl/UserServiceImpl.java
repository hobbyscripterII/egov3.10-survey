package egovframework.example.sample.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.Utils;
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
	public Map<String, Object> signIn(UserSignInDto dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserDetailVo userDetail = userMapper.getUserDetail(dto); // iuser, pwd, name, role이 저장된 vo

		// 1. 사용자가 입력한 아이디가 없을 때
		if (Utils.isNull(userDetail)) {
			resultMap.put(Const.MSG_KEY, Const.NOT_FOUND_USER_ID);
			return resultMap;
		}

		// 2. 사용자가 입력한 비밀번호와 테이블에 저장된 비밀번호가 일치한지
		boolean result = BCrypt.checkpw(dto.getPwd(), userDetail.getPwd());

		if (Utils.isTrue(result)) {
			resultMap.put(Const.MSG_KEY, Const.SUCCESS);
			resultMap.put(Const.VO_KEY, userDetail);
			return resultMap;
		} else {
			resultMap.put(Const.MSG_KEY, Const.PASSWORD_MISMATCH);
			return resultMap;
		}
	}

	@Override
	public int idChk(String id) {
		return userMapper.idChk(id);
	}

	@Override
	public String getUserHashedPwd(String id) {
		return userMapper.getUserHashedPwd(id);
	}
}
