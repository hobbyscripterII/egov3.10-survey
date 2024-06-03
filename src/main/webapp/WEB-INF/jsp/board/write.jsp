<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<c:choose>
	<c:when test="${dto.iboard == 0 }"><h1 class="title" id="category" data-category="${param.category }">게시글 등록</h1></c:when>
	<c:otherwise><h1 class="title">게시글 수정</h1></c:otherwise>
</c:choose>
<form id="f" enctype="multipart/form-data">
	<table class="table">
	  <tbody>
	  <tr><td>작성자</td><td colspan="3"><label class="col-form-label"><c:out value="${dto.name }" /></label></td></tr>
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
<!-- <script src="${pageContext.request.contextPath }/js/board-write.js"></script> -->
<script type="text/javascript">
let deleteIfileList = [];

document.addEventListener('click', (e) => {
	if(e.target.id == 'btn-file-delete') {
		if(confirm('해당 첨부파일을 삭제하시겠습니까?')) {
			// 타겟 이벤트 부모 노드 삭제
			let target = e.target;
			let parentNode = target.parentNode.parentNode;
			parentNode.remove();
			alert('첨부파일이 삭제되었습니다.');
			let deleteFileId = target.dataset.ifile; // 클라이언트가 삭제한 첨부파일 pk
			deleteIfileList.push(deleteFileId); // 배열에 담아 저장
		}
	}
	
	if(e.target.id == 'btn-update') {
		if(confirm('게시글을 수정하시겠습니까?')) {
    		let formData = new FormData(); // multipart/form-data
			let el = document.getElementById('btn-update');
			let iboard = el.dataset.iboard;
    		let title = $('#title').val();
    		let contents = $('#contents').val();
    		let dto = {"iboard" : iboard, "title" : title, "contents" : contents, "deleteIfileList" : deleteIfileList };
    		let files = document.getElementById('input-file').files;
    		
    		for(let i = 0; i < files.length; i++) {
    			let file = files[i];
    			formData.append("files", file);	
    		}
    		
    		formData.append("dto", new Blob([JSON.stringify(dto)], {type: "application/json"}));
    		
        	// 첨부파일 받을 수 있게 수정
		    $.ajax({
		        type: 'post', url: '/survey/board/update.do', data: formData,
    	        contentType: false, // 전달 데이터 형식 / formData로 보낼 경우 명시 필수
    	        processData: false, // string 변환 여부 / formData로 보낼 경우 명시 필수
		        success: (data) => {
		        	if(data == 1) {
		        		if(confirm('게시글이 수정되었습니다. 수정된 게시글을 확인하러 가시겠습니까?')) { location.href = '/survey/board/view.do?category=1&iboard=' + iboard; }
		        		else { alert('게시글 목록으로 이동합니다.'); location.href = '/survey/board/list.do?category=1'; }}
		        	else { alert('게시글 수정에 실패하였습니다. 잠시 후 다시 시도해주세요.'); }
		        },
		        error: (x) => { console.log(x); }
		    })
		}
	}
	
    if(e.target.id == 'btn-cancle') {
    	if(confirm('작성을 취소하시겠습니까?')) {
    		const NAME = document.getElementById('btn-cancle').dataset.name;
    		const TITLE = document.getElementById('btn-cancle').dataset.title;
    		let suffixUrl = 'null';
    		alert('작성이 취소되었습니다.');
    		if(NAME != undefined) { suffixUrl = 'name=' + NAME; }
    		else if(TITLE != undefined) { suffixUrl = 'title=' + TITLE; }
    		location.href = '/survey/board/list.do??category=1' + suffixUrl;
    	}
    }
    
    if(e.target.id == 'btn-insert') {
    	let category = $('#category').data('category');
    	let title = $('#title');
    	let contents = $('#contents');
    	let el = document.getElementById('btn-insert');
    	let code = el.dataset.iboard;

    	if(!contents.val()) { alert('내용을 입력해주세요.'); contents.focus(); }
    	else {
    		let formData = new FormData();
    		let dto = {category: category, code : code, title : title.val(), contents : contents.val()};
//    		let file = document.getElementById('input-file').files[0]; // 단일 파일 업로드 시
    		let files = document.getElementById('input-file').files;
    		
    		const LIMIT_FILE_COUNT = 3;
    		let fileLength = files.length;

    		if(fileLength > LIMIT_FILE_COUNT) {
    			alert('첨부파일 최대 개수는 3개입니다. 수정 후 다시 등록해주세요.\n현재 첨부파일 개수: ' + fileLength);
				return false;
    		} else {
        		for(let i = 0; i < fileLength; i++) {
        			let file = files[i];
        			
        			// 예외처리
        			const FILE = file;
        			const FILE_NAME = file.name;
        			const FILE_SIZE = file.size;
        			const FILE_MAX_SIZE = 10 * 1024 * 1024;
        			const FILE_TYPE = file.type;
        			
        			// console.log('FILE_SIZE = ', FILE_SIZE, 'FILE_MAX_SIZE = ', FILE_MAX_SIZE);
        			
        			if(FILE_SIZE > FILE_MAX_SIZE) {
        				alert('최대 파일 크기를 초과했습니다.\n최대 파일 크기는 ' + FILE_MAX_SIZE + 'byte입니다.\n\n해당 파일명: ' + FILE_NAME + '\n해당 파일 크기: ' + FILE_SIZE + 'byte');
        				return false;
        			}
					
					// 파일 확장자 체크
					const arr = ['image/*', '.pdf', '.xlsx'];
					console.log('arr = ', arr);
        			
	    			formData.append("files", file);
        		}
        		
        		formData.append("dto", new Blob([JSON.stringify(dto)], {type: "application/json"}));
//        		formData.append("file", file); // 단일 파일 업로드 시

        		$.ajax({
        	        type: 'post',
        	        url: '/survey/board/write.do',
        	        contentType: false, // 전달 데이터 형식 / formData로 보낼 경우 명시 필수
        	        processData: false, // string 변환 여부 / formData로 보낼 경우 명시 필수
        	        data: formData,
        	        success: (data) => {
						// data - ajax 반환 값으로 넘어온 HashMap<key - property, value - MSG>로 구성        	        	
        	        	const MSG = data.MSG;
						const INTERNAL_SERVER_ERROR = -5;
//						const PASHED_PASSWORD_FAIL_ERROR = -1;
						const VALIDATION_ERROR = -2;
						
       	        		if(MSG > 0) {
        	        			if(confirm('게시글 등록이 완료되었습니다. 등록한 글을 확인하러가시겠습니까?')) {
        	        				const IBOARD = data.IBOARD;
        	        				location.href = '/survey/board/view.do?category=1&iboard=' + IBOARD;
        	        			} else {
            	        			alert('게시판 목록으로 이동합니다.');
    	       	            		const NAME = document.getElementById('btn-insert').dataset.name;
    	       	            		const TITLE = document.getElementById('btn-insert').dataset.title;
    	       	            		let suffixUrl = '?null';
    	       	            		
    	       	            		if(NAME != undefined) { suffixUrl = 'name=' + NAME; }
    	       	            		else if(TITLE != undefined) { suffixUrl = 'title=' + TITLE; }
    	       	            		location.href = '/survey/board/list.do?category=1&' + suffixUrl;
        	        			}
        	        	} else {
        	        		if(MSG == -2) {
            	        		const ERRORS = data.ERRORS;
            	        		let alertMsg = '[' + MSG + '] 유효성 검증에 실패했습니다.\n\n';
            	        		for(let item in ERRORS) { alertMsg += item + ': ' + ERRORS[item] + '\n'; }
            	        		alert(alertMsg); // 유효성 검증 실패한 필드 + 메세지 출력
            	        		return false;
        	        		} else if(MSG == -4) {
        	        			alert('[' + MSG + '] 일시적 오류로 인해 게시글 등록에 실패했습니다. 잠시 후 다시 시도해주세요.');
        	        			return false;
        	        		} else if(MSG == -5) {
        	        			alert('[' + MSG + '] 일시적 오류로 인해 게시글 등록에 실패했습니다. 잠시 후 다시 시도해주세요.');
        	        			return false;
        	        		}
        	        	}
        	        }, 
        	        error: (x) => { console.log(x); }
        	     })
        	 }
    	}
    }
});
</script>