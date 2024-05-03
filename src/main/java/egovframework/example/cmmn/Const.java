package egovframework.example.cmmn;

public class Const {
	// [예외 처리]
	// HashMap key 값
	public static final String MSG_KEY = "MSG";
	public static final String ERRORS_KEY = "ERRORS";
	public static final String IBOARD_KEY = "IBOARD";
	public static final String VO_KEY = "VO";
	// 'code <= 0'이 true 일 경우 예외 처리를 위한 에러 코드로 인식
	public static final int SUCCESS = 1;
	// default 실패 플래그 값(추후 에러 코드 변경하기)
	public static final int FAIL = 0;
	// 게시글 등록 시 플래그 반환 값
	public static final int INTERNAL_SERVER_ERROR = -5;
	public static final int PASHED_PASSWORD_FAIL_ERROR = -1;
	public static final int VALIDATION_ERROR = -2;
	public static final int NULL_POINTER_EXCEPTION = -4;
	// 로그인 시 플래그 반환 값
	public static final int NOT_FOUND_USER_ID = 0;
	public static final int PASSWORD_MISMATCH = -1;

	// [답변글 여부]
	public static final String REPLY_FL = "Y";
	public static final String UN_REPLY_FL = "N";
	public static final int UN_REPLY_CODE = 0;
	
	// [회원 로그인 시 세션 저장용 상수]
	public static final String IUSER = "IUSER"; // 회원 pk
	public static final String ROLE = "ROLE"; // 권한

	// [유효성 검증]
	// 필드 멤버명
	public static final String FIELD_ID = "id";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_PWD = "pwd";
	public static final String FIELD_PWD_CHK = "pwdChk";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_CONTENTS = "contents";
	// 필드 멤버명 컨버터용
	public static final String FIELD_NAME_CONVERT = "작성자";
	public static final String FIELD_PWD_CONVERT = "패스워드";
	public static final String FIELD_TITLE_CONVERT = "제목";
	public static final String FIELD_CONTENTS_CONVERT = "내용";
	
	// [회원가입 아이디 중복 체크]
	public static final int ID_LENGTH_ERROR = -1; // 아이디 길이 유효성 검증 실패
	public static final int ID_REGEX_ERROR = -2; // 아이디 정규식 검증 실패
	
//	public static final String[] EXT_EXCEPTION_ARR = {".exe", ".gif", ".zip"}; // 확장명
}
