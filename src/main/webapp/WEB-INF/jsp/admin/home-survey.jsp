<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<body>
<h1 class="title">설문 관리</h1>
	<table class="table table-hover">
	  <thead>
	    <tr>
		      <th scope="col" class="th-style">순번</th>
		      <th scope="col" class="th-style">제목</th>
		      <th scope="col" class="th-style">사용여부</th>
		      <th scope="col" class="th-style">응답가능여부</th>
		      <th scope="col" class="th-style">작성자명</th>
		      <th scope="col" class="th-style">수정</th>
		      <th scope="col" class="th-style">삭제</th>
		      <th scope="col" class="th-style">통계조회</th>
	    </tr>
	  </thead>
	  <tbody>
    	<c:forEach var="l" items="${LIST }">
	    	<tr>
		      <th scope="row" class="td-style"><c:out value="${l.isurvey }" /></th>
		      <td class="td-style"><c:out value="${l.surveyTitle }" /></td>
		      <td class="td-style"><c:out value="${l.surveyUseFl }" /></td>
		      <td class="td-style"><c:out value="${l.surveyResponseUseFl }" /></td>
		      <td class="td-style"><c:out value="${l.name }" /></td>
		      <td class="td-style"><button type="button" class="btn btn-primary btn-sm" onclick="location.href='/survey/admin/update-survey.do?isurvey=${l.isurvey }'">수정</button></td>
		      <td class="td-style"><button type="button" class="btn btn-danger btn-sm" id="btn-delete-survey" data-isurvey="${l.isurvey }">삭제</button></td>
		      <td class="td-style"><button type="button" class="btn btn-primary btn-sm" onclick="location.href='/survey/admin/stats-survey.do?isurvey=${l.isurvey }'">통계조회</button></td>
	    	</tr>
    	</c:forEach>
	  </tbody>
	</table>
	
	<div class="div-list-bottom-wrap" style="text-align: right">
		<button type="button" class="btn btn-primary" onclick="location.href='/survey/admin/create-survey.do'">설문지 작성</button>
	</div>
</body>
<script>
$(document).on('click', '#btn-delete-survey', (e) => {
	let isurvey = $(e.target).data('isurvey');
	console.log('isurvey = ', isurvey);
	
	if(confirm('설문조사를 삭제하시겠습니까?')) {
		$.ajax({
		       type: 'post',
		       url: '/survey/admin/delete-survey.do',
		       data : { isurvey : isurvey },
		       success: (data) => {
		     	const SUCCESS = 1;
		     	const FAIL = 0;
		     	const RESULT = data.result;
		     	const MSG = data.msg;
		     	
		     	if(RESULT == SUCCESS) {
		     		alert(MSG);
		     		location.href = '/survey/admin/home.do';
		     	} else {
		     		alert(MSG);
		     		return false;
		     	}
		    },
		       error: (x) => { console.log(x); }
	    })
	}
});
</script>
</html>