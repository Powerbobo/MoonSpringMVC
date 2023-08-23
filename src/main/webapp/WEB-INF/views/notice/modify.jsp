<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 수정</title>
	</head>
	<body>
		<h1>공지 수정</h1>
		<form action="/notice/modify.kh" method="post" enctype="multipart/form-data">
			<!-- 수정할때, 리다이렉트 될 때 사용 -->
			<input type="hidden" name="noticeNo" value="${ notice.noticeNo }">
			<!-- 기존 업로드 파일 체크할 때 사용 -->
			<!-- 해당 코드가 없으면 파일 수정할 때 파일 이름, 경로, 크기를 알 수 없기 때문에 -->
			<!-- 아래 코드를 꼭 써주거나, mybatis-config.xml 에  -->
			<!-- setting (<setting name="jdbcTypeForNull" value="NULL" />)를 추가해줘야한다. -->
			<input type="hidden" name="noticeFilename" value="${ notice.noticeFilename }">
			<input type="hidden" name="noticeFileRename" value="${ notice.noticeFileRename }">
			<input type="hidden" name="noticeFilepath" value="${ notice.noticeFilepath }">
			<input type="hidden" name="noticeFilelength" value="${ notice.noticeFilelength }">
			<ul>
				<li>
					<label>제목</label>
					<input type="text" name="noticeSubject" value="${ notice.noticeSubject }">
				</li>
				<li>
					<label>작성자</label>
					<input type="text" name="noticeWriter" value="${ notice.noticeWriter }" readonly>
				</li>
				<li>
					<label>내용</label>
					<textarea rows="4" cols="50" name="noticeContent">${ notice.noticeContent }</textarea>
				</li>
				<li>
					<label>첨부파일</label>
					<!-- String으로 받을 수 없고, 변환작업이 필요함 -->
<%-- 					<a href="../resources/nuploadFiles/${ notice.noticeFilename }" download>${ notice.noticeFilename }</a> --%>
					<!-- uploadFile 은 파일이름, 경로, 크기를 담고있는 객체이기 때문에 -->
					<!-- @ModelAttribute 를 사용하기 위해서 NoticeFilename 으로 필드값과 맞춰도
					사용할 수 없고, @RequestParam 으로 uploadFile 처리를 해줘야 한다. -->
					<input type="file" name="uploadFile">
				</li>
			</ul>
			<div>
				<input type="submit" value="수정">
			</div>
		</form>
	</body>
</html>