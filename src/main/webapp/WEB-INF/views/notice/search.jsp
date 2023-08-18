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
<!-- 					<td>조회수</td> -->
				</tr>
			</thead>
			<tbody>
			<!-- list 데이터는 items에 넣었고, var에서 설정한 변수로 list 데이터에서 -->
			<!-- 꺼낸 값을 사용하고 i의 값은 varStatus로 사용 -->
				<c:forEach var="notice" items="${ sList }" varStatus="i">
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
<!-- 					<td> -->
<%-- 						<fmt:formatNumber pattern="##,###,###" value="1230000"></fmt:formatNumber> --%>
<!-- 					</td> -->
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr align="center">
					<td colspan="5">
						<c:forEach begin="${ pInfo.startNavi }" end="${ pInfo.endNavi }" var = "p">
							<!-- var : 변수명, value : url -->
							<!-- 페이징 처리 후 검색 시 search 페이지로 동작하기 위해서 -->
							<!-- value 주소값 변경 -->
							<c:url var ="pageUrl" value="/notice/search.kh">
							<!-- 쿼리 스트링 -->
								<c:param name="page" value="${ p }"></c:param>
								<!-- 페이징 처리 후 검색 시 search 페이지로 동작하기 위해서 -->
								<!-- searchCondition, searchKeyword 추가 -->
								<c:param name="searchCondition" value="${ searchCondition }"></c:param>
								<c:param name="searchKeyword" value="${ searchKeyword }"></c:param>
							</c:url>
							<a href="${ pageUrl }">${ p }</a>&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<form action="/notice/search.kh" method="get">
							<select name="searchCondition">
								<option value="all" <c:if test="${ searchCondition == 'all' }">selected</c:if>>전체</option>
								<option value="writer" <c:if test="${ searchCondition == 'writer' }">selected</c:if>>작성자</option>
								<option value="title" <c:if test="${ searchCondition == 'title' }">selected</c:if>>제목</option>
								<option value="content" <c:if test="${ searchCondition == 'content' }">selected</c:if>>내용</option>
							</select>
							<input type="text" name="searchKeyword" placeholder="검색어를 입력하세요" value="${ searchKeyword }">
							<input type="submit" value="검색">
						</form>
					</td>
					<td>
						<button>글쓰기</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</body>
</html>