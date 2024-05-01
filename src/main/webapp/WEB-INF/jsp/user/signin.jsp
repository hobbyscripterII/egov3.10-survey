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
#div-signin-wrap { width: 20%; height: 100%; }
#btn-login { margin-top: 10px; }
.text-muted { font-size: 13px; }
</style>
<body>
<h1 class="title">로그인</h1>
<div id="div-signin-wrap">
<form id="f" action="signin.do" method="post">
    <div>
      <span class="form-label mt-4">아이디</span>
      <!-- <span class="text-muted">We'll never share your email with anyone else.</span> -->
      <input type="text" class="form-control" name="id" id="id" placeholder="아이디">
    </div>
    <div>
      <label class="form-label mt-4">비밀번호</label>
      <!-- <span class="text-muted">We'll never share your email with anyone else.</span> -->
      <input type="password" class="form-control" name="pwd" id="pwd" placeholder="비밀번호">
    </div>
    <button type="button" class="btn btn-primary" id="btn-login">로그인</button>
</form>
</div>
</body>
</html>

<script type="text/javascript">
document.getElementById('btn-login').addEventListener('click', () => {
const ID = document.getElementById('id');
console.log('ID = ', ID);
const PWD = document.getElementById('pwd');
console.log('PWD = ', PWD);

if(!ID.value) { alert('아이디를 입력해주세요.'); ID.focus(); }
else if(!PWD.value) { alert('비밀번호를 입력해주세요.'); PWD.focus(); }
else { document.getElementById('f').submit(); }
});
</script>
