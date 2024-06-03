<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg bg-dark" data-bs-theme="dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="/survey">설문조사 프로젝트</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
    <div class="collapse navbar-collapse" id="navbarColor01">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="/survey/board/list.do?category=1">게시판</a></li>
        <li class="nav-item"><a class="nav-link" href="/survey/photo/list.do?category=2">사진 게시판</a></li>
      	<c:if test="${sessionScope.ROLE == 'ADMIN' || sessionScope.ROLE == 'USER' }">
	        <li class="nav-item"><a class="nav-link" href="/survey/survey/home.do">설문조사 목록</a></li>
      	</c:if>
        <c:if test="${sessionScope.ROLE == 'ADMIN' }">
          <li class="nav-item"><a class="nav-link" href="/survey/admin/home.do">설문 관리</a></li>
        </c:if>
      </ul>
      <c:choose>
      	<c:when test="${empty sessionScope.IUSER }">
      		<button type="button" class="btn btn-secondary btn-margin" onclick="location.href='/survey/user/signin.do'">로그인</button>
		    <button type="button" class="btn btn-primary" onclick="location.href='/survey/user/signup.do'">회원가입</button>
      	</c:when>
      	<c:otherwise>
	  		<button type="button" class="btn btn-secondary" id="btn-signout">로그아웃</button>
      	</c:otherwise>
      </c:choose>
    </div>
  </div>
</nav>

<script type="text/javascript">
document.getElementById('btn-signout').addEventListener('click', () => {
	if(confirm('로그아웃하시겠습니까?')) {
		alert('로그아웃이 정상적으로 완료되었습니다.');
		location.href = '/survey/user/signout.do';
	}
});
</script>