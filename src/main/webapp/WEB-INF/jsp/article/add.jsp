<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="doAdd" method="POST">
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input name="title" type="text" placeholder="제목을 입력해 주세요" />
			</div>
		</div>

		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<input name="body" type="text" placeholder="내용을 입력해 주세요" />
			</div>
		</div>

		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="글쓰기" />
			</div>
		</div>
	</form>
</body>
</html>