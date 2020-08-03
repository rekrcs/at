<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	${article.id}번 글 상세페이지 입니다.
	<div>내용 : ${article.title}</div>
	<div>제목 : ${article.body}</div>
	<div>번호 : ${article.id}</div>
	<div>작성일 : ${article.regDate}</div>
	<div>수정일 : ${article.updateDate}</div>
	
</body>
</html>