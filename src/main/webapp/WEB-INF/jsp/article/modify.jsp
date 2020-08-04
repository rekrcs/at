<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 수정" />
<%@ include file="../part/head.jspf"%>

<script>
	var submitModifyFormDone = false;

	function submitModifyForm(form) {
		if (submitModifyFormDone) {
			alert('처리중입니다.');
			return;
		}

		form.title.value = form.title.value.trim();

		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요.');
			form.title.focus();

			return false;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('내용을 입력해주세요.');
			form.body.focus();

			return false;
		}

		form.submit();
		submitModifyFormDone = true;
	}
</script>

<div class="modify-form-box con">
	<form action="doModify" method="POST" class="modify-form form1"
		onsubmit="submitModifyForm(this); return false;">
		<input type="hidden" name="id" value="${article.id}" />
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input name="title" type="text" value="${article.title}" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<textarea name="body">${article.body}</textarea>
			</div>
		</div>
		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="전송" /> <a
					href="detail?id=${article.id}">취소</a>
			</div>
		</div>
	</form>
</div>
<%@ include file="../part/foot.jspf"%>