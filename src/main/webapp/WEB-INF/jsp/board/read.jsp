<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<body>
<section>
<div class="modal" id="exampleModal">
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
        <p><input type="password" class="form-control" id="pwd" placeholder="비밀번호 입력란" autocomplete="off"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="pwdChk()">확인</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>

<h1 class="title">게시글 읽기</h1>
<table class="table">
  <tbody>
  <tr class="tr-style"><td>작성자</td><td><c:out value="${vo.name }" /></td></tr>
  <tr class="tr-style"><td>제목</td><td><c:out value="${vo.title }" /></td></tr>
  <tr class="tr-style"><td>작성일</td><td><fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" var="createdAt" value="${vo.createdAt }" /><fmt:formatDate pattern="yyyy년 MM월 dd일 HH:mm" value="${createdAt }" /></td></tr>
  <tr><td colspan="2" style="height: 500px"><c:out value="${vo.contents }" /></td></tr>
  <c:if test="${fn:length(vo.files) != 0 }">
  <tr style="text-align: center">
  <td rowspan="${fn:length(vo.files) + 1}">첨부파일<span class="span-byte-text"><c:out value="(${fn:length(vo.files) })" /></span></td>
  </tr>
  <c:forEach var="item" items="${vo.files }">
  <tr>
  <td><a href="/winitech/board/download.do?ifile=${item.ifile }"><c:out value="${item.originalName }${item.ext } " /></a><span class="span-byte-text"><fmt:formatNumber value="${item.fileSize }" pattern="[#,### byte]" /></span></td>
  </tr>
  </c:forEach>
  </c:if>
  </tbody>
</table>
<div class="div-btn-group-wrap">
	<!-- 답변글 여부가 'Y'가 아니거나 답변글 식별코드가 '0'인 경우에는 답변글 버튼을 출력한다.  -->
	<c:choose>
		<c:when test="${vo.replyFl eq 'N' && vo.code == 0 }">
			<button type="button" class="btn btn-success btn-margin" id="btn-reply" data-iboard="${vo.iboard }">답글</button>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	<button type="button" class="btn btn-warning btn-margin" id="btn-update" data-iboard="${vo.iboard }">수정</button>
	<button type="button" class="btn btn-danger btn-margin" id="btn-delete" data-iboard="${vo.iboard }" >삭제</button>
	<button type="button" class="btn btn-primary" onclick="location.href = '/winitech/board/list.do'">목록</button>
</div>
</section>
</body>
</html>

<script src="${pageContext.request.contextPath }/js/board-read.js"></script>