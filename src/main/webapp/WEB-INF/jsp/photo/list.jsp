<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<section>
	<h1>list.jsp</h1>
	<c:forEach var="l" items="${list }">
		<c:out value="${l.ifile }" />
		<c:out value="${l.iboard }" />
		<c:out value="${l.originalName }${l.ext }" />
		<c:out value="${l.originalName }${l.ext }" />
	</c:forEach>
</section>
</body>
</html>