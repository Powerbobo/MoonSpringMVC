<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 등록</title>
		<link rel="stylesheet" href="../resources/css/notice/notice.css">
	</head>
	<body>
		<h1>공지 상세 조회</h1>
		<ul>
			<li>
				<label>제목</label>
				<!-- input 태그를 써서 readonly 설정할수도있지만, 보통 span 태그를 많이 쓴다. -->
				<%-- <input type="text" name="noticeSubject" value="${ notice.noticeSubject }" readonly> --%>
				<span>${ notice.noticeSubject}</span>
			</li>
			<li>
				<label>작성자</label>
				<%-- <input type="text" name="noticeWriter" value="${ notice.noticeWriter }" readonly> --%>
				<span>${ notice.noticeWriter}</span>
			</li>
			<li>
				<label>내용</label>
				<span>${ notice.noticeContent}</span>
				<!-- <p>${ notice.noticeContent }></p> -->
			</li>
			<li>
				<label>첨부파일</label>
				<!-- String으로 받을 수 없고, 변환작업이 필요함 -->
				<img alt="첨부파일" src="../resources/nuploadFiles/${ notice.noticeFilename }">
				<!-- 하이퍼링크로 이미지 다운받게끔 할 수도 있음 -->
				<a href="../resources/nuploadFiles/${ notice.noticeFileRename }" download>${ notice.noticeFilename }</a>
			</li>
		</ul>
		<div>
			<button type="button" onclick="showModifyPage();">수정하기</button>
			<button>삭제하기</button>
			<button type="button" onclick="showListPage();">목록</button>
		</div>
		<script>
			function showModifyPage() {
				const noticeNo = "${ notice.noticeNo }";
				location.href="/notice/modify.kh?noticeNo=" + noticeNo;
			}
			function showListPage() {
				location.href="/notice/list.kh"
			}
		</script>
	</body>
</html>