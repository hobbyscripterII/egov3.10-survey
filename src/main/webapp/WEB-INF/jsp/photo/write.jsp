<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<style>
#div-custom-form-control { padding: .375rem .75rem; border: 1px solid #dee2e6; border-radius: 0.375rem; height: 850px; /* text 창 처럼 보이기위한 속임수 */ cursor: text; overflow: auto; }
textarea { width: 100%; height: 30px; line-height: 30px; border: none; border-bottom: 1px dotted #000; margin-top: 4px; outline: none; resize: none; overflow: hidden; } /* border-bottom: 1px dotted #000; */
#icon-image-upload { margin-top: 3px; cursor: pointer; }
.div-thumbnail-chioce-form { padding: 5px 5px 5px 10px; cursor: pointer; position: relative; width: 3rem; top: 34px; background-color: white; }
.icon-thumbnail-delete { cursor: pointer; position: relative; top: 59px; left: 3.5rem; z-index: 9999; }
</style>

<div class="icon-thumbnail-delete"></div>

<div style="display: none" class="div-thumbnail-chioce-form"></div>
<c:choose>
	<c:when test="${empty dto.contents }">
		<h1 class="title" data-category="${param.category }" >사진 게시글 등록</h1>
	</c:when>
	<c:otherwise>
		<h1 class="title" data-category="${param.category }" >사진 게시글 수정</h1>
	</c:otherwise>
</c:choose>
<form id="f" enctype="multipart/form-data">
	<table class="table">
	  <tbody>
	  <tr><td>작성자</td><td colspan="3"><label class="col-form-label"><c:out value="${dto.name }" /></label></td></tr>
	  <tr><td>제목</td><td colspan="3"><input type="text" class="form-control" id="title" maxlength="200" value="${dto.title }" placeholder="제목을 입력해주세요." autocomplete="off"></td></tr>
	  <tr>
		  <td colspan="4">
			  <div>
				  <img id="icon-image-upload" alt="" title="이미지 업로드 아이콘" src="${pageContext.request.contextPath }/images/egovframework/winitech/icon-image-upload.png" onchange="preview(this)">
				  <input class="form-control" type="file" id="input-file" accept="image/*" multiple style="display: none">
			  </div>
		  </td>
	  <tr>
		  <td colspan="4" style="height: 450px">
		  	<div id="div-custom-form-control"></div>
		  </td>
	  </tr>
	  </tbody>
	</table>
</form>

<div class="div-btn-group-wrap">
	<!-- 등록 버튼 클릭 후 목록으로 돌아갈 시 파라미터로 값을 넘겨주기 위해(검색어 유지) 분기문 처리 -->
	<c:if test="${param.name == null && param.title == null }"><button type="button" class="btn btn-primary btn-margin" id="btn-insert" data-iboard="${dto.iboard }">등록</button></c:if>
   	<c:if test="${param.name != null }"><button type="button" class="btn btn-primary btn-margin" id="btn-insert" data-iboard="${dto.iboard }" data-name="${param.name }">등록</button></c:if>
	<c:if test="${param.title != null }"><button type="button" class="btn btn-primary btn-margin" id="btn-insert" data-iboard="${dto.iboard }" data-title="${param.title }">등록</button></c:if>
	
	<!-- 취소 버튼 클릭 시 파라미터로 값을 넘겨주기 위해(검색어 유지) 분기문 처리 -->
	<c:if test="${param.name == null && param.title == null }"><button type="button" class="btn btn-danger" id="btn-cancle">취소</button></c:if>
   	<c:if test="${param.name != null }"><button type="button" class="btn btn-danger" id="btn-cancle" data-name="${param.name }">취소</button></c:if>
	<c:if test="${param.title != null }"><button type="button" class="btn btn-danger" id="btn-cancle" data-title="${param.title }">취소</button></c:if>
</div>
</html>
<script type="text/javascript">
// [photo/write js 함수]
let form = document.getElementById('div-custom-form-control');
let iconImageUpload = document.getElementById('icon-image-upload');
let inputFile = document.getElementById('input-file');
let imgPreview = document.querySelector('img-preview');
let btnInsert = document.getElementById('btn-insert');
let btnCancle = document.getElementById('btn-cancle');
let iboard = document.getElementById('btn-insert').dataset.iboard; // 게시글 등록 버튼 눌렀을 때 생기는 pk
let contents = `${dto.contents }`;
let thumbnail = `${dto.thumbnail }`; // 대표 썸네일 pk 초기화
let deleteFileArr = []; // 추후 처리

function imgDelete(e) {
	if(confirm('이미지를 삭제하시겠습니까?')) {
		let targetNode = e.nextSibling.nextSibling;
		let ifile = targetNode.dataset.ifile;
		let src_ = targetNode.getAttribute('src');
		let src = src_.replaceAll('/winitech/img/', '');
		let parentNode = targetNode.parentNode;
		
		$.ajax({
			type: 'post',
			url: '/winitech/photo/file-delete.do',
			data: { "iboard": iboard, "src": src },
			success: (data) => {
				if (data == 1) { parentNode.remove(); }
				else { console.log('이미지 삭제에 실패했습니다.'); }},
			error: (x) => { console.log(x); }
		});
	}
}

// [게시글 작성 취소 버튼 클릭 이벤트]
btnCancle.addEventListener('click', () => {
   	if(confirm('작성을 취소하시겠습니까?')) {
   		alert('작성이 취소되었습니다.');
   		location.href = '/winitech/photo/list.do';
   	}
});

// [수정 시 발생 이벤트]
if(contents != '') { // dto contents가 빈 문자열이 아니라면 이미 작성한 게시글이므로 form div에 해당 게시글 출력(수정 작업)
	// p 태그 다시 textarea로 변경 작업
	let prefixReplace = contents.replaceAll('<p>', '<textarea>'); // replaceAll - 왼쪽 인자 값에 해당하는 모든 문자열을 오른쪽 인자 값으로 변경 / 제일 앞에있는 거 변경할 때는 replace
	let suffixReplace = prefixReplace.replaceAll('</p>', '</textarea>');
	form.innerHTML = suffixReplace;
	
	// item - img-preview 클래스를 가진 요소 배열
	$('.img-preview').each((idx, item) => {
    	let newWrapDiv = document.createElement('div'); // 이미지 / 대표 이미지 선택 폼 감싸줄 wrap용 div 생성
    	let newThumbnailChioceForm = document.createElement('div'); // 대표 이미지 선택 폼 전용 div 생성
    	let newThumbnailChioceText = document.createTextNode('대표'); // 대표 이미지 선택 폼에 넣을 텍스트 노드 추가
    	newThumbnailChioceForm.classList.add('div-thumbnail-chioce-form'); // wrap div에 css 먹인 class 추가
    	newThumbnailChioceForm.appendChild(newThumbnailChioceText); // wrap div에 텍스트 노드 추가
		let newDeleteIcon = document.createElement('img'); // img 요소 새로 생성
		newDeleteIcon.classList.add('icon-thumbnail-delete');
		newDeleteIcon.setAttribute('src', '${pageContext.request.contextPath }/images/egovframework/winitech/icon-delete.png'); // src 속성 생성 후 ajax 리턴 값으로 받아온 값을 넣어줌
		newDeleteIcon.setAttribute('onclick', 'imgDelete(this)'); // 아이콘 클릭 시 ajax 연결 후 해당 파일 삭제
		newWrapDiv.appendChild(newDeleteIcon);
    	newWrapDiv.appendChild(newThumbnailChioceForm); // 대표 이미지 선택 폼 div를 wrap div로 이동
		newWrapDiv.appendChild(item); // 이미지 class를 wrap div로 이동
		form.appendChild(newWrapDiv); // text div로 wrap div를 이동시킴 
	});
}

form.addEventListener('click', (e) => {
	let targetNode = e.target; // 이벤트 발생 타겟
	let nextSiblingNode = targetNode.nextSibling; // 발생 타겟의 다음 노드
	let previousSiblingNode = targetNode.previousSibling; // 발생 타겟의 이전 노드
	let parentNode = targetNode.parentNode;
	let newTextarea = document.createElement('textarea'); // text div에 새로 삽입하기 위한 textarea 생성
	
	if(targetNode.className == 'div-thumbnail-chioce-form') {
		let divThumbnailChioceForms = document.getElementsByClassName('div-thumbnail-chioce-form');
		
		for(let i = 0; i < divThumbnailChioceForms.length; i++) {
			divThumbnailChioceForms[i].style.backgroundColor = 'white';
		}
		
		console.log('대표 이미지를 선택합니다.');
		targetNode.style.backgroundColor = '#03C04A';
		thumbnail = nextSiblingNode.getAttribute('data-ifile');
	}
	
	// 앞 뒤로 이미지만 있을 때 / 다음 형제 노드가 없을 때 새로운 textarea 생성
	if(targetNode.className == 'img-preview' & !nextSiblingNode) {
		targetNode.after(newTextarea);
	}
	
	// text div 어디를 클릭하더라도 마지막 textarea 폼으로 이동
	if(e.target.id == 'div-custom-form-control') { form.lastChild.focus(); }
});

//[text div 관련 이벤트 로직]
//text div 클릭 시 새로운 textarea 폼 생성(2번 실행 x)
form.addEventListener('click', (e) => {
	let newTextarea = document.createElement('textarea'); // text div에 새로 삽입하기 위한 textarea 생성
	newTextarea.classList.add('textarea-custom-form-control'); // 해당 textarea에 css 먹임
	form.appendChild(newTextarea); // textarea를 text div의 마지막 자식 요소에 덧붙임
}, {once : true}); // text 폼 클릭 시 input 폼 한번만 생성하기 위해 once 속성을 true로 지정

// 게시글 '등록' 버튼 누를 때 이미 insert 되었으므로 이후 작업들은 다 update임
// 따라서 아래 로직을 재사용함
btnInsert.addEventListener('click', (e) => {
	let title = $('#title'); // 제목
	let imgPreview = $('.img-preview');
	let textareas = $('textarea');
	let contents = ''; // 빈 값 초기화(null, undefined) 안나오게 처리하기 위함
	
	let length = imgPreview.length + textareas.length; // 작성한 게시글 내용을 다 처리하기 위해서 배열을 img 태그 개수 + textarea 개수로 계산
	
	if(!title.val()) { alert('제목을 입력해주세요.'); title.focus(); }
	else {
		// >>>>> 게시글 내용 담는 작업 시작
		let contentsTempArr = []; // 게시글 내용을 순서대로 담을 임시 배열 생성
		
		for (let i = 0; i < length; i++) {
			// outerHTML - 요소 전체를 html 문자열로 반환 / [object HTMLImageElement] 출력 방지
		    if (imgPreview[i]) { contentsTempArr.push(imgPreview[i].outerHTML);}
		 	// textarea 문자열만 추출 -> p 태그로 감싸서 처리
		    if (textareas[i]) { contentsTempArr.push('<p>' + textareas[i].value.trim() + '</p>'); }
		}

		// 번갈아가며 담은 배열을 순회하며 contents에 추가
		for (let content of contentsTempArr) { contents += content; }
		
	    contents = contents.trim(); // 값 제대입 / 앞에 빈 값 초기화했던 공백 제거
		// >>>>> 게시글 내용 담는 작업 종료
		
	    if(contents == '') { alert('내용을 입력해주세요.'); return false; }
	    else if(thumbnail == '0') { alert('대표 이미지를 지정해주세요.'); return false; }
	    else {
			let dto = {iboard : iboard, title : title.val(), contents : contents, thumbnail : thumbnail};
			
			$.ajax({
		        type: 'post',
		        url: '/winitech/photo/update.do',
		        data :  JSON.stringify(dto),
		        contentType : 'application/json',
		        success: (data) => {
		        	const SUCCESS = 1;
		        	if(data == SUCCESS) { if(confirm('게시글 등록에 성공했습니다. 등록한 글을 확인하러 가시겠습니까?')) { location.href ='/winitech/photo/view.do?iboard=' + iboard; } } 
		        	else { alert('게시글 등록에 실패하였습니다. 잠시 후 다시 시도해주세요.'); }}, 
		        error: (x) => { console.log(x); }
		     })
	    }
	}
});

// =====

// [이미지 업로드 아이콘 클릭 이벤트]
// 클릭 시 input file이 클릭됨(input file 폼은 display none으로 처리)
iconImageUpload.addEventListener('click', () => { inputFile.click(); });

inputFile.addEventListener('change', (e) => {
	let formData = new FormData();
	let files = e.currentTarget.files; // change evnet 발생 시 첨부된 이미지 목록 배열
	let fileLength = files.length;
	
	for(let i = 0; i < fileLength; i++) {
		let file = files[i];
		
		// 이미지 첨부 시 image 유효성 검증
		if(!file.type.match('image/.*')) { alert('이미지 파일만 업로드 가능합니다.'); return; }
		
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
		
		formData.append("files", file); // 반복문 돌릴 때 마다 files에 이미지를 담음 / 서버에서는 @RequestPart MultipartFile[] 배열로 받을 수 있음
	}
	
	formData.append("iboard", new Blob([JSON.stringify(iboard)], {type: "application/json"}));
	
	$.ajax({
        type: 'post',
        url: '/winitech/photo/fileupload.do',
        contentType: false, // 전달 데이터 형식 / formData로 보낼 경우 명시 필수
        processData: false, // string 변환 여부 / formData로 보낼 경우 명시 필수
        data: formData,
        success: (data) => {
        	let map = new Map(Object.entries(data));
        
        	// key - insert 후 반환된 이미지 pk
        	// value - 실제 업로드 경로에있는 UUID + 확장자
        	for(let [key, value] of map) {
	        	let src = '/winitech/img/' + value; // html 태그로 출력하기 위함 / /winitech/img/ - 실제 경로
	        	let newWrapDiv = document.createElement('div');
	        	let newThumbnailChioceForm = document.createElement('div');
	        	let newThumbnailChioceText = document.createTextNode('대표');
	        	newThumbnailChioceForm.classList.add('div-thumbnail-chioce-form');
	        	newThumbnailChioceForm.appendChild(newThumbnailChioceText);
				
	        	let newImg = document.createElement('img'); // img 요소 새로 생성
				newImg.setAttribute('src', src); // src 속성 생성 후 ajax 리턴 값으로 받아온 값을 넣어줌
				newImg.setAttribute('data-ifile', key);
				newImg.classList.add('img-preview');
				
				let newDeleteIcon = document.createElement('img'); // img 요소 새로 생성
				newDeleteIcon.classList.add('icon-thumbnail-delete');
				newDeleteIcon.setAttribute('src', '${pageContext.request.contextPath }/images/egovframework/winitech/icon-delete.png'); // src 속성 생성 후 ajax 리턴 값으로 받아온 값을 넣어줌
				newDeleteIcon.setAttribute('onclick', 'imgDelete(this)'); // 아이콘 클릭 시 ajax 연결 후 해당 파일 삭제
				newWrapDiv.appendChild(newDeleteIcon);
				
				newWrapDiv.appendChild(newThumbnailChioceForm);
				newWrapDiv.appendChild(newImg);
				form.appendChild(newWrapDiv);
        	}
        },
        error: (x) => { console.log(x); }
     })
});

form.addEventListener('keyup', (e) => { // keyup - 키보드에서 손 뗐을 때 실행
	let targetNode = e.target;
	let previousSiblingNode = e.target.previousSibling;
	let nextSiblingNode = e.target.nextSibling;
	
	// textarea 키보드 커서 제일 마지막에 위치하기 위한 작업
	// 방향키에 따라 포커즈 이동(노드 이동) // 방향키는 keyup, keydown만 가능
	/*
	let target;
	if(e.code == 'ArrowDown' || e.code == 'ArrowRight') {
		target = nextSiblingNode;
		target.focus();
	} else if(e.code == 'ArrowUp' || e.code == 'ArrowLeft') {
		target = previousSiblingNode;
		target.focus();
	}
	*/
	
	// textarea 자동 높이 조절
	/*
	let target = e.target;
	target.style.height = target.scrollHeight + 'px';
	*/
	
});

form.addEventListener('keydown', (e) => { // keydown - 키보드 눌렀을 때 실행 / 누르고 있을 때 한번만 실행됨
	let targetNode = e.target;
	let previousSiblingNode = e.target.previousSibling;
	let nextSiblingNode = e.target.nextSibling;

	// backspace 키 누를 경우 발생
	if(e.code == 'Backspace') {
		let thisTextValue = e.target.value; // backspace 이벤트를 발생시킨 textarea에 입력된 value
		
		// 제일 앞에 textarea를 지우고 싶을 때
		if(!previousSiblingNode && nextSiblingNode.nodeName == 'TEXTAREA') { // nodeName은 무조건 대문자
			targetNode.remove();
		}
		
		if(!thisTextValue) {
			let previousSiblingNode = e.target.previousSibling; // 이벤트 발생 타겟 이전 형제 노드
			console.log('더 이상 입력된 텍스트가 없습니다.'); // backspace 발생한 해당 타겟에 입력된 text가 없을 경우 해당 텍스트 콘솔 출력
			
			if(previousSiblingNode == null) {
				console.log('이전 textarea 폼이 없습니다. 현재 textarea를 유지합니다.'); e.target.focus(); }
			else {
				console.log('현재 textarea 폼을 삭제합니다.');
				e.target.remove(); // 이벤트 발생 타겟 노드 삭제
				previousSiblingNode.focus(); // 이전(마지막) 형제 노드에 포커즈 맞춤
				
				// textarea 키보드 커서 제일 마지막에 위치하기 위한 작업
				let temp = previousSiblingNode.value;
				previousSiblingNode.value = '';
				previousSiblingNode.value = temp;
			}
		}
	}
});

// text div에서 enter event 발생 시 textarea 폼 생성 이벤트 실행
// keyup, keydown은 한글적고 enter 누르면 2번 인식(한글 조합 입력기 관련)으로 사용 x
form.addEventListener('keypress', (e) => { // keypress - 키보드 눌렀을 때 실행 / 누르고 있을 때 계속 실행됨
	let targetNode = e.target;
	let previousSiblingNode = e.target.previousSibling;
	let nextSiblingNode = e.target.nextSibling;
	
	if(e.code == 'Enter') { // text 폼에서 enter를 눌렀을 경우에만 발생 / 대소문자 구분 주의
		let newTextarea = document.createElement('textarea');
		targetNode.after(newTextarea);
		newTextarea.focus(); // 새로 생긴 textarea 창에 바로 입력할 수 있게 focus 적용
	}
});
</script>