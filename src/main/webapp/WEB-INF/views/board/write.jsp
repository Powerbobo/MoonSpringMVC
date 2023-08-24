<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
		<title>게시글 등록</title>
		<link rel="stylesheet" href="../resources/css/board/board.css">
	</head>
	<body>
		<h1>게시글 등록</h1>
		<fieldset>
			<form action="/board/write.kh" method="post" enctype="multipart/form-data">
				<ul>
					<li>
						<label>제목</label>
						<input type="text" name="boardTitle">
					</li>
					<li>
						<label>작성자</label>
						<input type="text" name="boardWriter">
					</li>
					<li>
						<label>내용</label>
						<textarea rows="4" cols="50" name="boardContent"></textarea>
					</li>
					<li>
						<label>첨부파일</label>
						<!-- String으로 받을 수 없고, 변환작업이 필요함 -->
						<input type="file" name="uploadFile">
					</li>
				</ul>
				<div>
					<button type="button" class="btn btn-primary">Primary</button>
					<input type="submit" value="등록">
				</div>
			</form>
		</fieldset>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>