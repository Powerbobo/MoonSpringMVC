<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시글 목록</title>
		<link rel="stylesheet" href="../resources/css/board/board.css">
	</head>
	<body>
		<h1>게시글 목록</h1>
		<table>
			<!-- 테이블 너비 조정 -->
			<colgroup>
				<col width="10%" align="center"></col>
				<col width="45%"></col>
				<col width="10%"></col>
				<col width="20%"></col>
				<col width="15%"></col>
			</colgroup>
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
				<c:forEach var="board" items="${ bList }" varStatus="i">
				<tr>
					<td>${ i.count }</td>
					
					<%-- <td><a href="/board/detail.kh?boardNo=${ board.boardNo }"> ${ board.boardSubject }</a></td> --%>
					<c:url var="detailUrl" value="/board/detail.kh">
						<c:param name="boardNo" value="${ board.boardNo }"></c:param>
					</c:url>
					<td><a href="${ detailUrl }"> ${ board.boardTitle }</a></td>
					
					<td>
						${ board.boardWriter }</td>
						<!-- 연/월/일만 출력하기 위해서 c:fmt 사용 -->
					<td>	
						<fmt:formatDate pattern="YYYY-MM-dd" value="${ board.bCreateDate }"/>
					</td>
					<%-- <td>${ board.nCreateDate }</td> --%>
					<td>
						<c:if test="${ !empty board.boardFilename }">0</c:if>
						<c:if test="${ empty board.boardFilename }">X</c:if>
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
						<c:if test="${ pInfo.startNavi != 1}">
							<c:url var="prevUrl" value="/board/list.kh">
								<c:param name="page" value="${ pInfo.startNavi - 1 }"></c:param>
							</c:url>
							<a href="${ prevUrl }">[이전]</a>
						</c:if>
						<c:forEach begin="${ pInfo.startNavi }" end="${ pInfo.endNavi }" var = "p">
							<!-- var : 변수명, value : url -->
							<c:url var ="pageUrl" value="/board/list.kh">
							<!-- 쿼리 스트링 -->
								<c:param name="page" value="${ p }"></c:param>
							</c:url>
							<a href="${ pageUrl }">${ p }</a>&nbsp;
						</c:forEach>
						<c:if test="${ pInfo.endNavi != pInfo.naviTotalCount }">
							<c:url var="nextUrl" value="/board/list.kh">
								<c:param name="page" value="${ pInfo.endNavi + 1 }"></c:param>
							</c:url>
							<a href="${ nextUrl }">[다음]</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<form action="/board/search.kh" method="get">
							<select name="searchCondition">
								<option value="all">전체</option>
								<option value="writer">작성자</option>
								<option value="title">제목</option>
								<option value="content">내용</option>
							</select>
							<input type="text" name="searchKeyword" placeholder="검색어를 입력하세요">
							<input type="submit" value="검색">
						</form>
					</td>
					<td>
						<button type="button" onclick="showInsertForm();">글쓰기</button>
					</td>
				</tr>
			</tfoot>
		</table>
		<script>
			function showInsertForm() {
				location.href="/board/write.kh";
			}
		</script>
	</body>
</html>