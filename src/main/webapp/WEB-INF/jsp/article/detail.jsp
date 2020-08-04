<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 상세내용" />
<%@ include file="../part/head.jspf"%>

<style>
a {
	text-decoration: none;
	color: inherit;
}
/* 수정 삭제 버튼 시작 */
.option-box {
	display: flex;
	justify-content: space-between;
}

.option-box div {
	color: black;
	margin-top: 20px;
	font-size: 1.2rem;
	font-weight: bold;
}

.option-box>span:nth-child(2) {
	margin: 0 3px;
}

.option-box>div>span>a {
	color: blue;
}

.option-box>div>span>a:hover {
	color: red;
}

.option-box>div>a {
	color: blue;
}

.option-box>div>a:hover {
	color: red;
}

.previous-next-box {
	display: flex;
	justify-content: space-between;
	margin-top: 50px;
	font-size: 1.2rem;
	font-weight: bold;
}
</style>

<div class="table-box con">
	<table>
		<tbody>
			<tr>
				<th>번호</th>
				<td>${article.id}</td>
			</tr>
			<tr>
				<th>날짜</th>
				<td>${article.regDate}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${article.title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${article.body}</td>
			</tr>
		</tbody>
	</table>

</div>
<div class="option-box con">
	<div>
		<a href="javascript:history.back();"><i class="fas fa-angle-left"></i><i
			class="fas fa-angle-left"></i>뒤로가기</a>
	</div>
	<div>
		<span class="option-modify"><a href="modify?id=${article.id}">수정</a></span>
		<span></span> <span class="option-delete"><a
			onclick="if ( confirm('게시물을 삭제하시겠습니까?') == false ) return false;"
			href="doDelete?id=${article.id}">삭제</a></span>
	</div>
</div>


<div class="previous-next-box con">
	<c:if test="${article.id == firstId}">
		<span class="previous-btn"></span>
		<span class="next-btn"><a href="detail?id=${articleNext.id}">다음글
				(${articleNext.title}) <i class="fas fa-angle-right"></i>
		</a></span>
	</c:if>
	<c:if test="${article.id == lastId}">
		<span class="previous-btn"><a
			href="detail?id=${articlePrevious.id}"><i
				class="fas fa-angle-left"></i> (${articlePrevious.title}) 이전글</a></span>
		<span class="next-btn"></span>
	</c:if>
	<c:if test="${article.id != lastId && article.id != firstId}">
		<span class="previous-btn"><a
			href="detail?id=${articlePrevious.id}"><i
				class="fas fa-angle-left"></i> (${articlePrevious.title}) 이전글</a></span>
		<span class="next-btn"><a href="detail?id=${articleNext.id}">다음글
				(${articleNext.title}) <i class="fas fa-angle-right"></i>
		</a> </span>
	</c:if>
</div>
<%@ include file="../part/foot.jspf"%>