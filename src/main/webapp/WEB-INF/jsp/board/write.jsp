<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<c:choose>
	<c:when test="${dto.iboard == 0 }"><h1 class="title">게시글 등록</h1></c:when>
	<c:otherwise><h1 class="title">게시글 수정</h1></c:otherwise>
</c:choose>
<form id="f" enctype="multipart/form-data">
	<table class="table">
	  <tbody>
	  <c:choose>
	  	<c:when test="${dto.iboard == 0 }"><tr><td>작성자</td><td><input type="text" class="form-control" id="name" value="${dto.name }" placeholder="닉네임을 입력해주세요." autocomplete="off"></td><td>비밀번호</td><td><input type="password" class="form-control" id="pwd" placeholder="비밀번호를 입력해주세요." autocomplete="off"></td></tr></c:when>
	  	<c:otherwise><tr><td>작성자</td><td colspan="2"><input type="text" class="form-control" id="name" value="${dto.name }" disabled="disabled"></td></tr></c:otherwise>
	  </c:choose>
	  <tr><td>제목</td><td colspan="3"><input type="text" class="form-control" id="title" maxlength="200" value="${dto.title }" placeholder="제목을 입력해주세요." autocomplete="off"></td></tr>
	  <tr><td colspan="4" style="height: 450px"><textarea class="form-control" id="contents" maxlength="1000" style="height: 450px; resize: none"><c:out value="${dto.contents }" /></textarea></td></tr>
	  <tr>
	  	<td colspan="4">
		  	<label class="form-label label-file-alert">첨부파일의 최대 크기는 10,485,760byte입니다.</label>
			<input class="form-control" type="file" id="input-file" multiple accept="image/*, .pdf, .xlsx">
			
			<!-- 첨부파일 드래그 기능(x) -->
			<!-- 
			<div id="div-file-drop">
				<p>첨부파일을 드래그 앤 드롭 하거나 클릭하여 업로드하세요.</p>
			  	<input class="form-control" type="file" id="input-file" multiple accept="image/*, .pdf, .xlsx">
			</div>
			-->
	    </td>
	  </tr>
      <!-- 
	  <tr><td colspan="4"><label class="form-label label-file-alert">첨부파일의 최대 크기는 10,485,760byte입니다.</label><input class="form-control" type="file" id="input-file" multiple accept="image/*, .pdf, .xlsx"></td></tr>
      -->
	  <c:if test="${dto.iboard != 0 }">
		  <c:if test="${fn:length(dto.files) != 0 }">
			  <c:forEach var="item" items="${dto.files }">
			  <tr><td colspan="4"><c:out value="${item.originalName }${item.ext } " /><span class="span-byte-text"><fmt:formatNumber value="${item.fileSize }" pattern="[#,### byte]" /></span><button type="button" class="btn btn-info btn-sm btn-file-delete" id="btn-file-delete" data-ifile="${item.ifile }">삭제</button></td></tr>
			  </c:forEach>
		  </c:if>
	  </c:if>
	  </tbody>
	</table>
</form>

<div class="div-btn-group-wrap">
	<c:choose>
		<c:when test="${dto.iboard == 0 }">
			<!-- 등록 버튼 클릭 후 목록으로 돌아갈 시 파라미터로 값을 넘겨주기 위해(검색어 유지) 분기문 처리 -->
			<c:if test="${param.name == null && param.title == null }"><button type="button" class="btn btn-primary btn-margin" id="btn-insert" data-iboard="${iboard }">등록</button></c:if>
		   	<c:if test="${param.name != null }"><button type="button" class="btn btn-primary btn-margin" id="btn-insert" data-iboard="${iboard }" data-name="${param.name }">등록</button></c:if>
			<c:if test="${param.title != null }"><button type="button" class="btn btn-primary btn-margin" id="btn-insert" data-iboard="${iboard }" data-title="${param.title }">등록</button></c:if>
		</c:when>
		<c:otherwise><button type="button" class="btn btn-warning btn-margin" id="btn-update" data-iboard="${dto.iboard }">수정</button></c:otherwise>
	</c:choose>
	
	<!-- 취소 버튼 클릭 시 파라미터로 값을 넘겨주기 위해(검색어 유지) 분기문 처리 -->
	<c:if test="${param.name == null && param.title == null }"><button type="button" class="btn btn-danger" id="btn-cancle">취소</button></c:if>
   	<c:if test="${param.name != null }"><button type="button" class="btn btn-danger" id="btn-cancle" data-name="${param.name }">취소</button></c:if>
	<c:if test="${param.title != null }"><button type="button" class="btn btn-danger" id="btn-cancle" data-title="${param.title }">취소</button></c:if>
</div>
</html>

<script src="${pageContext.request.contextPath }/js/board-write.js"></script>