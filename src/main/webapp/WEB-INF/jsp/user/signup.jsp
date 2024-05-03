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
      <label class="form-label mt-4">아이디<span class="span-red-text"> *</span></label>
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
      <form:input type="password" class="form-control" path="pwdChk" name="pwdChk" id="pwdChk" placeholder="입력하신 비밀번호와 동일하게 입력해주세요" />
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
const PWD_CHK = document.getElementById('pwdChk');
const PWD = document.getElementById('pwd');
const PWD_CHK_MSG_SUCCESS = "입력하신 패스워드와 일치합니다.";
const PWD_CHK_MSG_FAIL = "입력하신 패스워드와 일치하지 않습니다.";
let pwdChkMsgEl = document.getElementById('p-pwd-chk-msg');

PWD_CHK.addEventListener('keyup', () => {
	if(PWD.value == PWD_CHK.value) { pwdChkMsgEl.innerHTML = '<span style="color: green">'+ PWD_CHK_MSG_SUCCESS +'</span>'; }
	else { pwdChkMsgEl.innerHTML = '<span style="color: red">'+ PWD_CHK_MSG_FAIL +'</span>'; }
});

document.getElementById('btn-signup').addEventListener('click', () => {
	const ID = document.getElementById('id');
	const NAME = document.getElementById('name');
	
	if(!ID.value) { alert('아이디를 입력해주세요.'); ID.focus(); }
	else if(!PWD.value) { alert('비밀번호를 입력해주세요.'); PWD.focus(); }
	else if(!NAME.value) { alert('이름을 입력해주세요.'); NAME.focus(); }
	else { document.getElementById('f').submit(); }}
);

document.getElementById('btn-signup-cancel').addEventListener('click', () => { if(confirm('회원가입을 취소하시겠습니까?')) { window.history.back(); } })
</script>
