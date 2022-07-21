<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title> 자유 게시판 목록 </title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	</head>
	<body>
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
		<hr>
		<h3> 자유 게시판 </h3>
		<hr>
		<a href="${ pageContext.request.contextPath }/board/free/write_form" class="clearfix">
			<button type="button" class="btn btn-primary float-right"> 글 쓰러 가기 </button>
		</a>
		<hr>
		<table class="table table-hover">
		<thead>
			<tr>
				<th>글번호</th>	<th>제목</th>	<th>작성자</th>	<th>작성일</th>
			</tr>	
		</thead>
			<tbody>
				<c:forEach var="dto" items="${ list }">
					<tr>
						<td><b>${dto.board_no}</b></td>
						<td>
							<a id ="dlink" href="${pageContext.request.contextPath}/board/free/detail?board_no=${dto.board_no}">
								${dto.title}
							</a>
						</td>
						
						<td>${ dto.writer }</td>
						<td>${ dto.write_date }</td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</body>
</html>