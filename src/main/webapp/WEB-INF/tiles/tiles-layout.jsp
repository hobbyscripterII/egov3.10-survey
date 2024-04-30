<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"  prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
    <tiles:insertAttribute name="loadfile"/>
</head>
<body>
	<div id="header"><tiles:insertAttribute name="header"/></div>
	<div id="nav"><tiles:insertAttribute name="nav"/></div>
	<div id="body"><tiles:insertAttribute name="body"/></div>
	<div id="footer"><tiles:insertAttribute name="footer"/></div>
</body>
</html>
