<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시글 조회</title>
		<link rel="stylesheet" href="../resources/css/board/board.css">
	</head>
	<body>
		<h1>게시글 상세 조회</h1>
		<ul>
			<li>
				<label>제목</label>
				<!-- input 태그를 써서 readonly 설정할수도있지만, 보통 span 태그를 많이 쓴다. -->
				<%-- <input type="text" name="boardSubject" value="${ board.boardSubject }" readonly> --%>
				<span>${ board.boardTitle}</span>
			</li>
			<li>
				<label>작성자</label>
				<%-- <input type="text" name="boardWriter" value="${ board.boardWriter }" readonly> --%>
				<span>${ board.boardWriter}</span>
			</li>
			<li>
				<label>내용</label>
				<span>${ board.boardContent}</span>
				<!-- <p>${ board.boardContent }></p> -->
			</li>
			<li>
				<label>첨부파일</label>
				<!-- String으로 받을 수 없고, 변환작업이 필요함 -->
				<img alt="첨부파일" src="../resources/nuploadFiles/${ board.boardFilename }">
				<!-- 하이퍼링크로 이미지 다운받게끔 할 수도 있음 -->
				<a href="${ board.boardFilepath }" download>${ board.boardFilename }</a>
			</li>
		</ul>
		<div>
			<button type="button" onclick="showModifyPage();">수정하기</button>
			<button>삭제하기</button>
			<button type="button" onclick="showListPage();">목록</button>
		</div>
		<!-- 댓글 등록 -->
		<br>
		<form action="/board/addReply.kh" method="post">
			<table>
				<tr>
					<td>
						<textarea rows="3" cols="55"></textarea>
					</td>
					<td>
						<input type="submit" value="완료">
					</td>
				</tr>
			</table>
		</form>
		<!-- 댓글 목록 -->
		<br>
		<table width="500" border="1">
			<tr>
				<td>일용자</td>
				<td>아 처음이시군요 환영합니다.</td>
				<td>2023-08-24</td>
				<td>
					<a href="#">수정하기</a>
					<a href="#">삭제하기</a>
				</td>
			</tr>
			<tr>
				<td>이용자</td>
				<td>아 처음이시군요 환영합니다.</td>
				<td>2023-08-24</td>
				<td>
					<a href="#">수정하기</a>
					<a href="#">삭제하기</a>
				</td>
			</tr>
			<tr>
				<td>삼용자</td>
				<td>아 처음이시군요 환영합니다.</td>
				<td>2023-08-24</td>
				<td>
					<a href="#">수정하기</a>
					<a href="#">삭제하기</a>
				</td>
			</tr>
		</table>
		<script>
			function showModifyPage() {
				const boardNo = "${ board.boardNo }";
				location.href="/board/modify.kh?boardNo=" + boardNo;
			}
			function showListPage() {
				location.href="/board/list.kh"
			}
		</script>
	</body>
</html>