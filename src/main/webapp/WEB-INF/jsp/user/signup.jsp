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
<style>
#body { display: flex; flex-wrap: wrap; justify-content: center; }
#div-signup-wrap { width: 30%; height: 100%; }
#btn-signup { margin-top: 10px; width: 100%; }
.text-muted { font-size: 13px; }
.text-muted > span { color: #FF0000; }
input, input::placeholder { font: 13px/3 sans-serif; }
</style>
<body>
<h1 class="title">회원가입</h1>
<div id="div-signup-wrap">
<form:form commandName="dto" id="f" action="signup.do" method="post">
    <div>
      <span class="form-label mt-4">아이디</span>
      <p class="text-muted"><form:errors path="id" /></p>
      <!-- path 사용 시 bindingResult의 객체에 담겨져있던 값이 다시 폼에 입력됨(사용자 편리) -->
      <form:input type="text" class="form-control" path="id" name="id" id="id" placeholder="한글, 특수문자를 제외한 5~15자까지의 영문과 숫자로 입력해주세요." />
    </div>
    <div>
      <label class="form-label mt-4">비밀번호</label>
      <p class="text-muted"><form:errors path="pwd" /></p>
      <form:input type="password" class="form-control" path="pwd" name="pwd" id="pwd" placeholder="숫자+영문자+특수문자 조합으로 10자리 이상 사용해야 합니다.(10자리~25자리)" />
    </div>
    <div>
      <label class="form-label mt-4">이름</label>
      <p class="text-muted"><form:errors path="name" /></p>
      <form:input type="text" class="form-control" path="name" name="name" id="name" placeholder="2자 이상 입력해주세요." />
    </div>
    <button type="button" class="btn btn-primary" id="btn-signup">회원가입</button>
</form:form>
</div>
</body>
</html>

<script type="text/javascript">
document.getElementById('btn-signup').addEventListener('click', () => {
const ID = document.getElementById('id');
const PWD = document.getElementById('pwd');
const NAME = document.getElementById('name');
if(!ID.value) { alert('아이디를 입력해주세요.'); ID.focus(); }
else if(!PWD.value) { alert('비밀번호를 입력해주세요.'); PWD.focus(); }
else if(!NAME.value) { alert('이름을 입력해주세요.'); NAME.focus(); }
else { document.getElementById('f').submit(); }
});
</script>
