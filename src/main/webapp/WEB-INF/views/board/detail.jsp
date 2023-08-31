<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<img alt="첨부파일" src="../resources/buploadFiles/${ board.boardFilename }">
				<!-- 하이퍼링크로 이미지 다운받게끔 할 수도 있음 -->
				<a href="${ board.boardFilepath }" download>${ board.boardFilename }</a>
			</li>
		</ul>
		<div>
			<button type="button" onclick="showModifyPage();">수정하기</button>
			<button>삭제하기</button>
			<button type="button" onclick="showListPage();">목록</button>
			<!-- <a href="/member/login.kh">로그인</a>  -->
		</div>
		<!-- 댓글 등록 -->
		<br>
		<form action="/reply/add.kh" method="post">
			<!-- 사용자가 알 필욘 없지만, 게시글 정보를 불러와야하기 때문에 작성 -->
			<input type="hidden" name="refBoardNo" value="${ board.boardNo }">
			<table>
				<tr>
					<td>
						<textarea rows="3" cols="55" name="replyContent"></textarea>
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
			<colgroup>
				<col width="15%">
				<col width="40%">
				<col width="20%">
				<col width="25%">
			</colgroup>
			<!-- var : 변수 / item : ?-->
			<c:forEach var="reply" items="${ rList }">
				<tr>
					<td>${ reply.replyWriter }</td>
					<td>${ reply.replyContent }</td>
					<td>${ reply.rCreateDate }</td>
					<td>
						<a href="javascript:void(0);" onclick="showModifyForm(this, '${reply.replyContent }');">수정하기</a>
						<c:url var="delUrl" value="/reply/delete.kh">
							<c:param name="replyNo" value="${ reply.replyNo }"></c:param>
							<!-- 본인이 작성한 댓글만 삭제하기 위해서 추가함 -->
							<c:param name="replyWriter" value="${ reply.replyWriter }"></c:param>
							<!-- 성공하면 detail로 가기 위해서 필요한 boardNo 셋팅 -->
							<c:param name="refBoardNo" value="${ reply.refBoardNo }"></c:param>
						</c:url>
						<a href="javascript:void(0);" onclick="deleteReply('${ delUrl }');">삭제하기</a>
					</td>
				</tr>
			<tr id="replyModifyForm" style="display:none;">
				<!-- ************* HTML 방식으로 수정하기 ************* -->
				<!-- <form action="/reply/update.kh" method="post"> -->
					<%-- <input type="hidden" name="replyNo" value="${ reply.replyNo }"> --%>
					<%-- <input type="hidden" name="refBoardNo" value="${ reply.refBoardNo }"> --%>
					<%-- <td colSpan="3"><input type="text" size="50" name="replyContent" value="${ reply.replyContent }"></td> --%>
					<!-- <td><input type="submit" value="완료"></td> -->
				<!-- </form> -->
				<!-- ************* DOM 방식으로 수정하기 ************* -->
					<!-- 입력한 값 가져오기 위해서 id 추가 -->
					<td colSpan="3"><input id="replyContent" type="text" size="50" name="replyContent" value="${ reply.replyContent }"></td>
					<td><input type="button" onclick="replyModify(this, '${ reply.replyNo }', '${ reply.refBoardNo }');" value="완료"></td>
			</tr>
			</c:forEach>
		</table>
		<script>
			// ############################## 게시글 ##############################
			// 게시글 삭제
			const deleteBoard = (boardUrl) => {
				location.href = boardUrl;
			}
			
			// 게시글 수정페이지 이동
			function showModifyPage(modifyUrl) {
				location.href = modifyUrl;
			}
			
			// 게시글 목록으로 이동
			function showListPage() {
				location.href="/board/list.kh"
			}
			// ############################## 댓글 ##############################
			// 게시글 댓글 삭제
			function deleteReply(url) {
				// DELETE FROM REPLY_TBL WHERE REPLY_NO = 샵{ replyNo } AND R_STATUS = 'Y'
				// UPDATE REPLY_TBL SET R_STATUS = 'N' WHERE REPLY_NO = 샵{ replyNo }
				location.href = url;
			}
			
			// 게시글 댓글 수정하기
			function replyModify(obj, replyNo, refBoardNo) {
				// DOM 프로그래밍을 이용하는 방법
				const form = document.createElement("form");
				form.action = "/reply/update.kh";
				form.method = "post";
				
				const input1 = document.createElement("input");
				input1.type = "hidden";
				input1.value = replyNo;
				input1.name = "replyNo";
				
				const input2 = document.createElement("input");
				input2.type = "hidden";
				input2.value = refBoardNo;
				input2.name = "refBoardNo";
				
				const input3 = document.createElement("input");
				input3.type = "text";
				// 여기를 this를 이용하여 수정해주세요!
				// input3.value = document.querySelector("#replyContent").value;
				// 아래 방식으로 입력하면 첫 댓글만 수정되고, 나머진 수정하면 첫 댓글로 수정된다.
				// this 를 이용해 매개변수를 만들어 수정해야 함!
				// 구조를 보면 부모의 부몬의 자식을 가져와야 함.
				// this를 이용해서 내가 원하는 노드 찾기
				input3.value = obj.parentElement.previousElementSibling.childNodes[0].value;
				// obj.parentElement.previousElementSibling.children[0].value; -> 이 방식도 가능
				input3.name = "replyContent";
				
				form.appendChild(input1);
				form.appendChild(input2);
				form.appendChild(input3);
				
				document.body.appendChild(form);
				form.submit();
			}
			// 게시글 댓글 수정하기 이벤트 연결
			function showReplyModifyForm(obj, replyContent) {
				// #1. HTML태그, display:none 사용하는 방법
	// 			document.querySelector("#replyModifyForm").style.display = "";
				obj.parentElement.parentElement.nextElementSibling.style.display = "";
				
				// #2. DOM프로그래밍 이용하는 방법
	// 			<tr id="replyModifyForm" style="display:none;">
	// 				<td colspan="3"><input type="text" size="50" value="${ reply.replyContent }"></td>
	// 				<td><input type="button" value="완료"></td>
	// 			</tr>
	// 			const trTag = document.createElement("tr");
	// 			const tdTag1 = document.createElement("td");
	// 			tdTag1.colSpan = 3;
	// 			const inputTag1 = document.createElement("input");
	// 			inputTag1.type = "text";
	// 			inputTag1.size = 50;
	// 			inputTag1.value = replyContent;
	// 			tdTag1.appendChild(inputTag1);
	// 			const tdTag2 = document.createElement("td");
	// 			const inputTag2 = document.createElement("input");
	// 			inputTag2.type="button";
	// 			inputTag2.value="완료";
	// 			tdTag2.appendChild(inputTag2);
	// 			trTag.appendChild(tdTag1);
	// 			trTag.appendChild(tdTag2);				
	// 			console.log(trTag);
				// 클릭한 a를 포함하고 있는 tr 다음에 수정 Form이 있는 tr 추가하기
	// 			const prevTrTag = obj.parentElement.parentElement;
				// 수정하기 1개만 출력하기
				// if(prevTrTag.nextSibling == null) -> 이렇게만 쓰면 수정하는게 계속 추가 됨!
	// 			if(prevTrTag.nextElementSibling || !prevTrTag.nextElementSibling.querySelector("input"))
	// 				prevTrTag.parentNode.insertBefore(trTag, prevTrTag.nextSibling);
							
			}
		</script>
	</body>
</html>