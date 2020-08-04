<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 작성" />
<%@ include file="../part/head.jspf"%>

<script>
	var ArticleWriteForm__submitDone = false;

	function ArticleWriteForm__submit(form) {
		if (ArticleWriteForm__submitDone) {
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
		ArticleWriteForm__submitDone = true;
	}
</script>

<style>
a {
	text-decoration: none;
	color: inherit;
}

.option-box {
	display: flex;
	justify-content: flex-start;
}

.option-box div {
	color: black;
	margin-top: 20px;
	font-size: 1.2rem;
	font-weight: bold;
}

.option-box>div>a {
	color: blue;
}

.option-box>div>a:hover {
	color: red;
}
</style>

<form method="POST" class="form1" action="doWrite"
	onsubmit="ArticleWriteForm__submit(this); return false;">
	<input type="hidden" name="redirectUrl" value="/article/detail?id=#id">
	<div class="table-box con">
		<table>
			<tbody>
				<tr>
					<th>제목</th>
					<td>
						<div class="form-control-box">
							<input type="text" placeholder="제목을 입력해주세요." name="title"
								maxlength="100" />
						</div>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<div class="form-control-box">
							<textarea placeholder="내용을 입력해주세요." name="body" maxlength="2000"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<th>작성</th>
					<td>
						<button class="btn btn-primary" type="submit">작성</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<div class="option-box con">
	<div>
		<a href="javascript:history.back();"><i class="fas fa-angle-left"></i><i
			class="fas fa-angle-left"></i>뒤로가기</a>
	</div>
</div>
<%@ include file="../part/foot.jspf"%>