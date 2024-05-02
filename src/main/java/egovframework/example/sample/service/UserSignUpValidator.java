package egovframework.example.sample.service;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import egovframework.example.cmmn.Const;
import egovframework.example.sample.service.model.UserSignUpDto;

public class UserSignUpValidator implements Validator {
	@Override public boolean supports(Class<?> clazz) { return UserSignUpDto.class.isAssignableFrom(clazz); }

	@Override
	public void validate(Object target, Errors errors) {
		Logger log = LoggerFactory.getLogger(getClass());
		UserSignUpDto dto = (UserSignUpDto) target;
		boolean idMatchers = Pattern.matches("^[a-zA-Z0-9]*$", dto.getId()); // 한글, 특수문자를 제외한 정규식 검증 패턴
		boolean pwdMatchers = Pattern.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[\\W_]).{10,}$", dto.getPwd()); // 숫자, 영문자. 특수문자 조합으로 10자 이상 정규식 검증 패턴
		boolean idLength = dto.getId().length() > 5 && dto.getId().length() < 15; // 5자 이상 15자 이하
		boolean pwdLength = dto.getPwd().length() > 10 && dto.getPwd().length() < 25; // 10자 이상 25자 이하
		
		// 아이디에 값이 비어있거나 공백이면 필수 입력란 에러 메세지를 출력한다.
		if (isEmptyOrWhitespace(dto.getId())) { errors.rejectValue(Const.FIELD_ID, "errors.required", null, "필수 입력란입니다."); }
		// 아이디가 5자 이상 15자 이하가 아닐 경우 에러 메세지를 출력한다.
		if (!idLength) { errors.rejectValue(Const.FIELD_ID, "errors.range", new Object[] { "아이디", 5, 15 }, "아이디는(은) 5자 이상 15자 이하의 값이어야 합니다."); }
		// 아이디 정규식 검증 실패 시 에러 메세지를 출력한다.
		if (!idMatchers) { errors.rejectValue(Const.FIELD_ID, "errors.id.regex", null, "한글, 특수문자를 제외한 5~15자까지의 영문과 숫자로 입력해주세요."); }
		
		// 비밀번호에 값이 비어있거나 공백이면 필수 입력란 에러 메세지를 출력한다.
		if (isEmptyOrWhitespace(dto.getPwd())) { errors.rejectValue(Const.FIELD_PWD, "errors.required", null, "필수 입력란입니다."); }
		// 비밀번호가 10자 이상 25자 이하가 아닐 경우 에러 메세지를 출력한다.
		if (!pwdLength) { errors.rejectValue(Const.FIELD_PWD, "errors.range", new Object[] { "비밀번호", 10, 25 }, "비밀번호는 10자 이상 25자 이하의 값이어야 합니다."); }
		// 비밀번호 정규식 검증 실패 시 에러 메세지를 출력한다.
		if (!pwdMatchers) { errors.rejectValue(Const.FIELD_PWD, "errors.pwd.regex", null, "한글, 특수문자를 제외한 5~15자까지의 영문과 숫자로 입력해주세요."); }

		// 이름에 값이 비어있거나 3자 이하라면 에러 메세지를 출력한다.
		if (isEmptyOrWhitespace(dto.getName())) { errors.rejectValue(Const.FIELD_NAME, "errors.required", null, "필수 입력란입니다."); }
		// 이름이 2자 이하일 경우 에러 메세지를 출력한다.
		if (dto.getName().length() <= 2) { errors.rejectValue(Const.FIELD_NAME, "errors.lengthsize", new Object[] { 2 }, "2자 이상 입력해야 합니다."); }
	}
	
	public boolean isEmptyOrWhitespace(String val) {
		if(val == null || val.trim().length() == 0) { return true; }
		else { return false; }
	}
}
