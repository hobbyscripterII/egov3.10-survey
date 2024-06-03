<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<style> #p-pwd-chk-msg { color: red; font-weight: bold; font-size: 13px; height: 20px; } </style>
<body>
<div class="modal" id="modal-pwd-chk">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">비밀번호 확인</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
          <span aria-hidden="true"></span>
        </button>
      </div>
      <div class="modal-body">
        <p>해당 게시글의 비밀번호를 입력해주세요.</p>
        <p id="p-pwd-chk-msg"></p>
        <p><input type="password" class="form-control" id="pwd" placeholder="비밀번호" autocomplete="off"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btn-success">확인</button>
        <button type="button" class="btn btn-secondary" id="btn-modal-close">닫기</button>
      </div>
    </div>
  </div>
</div>

<h1 class="title" data-iboard="${VO.iboard }">게시글 읽기</h1>
<table class="table">
  <tbody>
  <tr class="tr-style"><td>작성자</td><td><c:out value="${VO.name }" /></td></tr>
  <tr class="tr-style"><td>제목</td><td><c:out value="${VO.title }" /></td></tr>
  <tr class="tr-style"><td>작성일</td><td><fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" var="createdAt" value="${VO.createdAt }" /><fmt:formatDate pattern="yyyy년 MM월 dd일 HH:mm" value="${createdAt }" /></td></tr>
  <tr><td colspan="2" style="height: 500px"><c:out value="${VO.contents }" /></td></tr>
  <c:if test="${fn:length(VO.files) != 0 }">
  <tr style="text-align: center">
  <td rowspan="${fn:length(VO.files) + 1}">첨부파일<span class="span-byte-text"><c:out value="(${fn:length(VO.files) })" /></span></td>
  </tr>
  <c:forEach var="item" items="${VO.files }">
  <tr>
  <td><a href="/survey/board/download.do?ifile=${item.ifile }"><c:out value="${item.originalName }${item.ext } " /></a><span class="span-byte-text"><fmt:formatNumber value="${item.fileSize }" pattern="[#,### byte]" /></span></td>
  </tr>
  </c:forEach>
  </c:if>
  </tbody>
</table>
<div class="div-btn-group-wrap">
	<!-- 답변글 여부가 'Y'가 아니거나 답변글 식별코드가 '0'이 아니거나 session에 저장된 회원 pk가 있을 경우에만 답변글 버튼을 출력한다.  -->
	<c:choose>
		<c:when test="${VO.replyFl eq 'N' && VO.code == 0 && not empty sessionScope.IUSER }">
			<button type="button" class="btn btn-success btn-margin" id="btn-reply" data-iboard="${VO.iboard }">답글</button>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	<c:if test="${sessionScope.IUSER == VO.iuser }">
	<button type="button" class="btn btn-warning btn-margin" id="btn-update">수정</button>
	<button type="button" class="btn btn-danger btn-margin" id="btn-delete">삭제</button>
	
	<!--
	<button type="button" class="btn btn-warning btn-margin" data-flag="update" onclick="pwdChk(this)">수정</button>
	<button type="button" class="btn btn-danger btn-margin" data-flag="delete" onclick="pwdChk(this)">삭제</button>
	-->
	</c:if>
	<button type="button" class="btn btn-primary" onclick="location.href = '/survey/board/list.do'">목록</button>
</div>
</body>
</html>
<!-- <script src="${pageContext.request.contextPath }/js/board-read.js"></script> -->
<script type="text/javascript">
let btnUpdate = document.getElementById('btn-update');
let btnDelete = document.getElementById('btn-delete');
let iboard = document.querySelector('.title').dataset.iboard;

btnUpdate.addEventListener('click', () => { location.href = '/survey/board/update.do?iboard=' + iboard; });

btnDelete.addEventListener('click', () => {
	if (confirm('(주의) 답변글이 있는 경우 답변글이 함께 삭제됩니다.\n삭제된 게시글은 복구할 수 없습니다. 정말로 삭제하시겠습니까?')) {
		$.ajax({
			type: 'post', url: '/survey/board/delete.do', data: { "iboard": iboard },
			success: (data) => {
				if (data == 1) { alert('게시글 삭제가 완료되었습니다. 게시글 목록으로 이동합니다.'); location.href = '/survey/board/list.do?category=1'; }
				else { alert('게시글 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.'); return false; }},
			error: (x) => { console.log(x); }
		})
	}
});

document.addEventListener('click', (e) => {
	if (e.target.id == 'btn-reply') {
		const el = document.getElementById('btn-reply');
		const iboard = el.dataset.iboard;
		location.href = '/survey/board/write.do?iboard=' + iboard;
	}
});
</script>