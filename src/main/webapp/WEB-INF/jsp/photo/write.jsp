<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<style>
#div-custom-form-control { padding: .375rem .75rem; border: 1px solid #dee2e6; border-radius: 0.375rem; height: 850px; /* text 창 처럼 보이기위한 속임수 */ cursor: text; overflow: auto; }
.textarea-custom-form-control { width: 100%; height: 30px; line-height: 30px; /* border: none; */ border: 1px solid gray; margin-top: 4px; outline: none; resize: none; }
#icon-image-upload { margin-top: 3px; cursor: pointer; }
.img-preview { cursor: pointer; }
</style>
<c:choose>
	<c:when test="${dto.iboard == 0 }"><h1 class="title">사진 게시글 등록</h1></c:when>
	<c:otherwise><h1 class="title">사진 게시글 수정</h1></c:otherwise>
</c:choose>
<form id="f" enctype="multipart/form-data">
	<table class="table">
	  <tbody>
	  <tr><td>작성자</td><td colspan="3"><label class="col-form-label"><c:out value="${dto.name }" /></label></td></tr>
	  <tr><td>제목</td><td colspan="3"><input type="text" class="form-control" id="title" maxlength="200" value="${dto.title }" placeholder="제목을 입력해주세요." autocomplete="off"></td></tr>
	  <tr><td colspan="4">
	  <div>
	  <img id="icon-image-upload" alt="" title="이미지 업로드 아이콘" src="${pageContext.request.contextPath }/images/egovframework/winitech/icon-image-upload.png" onchange="preview(this)">
	  <input class="form-control" type="file" id="input-file" accept="image/*" multiple style="display: none">
	  </div>
	  </td>
	  <tr><td colspan="4" style="height: 450px">
	  <!-- custom textarea <script src="${pageContext.request.contextPath }/js/board-write.js"> -->
	  <div id="div-custom-form-control"></div>
	  <!-- default textarea -->
	  <!-- <textarea class="form-control" id="contents" maxlength="1000" style="height: 450px; resize: none"><c:out value="${dto.contents }" /></textarea> -->
	  </td></tr>
	  <!--
	  <tr>
	  	<td colspan="4">
		  	<label class="form-label label-file-alert">첨부파일의 최대 크기는 10,485,760byte입니다.</label>
			<input class="form-control" type="file" id="input-file" multiple accept="image/*, .pdf, .xlsx">
	    </td>
	  </tr>
	  -->
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
// [photo/write js 함수]
let form = document.getElementById('div-custom-form-control');
let iconImageUpload = document.getElementById('icon-image-upload');
let inputFile = document.getElementById('input-file');
let imgPreview = document.querySelector('img-preview');
let btnInsert = document.getElementById('btn-insert');

document.addEventListener('click', (e) => {
let nextSiblingNode = e.target.nextSibling;
let previousSiblingNode = e.target.previousSibling;
	
	// 다음 노드가 이미지이며 이전 노드 이름이 textarea이거나 null일 때 새로운 textarea 요소를 추가함
	if(nextSiblingNode.className == 'img-preview') {
		let newTextarea = document.createElement('textarea'); // text div에 새로 삽입하기 위한 textarea 생성
		newTextarea.classList.add('textarea-custom-form-control'); // 해당 textarea에 css 먹임
		e.target.after(newTextarea); // textarea를 현재 이벤트 타겟의 바로 뒤에 붙임
	}	
});

btnInsert.addEventListener('click', (e) => {
	let contents = ''; // 빈 값 초기화(null, undefined) 안나오게 처리하기 위함
	
    $('.img-preview').each((idx, img) => {
    	console.log(img);
    	contents += img;
    });
	
    $('textarea').each((idx, textarea) => {
    	let text = textarea.value.trim(); // trim - 앞 뒤 공백 제거
    	let pTagWrap = '<p>' + text + '</p>'; // 웹에디터 로직과 비슷하게 p 태그로 한번 감쌈
    	console.log(pTagWrap); // 확인용
    	contents += pTagWrap;
    });
	
    contents = contents.trim(); // 값 제대입 / 앞에 빈 값 초기화했던 공백 제거
    
    console.log(contents); // 테스트
    /* form.innerHTML = contents; */ // 테스트
});

// 이미지 업로드 아이콘 클릭 시 input file이 클릭됨
// input file 폼은 display none으로 처리
iconImageUpload.addEventListener('click', () => { inputFile.click(); });

inputFile.addEventListener('change', (e) => {
	let formData = new FormData();
	let files = e.currentTarget.files; // change evnet 발생 시 첨부된 이미지 목록 배열
	let fileLength = files.length;
	
	for(let i = 0; i < fileLength; i++) {
		let file = files[i];
		
		if(!file.type.match('image/.*')) {
			alert('이미지 파일만 업로드 해주세요.');
			return;
		}
		
		/*
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
		*/
		
		formData.append("files", file);
	}
	
	// 테스트 완료
	/*
	let iboard = document.getElementById('btn-insert').dataset.iboard;
	formData.append("iboard", new Blob([JSON.stringify(iboard)], {type: "application/json"}));
	
	$.ajax({
        type: 'post',
        url: '/winitech/photo/fileupload.do',
        contentType: false, // 전달 데이터 형식 / formData로 보낼 경우 명시 필수
        processData: false, // string 변환 여부 / formData로 보낼 경우 명시 필수
        data: formData,
        success: (data) => {
        	for(let file of data) {
	        	let src = '/winitech/img/' + file.savedName + file.ext;
				let newImg = document.createElement('img');
				newImg.setAttribute('src', src);
				newImg.classList.add('img-preview');
				form.appendChild(newImg);
        	}
        }, 
        error: (x) => { console.log(x); }
     })
	*/
});

// [text div 관련 이벤트 로직]
// text div 클릭 시 새로운 textarea 폼 생성(2번 실행 x)
form.addEventListener('click', (e) => {
	let newTextarea = document.createElement('textarea'); // text div에 새로 삽입하기 위한 textarea 생성
	newTextarea.classList.add('textarea-custom-form-control'); // 해당 textarea에 css 먹임
	form.appendChild(newTextarea); // textarea를 text div의 마지막 자식 요소에 덧붙임
	
	// text 폼 클릭 시 input 폼 한번만 생성하기 위해 once 속성을 true로 지정
}, {once : true});

form.addEventListener('click', (e) => {
	// text div 어디를 클릭하더라도 마지막 textarea 폼으로 이동
	if(e.target.id == 'div-custom-form-control') { form.lastChild.focus(); }
});

form.addEventListener('keyup', (e) => { // keyup - 키보드에서 손 뗐을 때 실행
	// textarea 자동 높이 조절
	/*
	let target = e.target;
	target.style.height = target.scrollHeight + 'px';
	*/
});

form.addEventListener('keydown', (e) => { // keydown - 키보드 눌렀을 때 실행 / 누르고 있을 때 한번만 실행됨
	// backspace 키 누를 경우 발생
	if(e.code == 'Backspace') {
		let thisTextValue = e.target.value; // backspace 이벤트를 발생시킨 textarea에 입력된 value
		
		if(!thisTextValue) {
			let previousSiblingNode = e.target.previousSibling; // 이벤트 발생 타겟 이전 형제 노드
			console.log('더 이상 입력된 텍스트가 없습니다.'); // backspace 발생한 해당 타겟에 입력된 text가 없을 경우 해당 텍스트 콘솔 출력
			
			if(previousSiblingNode == null) { console.log('이전 textarea 폼이 없습니다. 현재 textarea를 유지합니다.'); e.target.focus(); }
			else {
				console.log('현재 textarea 폼을 삭제합니다.');
				e.target.remove(); // 이벤트 발생 타겟 노드 삭제
				previousSiblingNode.focus(); // 이전(마지막) 형제 노드에 포커즈 맞춤
			}
		}
	}
});

// text div에서 enter event 발생 시 textarea 폼 생성 이벤트 실행
// keyup, keydown은 한글적고 enter 누르면 2번 인식(한글 조합 입력기 관련)으로 사용 x
form.addEventListener('keypress', (e) => { // keypress - 키보드 눌렀을 때 실행 / 누르고 있을 때 계속 실행됨
	if(e.code == 'Enter') { // text 폼에서 enter를 눌렀을 경우에만 발생 / 대소문자 구분 주의
		let newInput = document.createElement('textarea');
		newInput.classList.add('textarea-custom-form-control');
		form.appendChild(newInput);
		newInput.focus(); // 새로 생긴 textarea 창에 바로 입력할 수 있게 focus 적용
	}
	
	/*
	if(e.code == 'ControlLeft' && e.code == 'KeyA') {
		console.log('모든 내용을 삭제합니다.');
	}
	*/
});

// ==============================================================================================================
// [기존 board/write js 함수]
/*
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
			let pwd = prompt('게시글 수정을 위해 비밀번호를 입력해주세요.');
			let el = document.getElementById('btn-update');
			let iboard = el.dataset.iboard;
			let dto = {"iboard" : iboard, "pwd" : pwd};
			
		    $.ajax({
		        type: 'post', url: '/winitech/board/check.do', data: JSON.stringify(dto), dataType: 'text', contentType: 'application/json',
		        success: (data) => {
		        	if(data == 1) {
		        		let formData = new FormData(); // multipart/form-data
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
		    		        type: 'post', url: '/winitech/board/update.do', data: formData,
		        	        contentType: false, // 전달 데이터 형식 / formData로 보낼 경우 명시 필수
		        	        processData: false, // string 변환 여부 / formData로 보낼 경우 명시 필수
		    		        success: (data) => {
		    		        	if(data == 1) {
		    		        		if(confirm('게시글이 수정되었습니다. 수정된 게시글을 확인하러 가시겠습니까?')) { location.href = '/winitech/board/view.do?iboard=' + iboard; }
		    		        		else { alert('게시글 목록으로 이동합니다.'); location.href = '/winitech/board/list.do'; }}
		    		        	else { alert('게시글 수정에 실패하였습니다. 잠시 후 다시 시도해주세요.'); }
		    		        },
		    		        error: (x) => { console.log(x); }
		    		    })} 
		        	else { alert('비밀번호가 일치하지 않습니다. 확인 후 다시 시도해주세요.'); }},
		        error: (x) => { console.log(x); alert('게시글 수정에 실패하였습니다. 잠시 후 다시 시도해주세요.'); }
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
    		location.href = '/winitech/board/list.do?' + suffixUrl;
    	}
    }
    
    if(e.target.id == 'btn-insert') {
    	let name = $('#name');
    	let pwd = $('#pwd');
    	let title = $('#title');
    	let contents = $('#contents');
    	let el = document.getElementById('btn-insert');
    	let code = el.dataset.iboard;

    	if(!name.val()) { alert('닉네임을 입력해주세요.'); name.focus(); }
    	else if(!pwd.val()) { alert('패스워드를 입력해주세요.'); pwd.focus(); }
    	else if(!title.val()) { alert('제목을 입력해주세요.'); title.focus(); }
    	else if(!contents.val()) { alert('내용을 입력해주세요.'); contents.focus(); }
    	else {
    		let formData = new FormData();
    		let dto = {code : code, name : name.val(), pwd : pwd.val(), title : title.val(), contents : contents.val()};
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
        	        url: '/winitech/board/write.do',
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
        	        				location.href = '/winitech/board/view.do?iboard=' + IBOARD;
        	        			} else {
            	        			alert('게시판 목록으로 이동합니다.');
    	       	            		const NAME = document.getElementById('btn-insert').dataset.name;
    	       	            		const TITLE = document.getElementById('btn-insert').dataset.title;
    	       	            		let suffixUrl = '?null';
    	       	            		
    	       	            		if(NAME != undefined) { suffixUrl = 'name=' + NAME; }
    	       	            		else if(TITLE != undefined) { suffixUrl = 'title=' + TITLE; }
    	       	            		location.href = '/winitech/board/list.do' + suffixUrl;
        	        			}
        	        	} else {
        	        		if(MSG == -1) {
        	        			alert('[' + MSG + '] 패스워드 암호화에 실패했습니다. 잠시 후 다시 시도해주세요.');
        	        			return false;
        	        		} else if(MSG == -2) {
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
*/
</script>