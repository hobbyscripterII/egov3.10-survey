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
#body { display: flex; flex-wrap: wrap; justify-content: center; height: 67vh; }
#div-signup-wrap { width: 30%; height: 100%; }
#btn-login { margin-top: 10px; width: 100%; }
.text-muted { font-size: 13px; }
.text-muted > span { color: #FF0000; }
input, input::placeholder { font: 13px/3 sans-serif; }
</style>
<body>
<h1 class="title">회원가입 성공</h1>

<div id="div-signup-success-form">
	<p><a href="https://www.flaticon.com/kr/free-icon/key-person_14820457" title="열쇠를 쥐고있는 사람 아이콘" target="_blank"><img id="img-icon-megaphone" alt="열쇠를 쥐고있는 사람 아이콘" src="${pageContext.request.contextPath }/images/egovframework/winitech/icon-keyman.png"></a></p>
	<p><c:out value="축하드립니다! 회원가입이 완료되었습니다." /></p>
	<p><button type="button" class="btn btn-info btn-lg" id="btn-login">로그인</button></p>
</div>
</body>
</html>

<script type="text/javascript">
document.getElementById('btn-login').addEventListener('click', () => {
	location.href = '/winitech/user/signin.do';
});
</script>
