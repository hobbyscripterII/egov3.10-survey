<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<body>
<h1 class="title">게시판 목록</h1>
<div><span class="span-board-cnt">전체: <span style="color: #D1180B"><c:out value="${pagination.total }" /></span>건</span></div>
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col" class="th-style">번호</th>
      <th scope="col" class="th-style">제목</th>
      <th scope="col" class="th-style">작성자</th>
      <th scope="col" class="th-style">조회수</th>
      <th scope="col" class="th-style">작성일</th>
    </tr>
  </thead>
  <tbody>
  
  <c:choose>
  	<c:when test="${fn:length(vo) == 0 }">
	  	<tr style="text-align: center"><td colspan="5" style="font-weight: bold">검색 결과가 없습니다. <br> 검색 키워드: &nbsp;<c:if test="${not empty param.title }"><c:out value="'${param.title }'" /></c:if><c:if test="${not empty param.name }"><c:out value="'${param.name }'" /></c:if></td></tr>
  	</c:when>
  	<c:otherwise>
  	  <c:forEach var="item" items="${vo }">
	    <tr>
	      <th scope="row" class="td-style" ><c:out value="${item.iboard }" /></th>
	      <td><a href="/winitech/board/view.do?iboard=${item.iboard }" style="color: #000 !important"><c:out value="${item.title }" /></a></td>
	      <td class="td-style"><c:out value="${item.name }" /></td>
	      <td class="td-style"><c:out value="${item.view }" /></td>
	      <td class="td-style"><fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" var="createdAt" value="${item.createdAt }" /><fmt:formatDate pattern="yyyy년 MM월 dd일 HH:mm" value="${createdAt }" /></td>
	    </tr>
	    <c:if test="${item.reply != null }">
		    <tr>
		    	<td></td>
			    <td><a href="/winitech/board/view.do?iboard=${item.reply.iboard }" style="color: #000 !important"><strong>답변글: </strong><c:out value="${item.reply.title }" /></a></td>
		    	<td class="td-style"><c:out value="${item.reply.name }" /></td>
		    	<td class="td-style"><c:out value="${item.reply.view }" /></td>
		    	<td class="td-style"><fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" var="createdAt" value="${item.reply.createdAt }" /><fmt:formatDate pattern="yyyy년 MM월 dd일 HH:mm" value="${createdAt }" /></td>
		    </tr>
	    </c:if>
	  </c:forEach>
  	</c:otherwise>
  </c:choose>

  </tbody>
</table>
<div class="div-list-bottom-wrap">
	<div class="div-btn-group-wrap">
		<c:if test="${empty criteria.name && empty criteria.title }"><button type="button" class="btn btn-primary" onclick="location.href='/winitech/board/write.do?category=1'">작성</button></c:if>
	   	<c:if test="${not empty param.name }"><button type="button" class="btn btn-primary" onclick="location.href='/winitech/board/write.do?category=1&name=${criteria.name }'">작성</button></c:if>
		<c:if test="${not empty param.title }"><button type="button" class="btn btn-primary" onclick="location.href='/winitech/board/write.do?category=1&title=${criteria.title }'">작성</button></c:if>
	</div>
	
	<c:if test="${fn:length(vo) != 0 }">
	  	<div class="div-pagination-wrap">
  		<ul class="pagination pagination-sm">
		  <li class="page-item"><a class="page-link" href="/winitech/board/list.do?category=${param.category }&page=1&amount=${pagination.amount }&name=${criteria.name }&title=${criteria.title }">&laquo;</a></li>
		  <c:if test="${1 < pagination.start }"><li class="page-item"><a class="page-link" href="/winitech/board/list.do?category=${param.category }&page=${pagination.start - pagination.pageCnt}&name=${criteria.name }&title=${criteria.title }">&lt;</a></li></c:if>
		  <c:forEach var="num" begin="${pagination.start }" end="${pagination.end }">
		  	<li class="page-item"><a class="page-link" href="/winitech/board/list.do?category=${param.category }&page=${num }&amount=${pagination.amount }&name=${criteria.name }&title=${criteria.title }"><c:out value="${num }" /></a></li>
		  </c:forEach>
		  <c:if test="${pagination.end < pagination.realEnd }"><li class="page-item"><a class="page-link" href="/winitech/board/list.do?page=${pagination.end + 1}&name=${criteria.name }&title=${criteria.title }">&gt;</a></li></c:if>
		  <li class="page-item"><a class="page-link" href="/winitech/board/list.do?category=${param.category }&page=${pagination.realEnd }&amount=${pagination.amount }&name=${criteria.name }&title=${criteria.title }">&raquo;</a></li>
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
<!-- <script src="${pageContext.request.contextPath }/js/board-list.js"></script> -->
<script type="text/javascript">
document.addEventListener('click', (e) => {
	if(e.target.id == 'btn-search') {
		const category = document.getElementById('select-search');
		const keyword = document.getElementById('input-search');
		if(category.value == 'null') { alert('검색 카테고리를 선택해주세요.'); category.focus(); }
		else if(keyword.value == null || keyword.value == '') { alert('검색 키워드를 입력해주세요.'); keyword.focus(); }
		else { location.href = '/winitech/board/list.do?' + category.value + '=' + keyword.value; }
	}
});
</script>