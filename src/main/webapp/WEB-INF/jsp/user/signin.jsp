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
#div-signin-wrap { width: 30%; height: 100%; }
#btn-signin { margin-top: 10px; width: 100%; }
.text-muted { height: 20px; font-size: 13px; text-align: center; }
.text-muted > span { color: #FF0000; }
</style>
<body>
<h1 class="title">로그인</h1>
<div id="div-signin-wrap">
<form:form commandName="dto" id="f" action="signin.do" method="post">
    <p class="text-muted"><form:errors path="*" /></p>
    <div>
      <span class="form-label mt-4">아이디</span>
      <form:input type="text" class="form-control" path="id" name="id" id="id" placeholder="아이디" />
    </div>
    <div>
      <label class="form-label mt-4">비밀번호</label>
      <form:input type="password" class="form-control" path="pwd" name="pwd" id="pwd" placeholder="비밀번호" />
    </div>
    <button type="button" class="btn btn-primary" id="btn-signin">로그인</button>
</form:form>
</div>
</body>
</html>

<script type="text/javascript">
document.getElementById('btn-signin').addEventListener('click', () => {
const ID = document.getElementById('id');
const PWD = document.getElementById('pwd');

if(!ID.value) { alert('아이디를 입력해주세요.'); ID.focus(); }
else if(!PWD.value) { alert('비밀번호를 입력해주세요.'); PWD.focus(); }
else { document.getElementById('f').submit(); }
});
</script>
