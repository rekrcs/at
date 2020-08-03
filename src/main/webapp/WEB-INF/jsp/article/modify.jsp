<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="doModify" method="POST">
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
				<input name="body" type="text" value="${article.body}" />
			</div>
		</div>

		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="수정" />
			</div>
		</div>
	</form>
</body>
</html>