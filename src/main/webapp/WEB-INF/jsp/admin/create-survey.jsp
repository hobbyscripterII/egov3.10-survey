<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<style>
body { background-color: #D5E6F5; }
#body { height: auto; }
.div-survey-form { height: auto; margin: 0 150px 30px 150px; }
.div-question-bottom-wrap { width: 100%; border-top: 1px solid #DEE2E6; padding-top: 10px; display: flex; justify-content: right; }
.div-question-center-wrap { border: 1px solid #DEE2E6; border-radius: 5px; margin-bottom: 20px; padding: 4px 2px 4px 7px; }
.div-question-top-wrap { display: flex; margin-bottom: 20px; flex-wrap: wrap; }
#question-name { width: 64%; border-radius: 0px; border: none; border-bottom: 1px solid #DEE2E6; margin-right: 10px; }
.question-format-short { border-bottom: 1px solid #DEE2E6; }
#response-format { width: 30%; }
.form-check-input { position: relative; top: 4px; }
#icon-x { cursor: pointer; }
#icon-photo { margin: 4px 8px 0 0; cursor: pointer; color: #000; }
.div-response-format-radio-wrap, .div-response-format-check-wrap, .div-response-format-select-wrap { margin: 4px 0 3px 0; }
.img-preview { width: 60%; margin-top: 20px; }
.form-switch .form-check-input { margin-left: 0px; }
.form-check .form-check-input { float: none; margin-right: 10px; }
.form-check-input { position: static; }
</style>
<body>
<h1 class="title">설문조사 작성</h1>
<form id="f" enctype="multipart/form-data">
<!-- 질문 생성 버튼(고정형) -->
<div id="div-create-question">
	<svg id="icon-create-question" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
	  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
	</svg>
	<svg id="icon-save" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check" viewBox="0 0 16 16">
	  <path d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425z"/>
	</svg>
</div>
<div class="div-survey-info-form-wrap">
	<div class="div-survey-form">
		<div class="div-question-form">
			<p class="p-survey-text"><c:out value="제목" /><input class="form-control" type="text" id="title" name="title"></p>
			<p class="p-survey-text"><c:out value="개요" /><input class="form-control" type="text" id="descript" name="descript"></p>
			<p class="p-survey-text"><c:out value="설문조사 시작 기간" /><input class="form-control" type="date" id="started-at" name="startedAt" max="9999-12-31"></p>
			<p class="p-survey-text"><c:out value="설문조사 종료 기간" /><input class="form-control" type="date" id="finished-at" name="finishedAt" max="9999-12-31"></p>
		</div>
	</div>
</div>
<div class="div-survey-form-wrap">
	<input type="hidden" id="question-ioption" value="1" />
	<div class="div-survey-form">
		<div class="div-question-form">
			<div class="div-question-top-wrap">
				<input class="form-control form-control-sm" type="text" placeholder="질문" id="question-name">
				<input class="form-control" type="file" id="input-file" accept="image/*" multiple style="display: none">
				<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-image" id="icon-photo" viewBox="0 0 16 16">
				  <path d="M6.002 5.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
				  <path d="M2.002 1a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2zm12 1a1 1 0 0 1 1 1v6.5l-3.777-1.947a.5.5 0 0 0-.577.093l-3.71 3.71-2.66-1.772a.5.5 0 0 0-.63.062L1.002 12V3a1 1 0 0 1 1-1z"/>
				</svg>
				
				<select class="form-select form-select-sm" id="response-format" onchange="responseFormatChange(this)">
					<option value="1">단답형</option>
					<option value="2">장문형</option>
					<option value="3">객관식(라디오)</option>
					<option value="4">객관식(셀렉트 박스)</option>
					<option value="5">체크박스</option>
				</select>
				<img class="img-preview">
				</img>
			</div>
			<div class="div-question-center-wrap">
				<input class="form-control" type="text" readonly>
			</div>
			<div class="div-question-bottom-wrap">
		        <div class="form-check form-switch">
		          <label class="form-check-label" for="requiredFl">필수 문항 설정</label>
		          <input class="form-check-input" type="checkbox" id="requiredFl">
		        </div>
				<img id="icon-trash" alt="" title="질문 삭제 아이콘" src="${pageContext.request.contextPath }/images/egovframework/icon-trash.png">
			</div>
		</div>
	</div>
</div>
</form>
</body>
<script type="text/javascript">
// 테이블에 저장된 응답 형식 식별 코드 변수 초기화
const SHORT = "1", LONG = "2", RADIO = "3", SELECT_BOX = "4", CHECK_BOX = "5";

// @ModelAttribute 방식
// dto에 자동으로 바인딩되기 때문에 name으로 dto 프로퍼티를 지정해줌
$('#icon-save').click((e) => {
	$('.div-survey-form-wrap').find('.div-question-form').each(function(idx, item) {
		let el = $(item).children();
		let top = el.closest('.div-question-top-wrap');
		let center = el.closest('.div-question-center-wrap');
		top.find('#response-format').attr('name', `list[\${idx }].iresponseformat`);
		top.children().closest('#question-name').attr('name', `list[\${idx }].name`);
		top.children().closest('#input-file').attr('name', `list[\${idx }].file`);
		$(item).closest('.div-survey-form-wrap').children().attr('name', `list[\${idx }].ioption`);
		$(item).find('#requiredFl').attr('name', `list[\${idx }].requiredFl`); // 필수 문항 설정 넣기
		let ioption = $(item).closest('.div-survey-form-wrap').children().val(); // 질문 응답 포맷
		
		if(ioption == LONG) {
			center.children().attr('name', `list[\${idx}].list[0].optionName`);
		} else if(ioption == SELECT_BOX) {
			center.find('.div-response-format-select-wrap label.form-check-label input').each(function(idx2, item) {
				$(item).attr('name', `list[\${idx}].list[\${idx2}].optionName`); // 질문 옵션 list[].list[].optionName 넣기
			});
		} else {
			center.find('input.form-control').each(function(idx2, item) {
				$(item).attr('name', `list[\${idx}].list[\${idx2}].optionName`); // 질문 옵션 list[].list[].optionName 넣기
			});
		}
	});
	
	let form = $('#f')[0];
	
	$.ajax({
       type: 'POST',
       url: '/survey/admin/insert-survey.do',
       data : new FormData(form), // dto로 바로 바인딩
       // data: formData, // @RequestPart
       contentType: false,
       processData: false,
       enctype: 'multipart/form-data',
       success: (data) => {
     	const SUCCESS = 1;
     	const FAIL = 0;
     	const RESULT = data.result;
     	const MSG = data.msg;
     	
     	if(RESULT == SUCCESS) {
     		alert(MSG + ' 설문조사 관리 페이지로 이동합니다.');
     		location.href = '/survey/admin/home.do';
     	}
    },
       error: (x) => { console.log(x); }
    })
});

// 질문 폼 생성 이후 셀렉트 박스 내 질문 응답 형식(단답형, 장문형 ...) 변경할 때
function responseFormatChange(item) {
	let responseFormat = item.value; // 사용자가 선택한 응답 형식 포맷 방식(셀렉트 박스로 선택 / value에 식별 코드 담음)
	let target = $(event.target).parent().next(); // 폼을 생성할 타겟 노드
	let format = '';
	
	switch(responseFormat) { // 응답 형식 문자열 초기화
		case SHORT :
			format = `<input class="form-control" type="text" readonly>`;
			break;
		case LONG :
			format = `<textarea class="form-control" rows="3" readonly style="resize: none"></textarea>`;
			break;
		case RADIO :
			format = `<div class="div-response-format-radio-wrap">
					  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
					  <img alt="" title="질문 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('radio')">
					  </div>
					  <div class="div-response-format-radio-wrap">
					  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
					  <img alt="" title="질문 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('radio')">
					  </div>
					  <button type="button" class="btn btn-link" id="btn-radio-create">질문 옵션 생성</button>`;
			break;
		case SELECT_BOX :
			format = `<div class="div-response-format-select-wrap">
					  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
					  <img alt="" title="질문 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('select')">
					  </div>
					  <div class="div-response-format-select-wrap">
					  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
					  <img alt="" title="질문 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('select')">
					  </div>
				  <button type="button" class="btn btn-link" id="btn-select-create">질문 옵션 생성</button>`;
			break;
		case CHECK_BOX :
			format = `<div class="div-response-format-check-wrap">
					  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
					  <img alt="" title="질문 응답 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('check')">
					  </div>
					  <div class="div-response-format-check-wrap">
					  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
					  <img alt="" title="질문 응답 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('check')">
					  </div>
					  <button type="button" class="btn btn-link" id="btn-check-create">질문 옵션 생성</button>`;
			break;
	}
	
	target.html(format);
	
	let dataInputTarget = target.closest('.div-survey-form-wrap').children();
	dataInputTarget.val(responseFormat);
}

$('#icon-create-question').click((e) => {
	let target = $('.div-survey-form-wrap:last'); // 검색된 요소 중 마지막 요소 찾기(반대로 첫번째 요소는 first 사용)
	let length = $('.div-survey-form-wrap').length + 1;
	
	let divSurveyForm = `<div class="div-survey-form-wrap">
						<input type="hidden" id="question-ioption" value="1" />
						<div class="div-survey-form">
							<div class="div-question-form">
								<div class="div-question-top-wrap">
									<input class="form-control form-control-sm" type="text" placeholder="질문" id="question-name">
									<input class="form-control" type="file" id="input-file" accept="image/*" multiple style="display: none">
									<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-image" id="icon-photo" viewBox="0 0 16 16">
									  <path d="M6.002 5.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
									  <path d="M2.002 1a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2zm12 1a1 1 0 0 1 1 1v6.5l-3.777-1.947a.5.5 0 0 0-.577.093l-3.71 3.71-2.66-1.772a.5.5 0 0 0-.63.062L1.002 12V3a1 1 0 0 1 1-1z"/>
									</svg>
									
									<select class="form-select form-select-sm" id="response-format" onchange="responseFormatChange(this)">
										<option value="1">단답형</option>
										<option value="2">장문형</option>
										<option value="3">객관식(라디오)</option>
										<option value="4">객관식(셀렉트 박스)</option>
										<option value="5">체크박스</option>
									</select>
									<img class="img-preview">
									</img>
								</div>
								<div class="div-question-center-wrap">
									<input class="form-control" type="text" readonly>
								</div>
								<div class="div-question-bottom-wrap">
						        <div class="form-check form-switch">
						          <label class="form-check-label" for="requiredFl">필수 문항 설정</label>
						          <input class="form-check-input" type="checkbox" id="requiredFl">
						        </div>
								<img id="icon-trash" alt="" title="질문 삭제 아이콘" src="${pageContext.request.contextPath }/images/egovframework/icon-trash.png">
							</div>
							</div>
						</div>
					</div>`;
	
	target.after(divSurveyForm);
});

// >>>>> 질문 폼 삭제 함수
$(document).on('click', '#icon-trash', (e) => { // 쓰레기통 아이콘 클릭 시 이벤트 발생
	let target = $(e.target).parent().parent().parent();
	
	if(confirm('질문을 삭제하시겠습니까?')) {
		let index = $('.div-question-form').length; // 질문 폼의 개수
		
		const MIN_LENGTH = 1; // 질문 폼의 최소 개수(설문지는 최소 1개의 질문을 가져야 함)
		
		if(index == MIN_LENGTH) { // 질문 폼이 1개인데 질문 폼을 삭제하려 한다면 아래 분기 실행
			alert('설문지는 최소 1개의 질문을 가져야합니다.');
			return false;
		} else {
			target.remove(); // 질문 폼이 1개 이상일 경우 해당 폼 삭제
		}
	}
});

function responseFormatDelete(format) {
	const RADIO = 'radio';
	const CHECK = 'check';
	const MIN_LENGTH = 2; // 객관식 질문 형식의 최소 개수(객관식 질문은 최소 2개의 형식을 가져야 함)
	let eachTarget = $(event.target).parent().parent();
	let deleteTarget = $(event.target).parent();
	let questionResponseCnt;
	
	eachTarget.each(function(idx, item) {
		questionResponseCnt = $(item).find('div').length;
	});
	
	if(questionResponseCnt <= MIN_LENGTH) {
		alert('객관식 질문은 최소 2개의 형식을 가져야 합니다.');
		return false;
	} else {
		deleteTarget.remove();
		
		// 추후 수정
		// 질문 옵션 삭제 시 idnex 다시 세팅하는 부분
		eachTarget.each(function(idx, item) {
			questionResponseCnt = $(item).find('.div-response-format-select-wrap').length;
			
			let label = $(item).find('.form-check-label index');
			label.text((questionResponseCnt - 1) + '. ');
		});
	}
};

// >>>>> 라디오 버튼 형식의 질문 옵션 추가
$(document).on('click', '#btn-radio-create', (e) => {
	let target = $(e.target);
	let el = `<div class="div-response-format-radio-wrap">
			  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
			  <img alt="" title="질문 응답 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('radio')">
			  </div>`;
	target.before(el);
});

$(document).on('click', '#btn-check-create', (e) => {
	let target = $(e.target);
	let el = `<div class="div-response-format-check-wrap">
			  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
			  <img alt="" title="질문 응답 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('check')">
			  </div>`;
	target.before(el);
});

$(document).on('click', '#btn-select-create', (e) => {
	let target = $(e.target);
	let parent = target.parent();
	
	let el = `<div class="div-response-format-select-wrap">
			  <label class="form-check-label" ><input class="form-control form-control-sm" type="text"></label>
			  <img alt="" title="질문 삭제 아이콘" id="icon-x" src="${pageContext.request.contextPath }/images/egovframework/icon-delete.png" onclick="responseFormatDelete('select')">
			  </div>`;
			  
	target.before(el);
});

$(document).on('click', '#icon-photo', (e) => {
	let target = $(e.target).parent().children();
	
	// 첨부파일 선택 창 열리게..
	let inputFile = target.closest('input:hidden');
	inputFile.click();
});

	// >>>>> 질문에 등록한 첨부파일 미리보기
// $('#input-file').on('change', (e) => { // <- 이렇게 작성하면 document에 있는 첫번째 #input-file에만 change 이벤트가 적용되어 이후 생성된 #input-file에는 이벤트 적용 x
$(document).on('change', '#input-file', (e) => {
	let target = $(e.target);
	let file = target[0].files[0]; // 등록된 첨부파일
	let reader = new FileReader();
	
	reader.onload = (e) => {
		let imgEl = target.next().next().next();
		imgEl.attr('src', e.target.result);
	}
	
	reader.readAsDataURL(file);
});

//@RequestPart 방식
//formData에 json 형식으로 dto를 넘겨주고 multipartFile[] 형식의 file을 넘겨줌
/*
$('#icon-save').click((e) => {
	let formData = new FormData();
	let adminSurveyQuestionInsDto = []; // 질문 리스트 배열
	let title = $('#title').val();
	let descript = $('#descript').val();
	let startedAt = $('#started-at').val();
	let finishedAt = $('#finished-at').val();
	
	$('.div-survey-form-wrap').find('.div-question-form').each(function(idx, item) {
		let name = $(item).find('#question-name').val();
		let ioption = $(item).closest('.div-survey-form-wrap').children().val();
		let img = $(item).children().first().children()[1].files[0];
		let requiredFl = $(item).find('#requiredFl').is(':checked');
		let adminSurveyResponseInsDto = []; // 질문 리스트 내 질문 옵션 배열
		let imgName = '';
		
		if(img != undefined) {
			imgName = img.name;
			formData.append('files', img)
		}
		
		if(ioption >= 3) {
			$(item).children().next().find('input.form-control.form-control-sm').each(function(idx, item) {
				let optionName = $(item).val();
				let responseList = { optionName : optionName };
				adminSurveyResponseInsDto.push(responseList);
			});
		} else {
			let optionName = '';
			let responseList = { optionName : optionName };
			adminSurveyResponseInsDto.push(responseList);
		}
		
		let questionList = { ioption : ioption, name : name, img : img, imgName : imgName, requiredFl : requiredFl, list : adminSurveyResponseInsDto };
		adminSurveyQuestionInsDto.push(questionList);
	});
	
	dto = { title: title, descript : descript, startedAt : startedAt, finishedAt : finishedAt, list : adminSurveyQuestionInsDto };
	formData.append('dto', new Blob([JSON.stringify(dto)], {type: 'application/json' }));
	
	$.ajax({
    type: 'post',
    url: '/survey/admin/insert-survey.do',
    data : formData,
    contentType: false,
    processData: false,
    success: (data) => {
	     	const SUCCESS = 1;
	     	const FAIL = 0;
	     	const RESULT = data.result;
	     	const MSG = data.msg;
	     	
	     	if(RESULT == SUCCESS) {
	     		alert(MSG + ' 설문조사 관리 페이지로 이동합니다.');
	     		location.href = '/survey/admin/home.do';
	     	}
    }, 
    error: (x) => { console.log(x); }
 })
});
*/
</script>
</html>