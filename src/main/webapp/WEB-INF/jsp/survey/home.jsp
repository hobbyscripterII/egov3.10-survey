<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<body>
<h1 class="title">설문조사 목록</h1>
	<table class="table table-hover">
	  <thead>
	    <tr>
		      <th scope="col" class="th-style">순번</th>
		      <th scope="col" class="th-style">제목</th>
		      <th scope="col" class="th-style">개요</th>
		      <th scope="col" class="th-style">설문기간</th>
	    </tr>
	  </thead>
	  <tbody>
    	<c:forEach var="l" items="${LIST }">
		    <tr>
		      <th scope="row" class="td-style"><c:out value="${l.isurvey }" /></th>
		      <td class="td-style"><a href="/survey/survey/form.do?isurvey=${l.isurvey }"><c:out value="${l.surveyTitle }" /></a></td>
		      <td class="td-style"><c:out value="${l.surveyDescript }" /></td>
		      <td class="td-style">
			      <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" var="startedAt" value="${l.startedAt }" />
			      <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" var="finishedAt" value="${l.finishedAt }" />
			      <fmt:formatDate pattern="yyyy.MM.dd" value="${startedAt }" />
			      <c:out value=" ~ " />
			      <fmt:formatDate pattern="yyyy.MM.dd" value="${finishedAt }" />
		      </td>
		    </tr>
    	</c:forEach>
	  </tbody>
	</table>
</body>
</html>