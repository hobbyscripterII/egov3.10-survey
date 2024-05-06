<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<style>
#body { height: auto; }
</style>
<body>
<h1 class="title" data-iboard="${vo.iboard }">사진 게시글 읽기</h1>
<table class="table">
  <tbody>
	  <tr class="tr-style"><td>작성자</td><td><c:out value="${vo.name }" /></td></tr>
	  <tr class="tr-style"><td>제목</td><td><c:out value="${vo.title }" /></td></tr>
	  <tr class="tr-style"><td>작성일</td><td><fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" var="createdAt" value="${vo.createdAt }" /><fmt:formatDate pattern="yyyy년 MM월 dd일 HH:mm" value="${createdAt }" /></td></tr>
	  <tr><td colspan="2" style="height: 500px" id="contents"></td></tr>
  </tbody>
</table>
<div class="div-btn-group-wrap">
	<c:if test="${sessionScope.IUSER == vo.iuser }">
		<button type="button" class="btn btn-warning btn-margin" id="btn-update">수정</button>
		<button type="button" class="btn btn-danger btn-margin" id="btn-delete">삭제</button>
	</c:if>
	<button type="button" class="btn btn-primary" onclick="location.href = '/winitech/photo/list.do'">목록</button>
</div>
</body>
</html>
<!-- <script src="${pageContext.request.contextPath }/js/board-read.js"></script> -->
<script type="text/javascript">
let btnUpdate = document.getElementById('btn-update');
let btnDelete = document.getElementById('btn-delete');
let iboard = document.querySelector('.title').dataset.iboard;
let contents = document.getElementById('contents');
contents.innerHTML = `${vo.contents }`; // 내용은 따로 출력 / `` 백틱키로 감싸주지 않으면 태그 자체로 인식하므로 감싸주기

btnUpdate.addEventListener('click', () => { location.href = '/winitech/photo/update.do?iboard=' + iboard; });

btnDelete.addEventListener('click', () => {
	if (confirm('게시글을 삭제하시겠습니까?')) {
		$.ajax({
			type: 'post',
			url: '/winitech/photo/delete.do',
			data: { "iboard": iboard },
			success: (data) => {
				if (data == 1) { alert('게시글 삭제가 완료되었습니다. 게시글 목록으로 이동합니다.'); location.href = '/winitech/photo/list.do?category=2'; }
				else { alert('게시글 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.'); return false; }},
			error: (x) => { console.log(x); }
		});
	}
});
</script>