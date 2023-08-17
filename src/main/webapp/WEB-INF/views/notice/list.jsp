<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 목록</title>
		<link rel="stylesheet" href="../resources/css/notice/notice.css">
	</head>
	<body>
		<h1>공지사항 목록</h1>
		<table>
			<thead>
				<tr>
					<td>번호</td>
					<td>제목</td>
					<td>작성자</td>
					<td>작성날짜</td>
					<td>첨부파일</td>
					<td>조회수</td>
				</tr>
			</thead>
			<tbody>
			<!-- list 데이터는 items에 넣었고, var에서 설정한 변수로 list 데이터에서 -->
			<!-- 꺼낸 값을 사용하고 i의 값은 varStatus로 사용 -->
				<c:forEach var="notice" items="${ nList }" varStatus="i">
				<tr>
					<td>${ i.count }</td>
					<td>${ notice.noticeSubject }</td>
					<td>
						${ notice.noticeWriter }</td>
						<!-- 연/월/일만 출력하기 위해서 c:fmt 사용 -->
					<td>	
						<fmt:formatDate pattern="YYYY-MM-dd" value="${ notice.nCreateDate }"/>
					</td>
					<%-- <td>${ notice.nCreateDate }</td> --%>
					<td>
						<c:if test="${ !empty notice.noticeFilename }">0</c:if>
						<c:if test="${ empty notice.noticeFilename }">X</c:if>
					</td>
					<td>
						<fmt:formatNumber pattern="##,###,###" value="1230000"></fmt:formatNumber>
					</td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr align="center">
					<td colspan="5">
						1 2 3 4 5
					</td>
				</tr>
				<tr>
					<td colspan="4"><input type="text"></td>
					<td><input type="submit" value="검색"></td>
				</tr>
			</tfoot>
		</table>
	</body>
</html>