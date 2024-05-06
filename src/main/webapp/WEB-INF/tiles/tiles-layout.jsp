<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<body>
	<tiles:insertAttribute name="head"/>
	<tiles:insertAttribute name="nav"/>
	<div id="body"><tiles:insertAttribute name="body"/></div>
	<tiles:insertAttribute name="footer"/>
</body>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/photo-write.js" />
</html>
