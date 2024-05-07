<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
p { margin-bottom: 0 !important; }
#p-img-thumbnail-title, #p-img-thumbnail-name { width: 100%; margin-top: 5px; }
#p-img-thumbnail-title { font-weight: bold; }
</style>
<body>
<h1 class="title">사진 게시판 목록</h1>
<div><span class="span-board-cnt">전체: <span style="color: #D1180B"><c:out value="${pagination.total }" /></span>건</span></div>
<div class="div-photo-board-list-wrap">
	<c:forEach var="l" items="${list }">
		<div class="div-img-thumbnail-wrap">
			<a href="/winitech/photo/view.do?iboard=${l.iboard }"><img alt="썸네일" class="img-fluid img-thumbnail" src="/winitech/img/${l.thumbnail }"></a>
			<p id="p-img-thumbnail-title"><a href="/winitech/photo/view.do?iboard=${l.iboard }" style="color: #000 !important"><c:out value="${l.title }" /></a></p>
			<p id="p-img-thumbnail-name"><c:out value="${l.name }" /></p>
			<p style="color: #9E9E9E"><span><c:out value="${l.createdAt }" /></span><span>
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-fill" viewBox="0 0 16 16">
			  <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0"/>
			  <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8m8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7"/>
			</svg>
			<c:out value="${l.view }" /></span></p>
		</div>
	</c:forEach>
</div>

<div class="div-list-bottom-wrap">
	<c:if test="${not empty sessionScope.IUSER }">
		<div class="div-btn-group-wrap">
			<c:if test="${empty criteria.name && empty criteria.title }"><button type="button" class="btn btn-primary" onclick="location.href='/winitech/photo/write.do?category=2'">작성</button></c:if>
		   	<c:if test="${not empty param.name }"><button type="button" class="btn btn-primary" onclick="location.href='/winitech/photo/write.do?category=2&name=${criteria.name }'">작성</button></c:if>
			<c:if test="${not empty param.title }"><button type="button" class="btn btn-primary" onclick="location.href='/winitech/photo/write.do?category=2&title=${criteria.title }'">작성</button></c:if>
		</div>
	</c:if>
	
	<c:if test="${fn:length(list) != 0 }">
	  	<div class="div-pagination-wrap">
  		<ul class="pagination pagination-sm">
		  <li class="page-item"><a class="page-link" href="/winitech/photo/list.do?page=1&amount=${pagination.amount }&name=${criteria.name }&title=${criteria.title }">&laquo;</a></li>
		  <c:if test="${1 < pagination.start }"><li class="page-item"><a class="page-link" href="/winitech/photo/list.do?page=${pagination.start - pagination.pageCnt}&name=${criteria.name }&title=${criteria.title }">&lt;</a></li></c:if>
		  <c:forEach var="num" begin="${pagination.start }" end="${pagination.end }">
		  	<li class="page-item"><a class="page-link" href="/winitech/photo/list.do?page=${num }&amount=${pagination.amount }&name=${criteria.name }&title=${criteria.title }"><c:out value="${num }" /></a></li>
		  </c:forEach>
		  <c:if test="${pagination.end < pagination.realEnd }"><li class="page-item"><a class="page-link" href="/winitech/photo/list.do?page=${pagination.end + 1}&name=${criteria.name }&title=${criteria.title }">&gt;</a></li></c:if>
		  <li class="page-item"><a class="page-link" href="/winitech/photo/list.do?page=${pagination.realEnd }&amount=${pagination.amount }&name=${criteria.name }&title=${criteria.title }">&raquo;</a></li>
		</ul>
  	</div>
	</c:if>

  	<form class="d-flex">
  	<!-- 검색창 -->
    <div>
      <select class="form-select form-control" id="select-search" style="padding: .375rem 2.25rem .375rem .75rem">
        <option value="null">검색 카테고리</option>
        <option value="name">작성자</option>
        <option value="title">제목</option>
      </select>
    </div>
    <!-- 추가 기능 - 화면 이동 시 검색어 유지 -->
	<c:if test="${empty criteria.name && empty criteria.title }"><input class="form-control me-sm-2" id="input-search" type="search" placeholder="검색어를 입력해주세요." data-value="null" autocomplete="off"></c:if>
   	<c:if test="${not empty param.name }"><input class="form-control me-sm-2" id="input-search" type="search" value="${criteria.name }" data-value="name" autocomplete="off"></c:if>
	<c:if test="${not empty param.title }"><input class="form-control me-sm-2" id="input-search" type="search" value="${criteria.title }" data-value="title" autocomplete="off"></c:if>
    <input class="btn btn-secondary my-2 my-sm-0" id="btn-search" type="button" value="검색">
	</form>
</div>
</body>
</html>
<script type="text/javascript">
document.addEventListener('click', (e) => {
	if(e.target.id == 'btn-search') {
		const category = document.getElementById('select-search');
		const keyword = document.getElementById('input-search');
		if(category.value == 'null') { alert('검색 카테고리를 선택해주세요.'); category.focus(); }
		else if(keyword.value == null || keyword.value == '') { alert('검색 키워드를 입력해주세요.'); keyword.focus(); }
		else { location.href = '/winitech/photo/list.do?' + category.value + '=' + keyword.value; }
	}
});
</script>