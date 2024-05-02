package egovframework.example.sample.service;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import egovframework.example.cmmn.Const;
import egovframework.example.sample.service.model.BoardInsDto;

public class BoardInsValidator implements Validator {
	@Override public boolean supports(Class<?> clazz) { return BoardInsDto.class.isAssignableFrom(clazz); }

	@Override
	public void validate(Object target, Errors errors) {
		BoardInsDto dto = (BoardInsDto) target;
		
		/*
		 * 이름 - 공백 x, 2자 이상
		 * 패스워드 - 공백 x, 3자 이상
		 * 제목 - 공백 x, 3자 이상
		 * 내용 - 공백 x, 10자 이상
		 */
		
		// 이름에 값이 비어있거나 공백이면 필수 입력란 에러 메세지를 출력한다.
		if (isEmptyOrWhitespace(dto.getName())) { errors.rejectValue(Const.FIELD_NAME, "errors.required", null, "필수 입력란입니다."); }
		// 이름이 2자 이하일 경우 에러 메세지를 출력한다.
		if (dto.getName().length() <= 2) { errors.rejectValue(Const.FIELD_NAME, "errors.lengthsize", new Object[] { 2 }, "2자 이상 입력해야 합니다."); }

		// 패스워드에 값이 비어있거나 3자 이하라면 에러 메세지를 출력한다.
		if (isEmptyOrWhitespace(dto.getPwd())) { errors.rejectValue(Const.FIELD_PWD, "errors.required", null, "필수 입력란입니다."); }
		// 패스워드가 3자 이하일 경우 에러 메세지를 출력한다.
		if (dto.getPwd().length() <= 3) { errors.rejectValue(Const.FIELD_PWD, "errors.lengthsize", new Object[] { 3 }, "3자 이상 입력해야 합니다."); }
		
		// 제목에 값이 비어있거나 공백이면 필수 입력란 에러 메세지를 출력한다.
		if (isEmptyOrWhitespace(dto.getTitle())) { errors.rejectValue(Const.FIELD_TITLE, "errors.required", null, "필수 입력란입니다."); }
		// 제목이 3자 이하일 경우 에러 메세지를 출력한다.
		if (dto.getTitle().length() <= 3) { errors.rejectValue(Const.FIELD_TITLE, "errors.lengthsize", new Object[] { 3 }, "3자 이상 입력해야 합니다."); }
		
		// 내용에 값이 비어있거나 공백이면 필수 입력란 에러 메세지를 출력한다.
		if (isEmptyOrWhitespace(dto.getContents())) { errors.rejectValue(Const.FIELD_CONTENTS, "errors.required", null, "필수 입력란입니다."); }
		// 내용이 10자 이하일 경우 에러 메세지를 출력한다.
		if (dto.getContents().length() <= 10) { errors.rejectValue(Const.FIELD_CONTENTS, "errors.lengthsize", new Object[] { 10 }, "10자 이상 입력해야 합니다."); }
	}
	
	public boolean isEmptyOrWhitespace(String val) {
		if(val == null || val.trim().length() == 0) { return true; }
		else { return false; }
	}
}
