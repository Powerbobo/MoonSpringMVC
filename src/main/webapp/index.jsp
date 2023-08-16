<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>Welcome to Spring Web</title>
	</head>
	<body>
		<!-- 해당 페이지를 잠깐 보여주고 바로 페이지를 이동시킬 수 있음! -->
		<!-- web.xml 파일에서 *.kh 로 변경했기 때문에 .jsp 가 아닌 .kh 로 작성함 -->
		<jsp:forward page="/home.kh"></jsp:forward>
	</body>
</html>