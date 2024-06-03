<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<style>
body { background-color: #A3CCA2; }
#body { height: auto; }
footer { position: relative; bottom: 0px; }
.div-survey-form-wrap, .div-survey-submit-form-wrap { width: 100%; display: flex; justify-content: center; }
.div-survey-form { width: 900px; height: auto; background-color: white; border-radius: 5px; margin-bottom: 15px; border: 1px solid lightgray; }
.div-question-form { padding: 20px 25px 20px 25px; }
.div-survey-submit-form { width: 900px; display: flex; justify-content: space-between; }
.div-question-title { color: #000; }
.div-response-option-form { margin: 10px 0 10px 0; }
.text-response-style { width: 100%; }
.form-control { border: none; border-radius: 0px; border-bottom: 1px solid #dee2e6; }
</style>
<body>
<h1 class="title">설문조사 참여</h1>
<div class="div-survey-form-wrap">
	<div class="div-survey-form">
		<div class="div-question-form">
			<p class="p-survey-text"><c:out value="제목: ${SURVEY.surveyTitle }" /></p>
			<p class="p-survey-text"><c:out value="개요: ${SURVEY.surveyDescript }" /></p>
			<p class="p-survey-text"><c:out value="응답자: ${SURVEY.name }" /></p>
		</div>
	</div>
</div>

<form id="f">
<c:forEach var="s" varStatus="status" items="${SURVEY.list }"> <!-- 질문 리스트 출력 -->
<div class="div-survey-form-wrap">
	<div class="div-survey-form">
		<div class="div-question-form">
			<div class="div-question-title">
				<input type="hidden" value="${s.requiredFl }" name="list[${status.index }].requiredFl" />
				<input type="hidden" value="${s.iquestion }" name="list[${status.index }].iquestion" />
				<input type="hidden" value="${s.list[0].iresponseformat }" name="list[${status.index }].iresponseformat" />
				<c:out value="${s.questionName }" />
				<span style="color: red; font-weight: bold"><c:if test="${s.requiredFl == 'Y' }">* 필수 문항입니다.</c:if></span>
			</div>
			
			<!-- 이미지 출력 -->
			<c:if test="${s.questionImg != null}">
				<img src="/survey/img/${s.questionImg }" alt="질문 이미지" style="width: 50%" />
			</c:if>
			
			<div class="div-response-option-form">
			<c:choose>
				<c:when test="${s.list[0].iresponseformat == 4 }">
				<select class="form-select" name="list[${status.index }].ioption">
					<option value="0"><c:out value="선택" /></option>
					<c:forEach var="r" items="${s.list }">
						<option value="${r.ioption }"><c:out value="${r.responseOptionName }" /></option>
					</c:forEach>
				</select>
				</c:when>
				<c:otherwise>
				<c:forEach var="r" items="${s.list }">
					<c:if test="${r.iresponseformat == 1 }">
						<input type="hidden" value="${r.ioption }" name="list[${status.index }].ioption" />
						<input type="text" class="form-control" name="list[${status.index }].responseContents" />
					</c:if>
					<c:if test="${r.iresponseformat == 2 }">
						<input type="hidden" value="${r.ioption }" name="list[${status.index }].ioption" />
						<textarea class="form-control" name="list[${status.index }].responseContents" rows="5" style="resize: none"></textarea>
					</c:if>
					<c:if test="${r.iresponseformat == 3 }">
						<div class="form-check">
							<!-- name 속성은 버튼 체크 중복 방지를 위해 선언 실제로 사용 x -->
							<input type="radio" value="${r.ioption }" name="list[${status.index }].ioption" class="form-check-input"></input>
							<label class="form-check-label" ><c:out value="${r.responseOptionName }" /></label>
						</div>
					</c:if>
					<c:if test="${r.iresponseformat == 5 }">
						<input type="checkbox" value="${r.ioption }" name="list[${status.index }].ioption" class="form-check-input"></input>
						<label class="form-check-label" ><c:out value="${r.responseOptionName }" /></label>
					</c:if>
				</c:forEach>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
</div>
</c:forEach>
</form>
<div class="div-survey-submit-form-wrap">
	<div class="div-survey-submit-form">
		<button type="button" class="btn btn-primary" id="btn-participate">제출</button>
		<button type="button" class="btn btn-link" id="btn-form-del">양식 지우기</button>
	</div>
</div>
</body>
<script type="text/javascript">
$('#btn-participate').click(() => {
	let form = $('#f').serialize();
	
	$.ajax({
	       type: 'post',
	       url: '/survey/survey/participate.do',
	       data : form,
	       success: (data) => {
	     	const SUCCESS = 1;
	     	const FAIL = 0;
	     	const RESULT = data.result;
	     	const MSG = data.msg;
	     	
	     	if(RESULT == SUCCESS) {
	     		alert(MSG + ' 설문조사 메인 페이지로 이동합니다.');
	     		location.href = '/survey/survey/home.do';
	     	} else {
	     		alert(MSG);
	     		return false;
	     	}
	    },
	       error: (x) => { console.log(x); }
	    })
});

$('#btn-form-del').click((e) => {
	if(confirm('양식을 지우면 모든 질문에서 대답이 삭제되며 되돌릴 수 없습니다.')) {
		window.location.reload();
	}
});
</script>
</html>