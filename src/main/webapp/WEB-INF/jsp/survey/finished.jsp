<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<style>
	section { flex-wrap: wrap; }
	footer { position: fixed; bottom: 0; }
	#body { height: auto; }
</style>
<body>
<h1 class="title">설문조사 알림</h1>
<div id="div-error-form">
	<p><img id="img-icon-megaphone" alt="인사하는 사람 아이콘" src="${pageContext.request.contextPath }/images/egovframework/icon-greeting.png"></p>
	<p><c:out value="본 설문조사는 마감되었습니다." /></p>
	<p><c:out value="많은 관심 감사 드립니다." /></p>
	<p><button type="button" class="btn btn-secondary btn-lg" id="btn-back">돌아가기</button></p>
</div>
</body>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
document.addEventListener('click', (e) => {
	if(e.target.id == 'btn-back') {
		alert('이전 페이지로 이동합니다.');
		window.history.back();
	}
});
</script>
</html>