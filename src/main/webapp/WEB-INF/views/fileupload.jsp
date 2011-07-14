<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<META  http-equiv="Content-Type"  content="text/html;charset=UTF-8">
<title><fmt:message key="welcome.title" />
</title>
<link rel="stylesheet"
	href="<c:url value="/resources/blueprint/screen.css" />"
	type="text/css" media="screen, projection">
<link rel="stylesheet"
	href="<c:url value="/resources/blueprint/print.css" />" type="text/css"
	media="print">
<!--[if lt IE 8]>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
	<![endif]-->
</head>
<body>
	<form id="fileuploadForm" action="fileupload" method="POST"
		enctype="multipart/form-data" class="cleanform">
		<div class="header">
			<h2>Form</h2>
			<c:if test="${not empty message}">
				<div id="message" class="${message}">${message}</div>
			</c:if>
		</div>
		<label for="file">File</label> <input id="file" type="file"
			name="file" />
		<p>
			<button type="submit">Upload</button>
		</p>
	</form>
</body>
</html>