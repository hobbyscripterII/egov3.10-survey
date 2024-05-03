<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style> .span-red-text { color: red; font-weight: bold; } #body { display: flex; flex-wrap: wrap; justify-content: center; height: 100%; } .text-muted > span { font-size: 13px; } input, input::placeholder { font: 13px/3 sans-serif; } </style>
<body>
<h1 class="title">회원가입</h1>
<div id="div-signup-wrap">
<form:form commandName="dto" id="f" action="signup.do" method="post">
	<div style="width: 100%; text-align: right">
      <label class="form-label mt-4" style="color: #C0C0C0"><span class="span-red-text">* </span>은 필수 입력사항입니다.</label>
	</div>
    <div>
      <label class="form-label mt-4">아이디<span class="span-red-text"> *</span> <button type="button" class="btn btn-primary btn-sm" id="btn-id-chk" data-id-chk-fl="0">아이디 중복 체크</button></label>
      <label class="text-muted"><form:errors path="id" /></label>
      <!-- path 사용 시 bindingResult의 객체에 담겨져있던 값이 다시 폼에 입력됨(사용자 편리) -->
      <form:input type="text" class="form-control" path="id" name="id" id="id" placeholder="한글, 특수문자를 제외한 5~15자까지의 영문과 숫자로 입력해주세요." />
    </div>
    <div>
      <label class="form-label mt-4">비밀번호<span class="span-red-text"> *</span></label>
      <label class="text-muted"><form:errors path="pwd" /></label>
      <form:input type="password" class="form-control" path="pwd" name="pwd" id="pwd" placeholder="숫자+영문자+특수문자 조합으로 10자리 이상 사용해야 합니다.(10자리~25자리)" />
    </div>
    <div>
      <label class="form-label mt-4">비밀번호 확인<span class="span-red-text"> *</span></label>
      <label class="text-muted" id="p-pwd-chk-msg"><form:errors path="pwdChk" /></label>
      <form:input type="password" class="form-control" path="pwdChk" name="pwdChk" id="pwdChk" placeholder="입력하신 비밀번호와 동일하게 입력해주세요." />
    </div>
    <div>
      <label class="form-label mt-4">이름<span class="span-red-text"> *</span></label>
      <label class="text-muted"><form:errors path="name" /></label>
      <form:input type="text" class="form-control" path="name" name="name" id="name" placeholder="2자 이상 입력해주세요." />
    </div>
    <button type="button" class="btn btn-secondary btn-lg btn-signup" id="btn-signup-cancel">취소</button>
    <button type="button" class="btn btn-info btn-lg btn-signup" id="btn-signup">회원가입</button>
</form:form>
</div>
</body>
</html>

<script type="text/javascript">
const SUCCESS = 1; // 중복 x
const FAIL = 0; // 중복 o
const ID_LENGTH_ERROR = -1;
const ID_REGEX_ERROR = -2;
const SUCCESS_MSG = '사용 가능한 아이디입니다.';
const FAIL_MSG = '이미 등록된 아이디입니다.';
const ID_LENGTH_ERROR_MSG = '아이디의 길이는 5~15자입니다.';
const ID_REGEX_ERROR_MSG = '아이디는 한글, 특수문자를 제외한 5~15자입니다.';
const PWD_CHK_SUCCESS_MSG = "입력하신 패스워드와 일치합니다.";
const PWD_CHK_FAIL_MSG = "입력하신 패스워드와 일치하지 않습니다.";
let MSG;

let pwd = document.getElementById('pwd'); // 비밀번호 input 폼
let pwdChk = document.getElementById('pwdChk'); // 비밀번호 확인 input 폼
let pwdChkMsgEl = document.getElementById('p-pwd-chk-msg'); // 비밀번호 일치 / 불일치 시 출력되는 p 태그
let btnIdChk = document.getElementById('btn-id-chk'); // 아이디 중복 체크 버튼

let id = document.getElementById('id'); // 아이디 input 폼
let idChk = btnIdChk.dataset.idChkFl; // 아이디 중복 체크 확인 플래그
let btnSignUp = document.getElementById('btn-signup'); // 회원가입 버튼
let btnSignUpCancel = document.getElementById('btn-signup-cancel'); // 회원가입 취소 버튼

btnIdChk.addEventListener('click', () => {
	if(!id.value) {
		alert('아이디를 입력해주세요.');
	} else {
		$.ajax({
	        type: 'post',
	        url: '/winitech/user/id-check.do',
	        data: { 'id' : id.value },
	        success: (result) => {
	        	if(result == SUCCESS) {
	        		idChk = '1';
	        		MSG = SUCCESS_MSG;
	        		alert(MSG);
	        	} else {
	        		idChk = '0';
	        		if(result == FAIL) { MSG = FAIL_MSG; }
	        		else if(result == ID_LENGTH_ERROR) { MSG = ID_LENGTH_ERROR_MSG; }
	        		else { MSG = ID_REGEX_ERROR_MSG; }
	        		alert(MSG);
	        	}
	        }, 
	        error: (x) => { console.log(x); }
	     })
	}
});

btnSignUp.addEventListener('click', () => {
	const id = document.getElementById('id');
	const name = document.getElementById('name');
	
	if(!id.value) { alert('아이디를 입력해주세요.'); id.focus(); }
	else if(!pwd.value) { alert('비밀번호를 입력해주세요.'); pwd.focus(); }
	else if(!name.value) { alert('이름을 입력해주세요.'); name.focus(); }
	else if(idChk != SUCCESS) { alert('아이디 중복 체크를 확인해주세요.'); }
	else { document.getElementById('f').submit(); }}
);

pwdChk.addEventListener('keyup', () => {
	if(pwd.value == pwdChk.value) { pwdChkMsgEl.innerHTML = '<span style="color: green">'+ PWD_CHK_SUCCESS_MSG +'</span>'; }
	else { pwdChkMsgEl.innerHTML = '<span style="color: red">'+ PWD_CHK_FAIL_MSG +'</span>'; }
});

btnSignUpCancel.addEventListener('click', () => { if(confirm('회원가입을 취소하시겠습니까?')) { location.href = '/winitech/home.do' } })
</script>
