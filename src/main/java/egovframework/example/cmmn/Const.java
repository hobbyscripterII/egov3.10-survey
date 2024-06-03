package egovframework.example.cmmn;

public class Const {
	// API
	public static final String MODEL_YEARS_KEY = "YEARS";
	public static final String MODEL_JSON_KEY = "JSON";
	public static final String MODEL_ERRORS_KEY = "ERRORS";
	public static final String MODEL_CATEGORY_KEY = "CATEGORY";
	public static final String MODEL_KEYWORD_KEY = "KEYWORD";
	public static final String MODEL_NAME_KEY = "NAME";
	public static final String MODEL_PARAMETER_KEY = "PARAMETER";
	public static final String MODEL_STOCK_NAME_KEY = "STOCKNAME";
	public static final String MODEL_MOVIENM_KEY = "MOVIENM";
	public static final String MODEL_DIRECTORNM_KEY = "DIRECTORNM";
	public static final String MODEL_TOTCNT_KEY = "TOTCNT";
	
	public static final String ERRORS_NOT_FOUND_STOCK_NAME = "[알림] 해당 종목을 찾을 수 없습니다. 다시 입력해주세요.";
	public static final String ERRORS_NOT_FOUND_URL = "[알림] 데이터를 불러오는 데 실패하였습니다. 관리자에게 문의해주세요.";
	public static final String ERRORS_FILE_READ_FAIL = "[알림] 파일을 읽을 수 없습니다. 관리자에게 문의해주세요.";
	public static final String ERRORS_RUNTIME_EXCEPTION = "[알림] 내부적으로 에러가 발생하였습니다. 관리자에게 문의해주세요.";
	
	// 과제
	public static final int CODE_TEST_EXIT = 0;
	public static final String MSG_TEST_EXIT = "테스트가 종료되었습니다.";
	
	public static final String SURVEY_CREATE_SUCCESS = "설문조사 등록이 완료되었습니다.";
	public static final String SURVEY_CREATE_FAIL = "설문조사 등록에 실패하였습니다. 잠시 후 다시 시도해주세요.";
	public static final String SURVEY_IMAGE_UPLOAD_FAIL = "설문조사 등록 중 이미지 업로드에 실패하였습니다. 잠시 후 다시 시도해주세요.";
	public static final String NOT_FOUND_SESSION = "해당 권한이 없습니다.";
	public static final String SURVEY_UPDATE_SUCCESS = "설문조사 수정이 완료되었습니다.";
	public static final String SURVEY_UPDATE_FAIL = "설문조사 수정에 실패하였습니다. 잠시 후 다시 시도해주세요.";
	public static final String NOT_FOUND_USER = "회원가입 후 설문조사에 참여해주세요.";
	public static final String SURVEY_PARTICIPATE_SUCCESS = "설문조사 참여가 완료되었습니다. 감사합니다.";
	public static final String SURVEY_PARTICIPATE_FAIL = "일시적인 오류로 설문조사 참여에 실패하였습니다. 잠시 후 다시 시도해주세요.";
	public static final String SURVEY_PARTICIPATE_INSERT_FAIL = "누락된 항목에 응답 후 참여해주세요.";
	public static final String SURVEY_DELETE_FAIL = "설문조사 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.";
	public static final String SURVEY_DELETE_SUCCESS = "설문조사 삭제가 완료되었습니다.";
	public static final String SURVEY_REQUIRED_QUESTION_ERROR = "필수 문항에 응답해주세요.";
	
	// Model Key 값
	public static final String MODEL_LIST_KEY = "LIST";
	public static final String MODEL_VO_KEY = "VO";
	public static final String MODEL_DTO_KEY = "DTO";
	public static final String MODEL_SURVEY_KEY = "SURVEY";
	public static final String MODEL_QUESTION_KEY = "QUESTION";
	
	// 권한 문자열
	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_USER = "USER";
	
	// 관리자 권한 인터셉터 url 문자열
	public static final String ROLE_ADMIN_COMMON_URL = "admin";
	
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
	public static final String USER_IUSER = "IUSER"; // 회원 pk
	public static final String USER_NAME = "NAME"; // 이름
	public static final String USER_ROLE = "ROLE"; // 권한
	public static final String USER_IP = "IP"; // 접속 ip

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
