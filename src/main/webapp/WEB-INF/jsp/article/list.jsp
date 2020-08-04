<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../part/head.jspf"%>

<style>
a {
	text-decoration: none;
	color: inherit;
}
/* 수정 삭제 버튼 시작 */
.btns {
	display: flex;
	justify-content: flex-end;
	color: black;
	margin-top: 20px;
	font-size: 1.2rem;
	font-weight: bold;
}

.btns>a {
	color: blue;
}

.btns>a:hover {
	color: red;
}
</style>


<div class="table-box con">
	<table>
		<colgroup>
			<col width="100" />
			<col width="200" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>제목</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articles}" var="article">
				<tr>
					<td>${article.id}</td>
					<td>${article.regDate}</td>
					<td><a href="detail?id=${article.id}">${article.title}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="btns con">
	<a href="./add">게시물 추가</a>
</div>

<%@ include file="../part/foot.jspf"%>