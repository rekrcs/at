<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 상세내용" />
<%@ include file="../part/head.jspf"%>

<script>
	var articleId = parseInt('${article.id}');
</script>

<script>
	var ArticleReply__loadListDelay = 1000;

	// 임시
	ArticleReply__loadListDelay = 5000;
</script>
<style>
a {
	text-decoration: none;
	color: inherit;
}

/* 댓글 ajax화 */
.article-reply-list-box tr .loading-delete-inline {
	display: none;
	font-weight: bold;
	color: red;
}

.article-reply-list-box tr[data-loading="Y"] .loading-none {
	display: none;
}

.article-reply-list-box tr[data-loading="Y"][data-loading-delete="Y"] .loading-delete-inline
	{
	display: inline;
}

.article-reply-list-box tr[data-modify-mode="Y"] .modify-mode-none {
	display: none;
}

.article-reply-list-box tr .modify-mode-inline {
	display: none;
}

.article-reply-list-box tr .modify-mode-block {
	display: none;
}

.article-reply-list-box tr[data-modify-mode="Y"] .modify-mode-block {
	display: block;
}

.article-reply-list-box tr[data-modify-mode="Y"] .modify-mode-inline {
	display: inline;
}

/* 게시물 수정 삭제 버튼 시작 */
.option-box {
	display: flex;
	justify-content: space-between;
}

.option-box div {
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

/* 댓글 수정 삭제 버튼 시작 */
.table-box>table>tbody>tr>td>a {
	color: blue;
}

.table-box>table>tbody>tr>td>a:hover {
	color: red;
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
<h2 class="con">댓글 작성</h2>

<script>
	function ArticleReply__submitWriteForm(form) {
		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('댓글을 입력해주세요.');
			form.body.focus();

			return;
		}

		$.post('./doWriteReplyAjax', {
			articleId : articleId,
			body : form.body.value
		}, function(data) {

		}, 'json');

		form.body.value = '';
	}
</script>

<form action="" class="form1"
	onsubmit="ArticleReply__submitWriteForm(this); return false;">
	<div class="table-box con">
		<table>
			<tbody>
				<tr>
					<th>내용</th>
					<td>
						<div class="form-control-box ">
							<textarea class="min-height-100px" placeholder="내용을 입력해주세요."
								name="body" maxlength="300"></textarea>
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

<h2 class="con">댓글 리스트</h2>

<script>
	var ArticleReply__lastLoadedArticleReplyId = 0;
	function ArticleReply__loadList() {
		$.get('./getForPrintArticleRepliesRs', {
			articleId : articleId,
			from : ArticleReply__lastLoadedArticleReplyId + 1
		}, function(data) {
			data.articleReplies = data.articleReplies.reverse();

			for (var i = 0; i < data.articleReplies.length; i++) {
				var articleReply = data.articleReplies[i];
				ArticleReply__drawReply(articleReply);

				ArticleReply__lastLoadedArticleReplyId = articleReply.id;
			}

			setTimeout(ArticleReply__loadList, ArticleReply__loadListDelay);
		}, 'json');
	}

	var ArticleReply__$listTbody;

	function ArticleReply__drawReply(articleReply) {
		var html = $('.template-box-1 tbody').html();

		html = replaceAll(html, "{$번호}", articleReply.id);
		html = replaceAll(html, "{$날짜}", articleReply.regDate);
		html = replaceAll(html, "{$내용}", articleReply.body);

		/*
		var html = '';

		html = '<tr data-article-reply-id="' + articleReply.id + '">';
		html += '<td>' + articleReply.id + '</td>';
		html += '<td>' + articleReply.regDate + '</td>';
		html += '<td>' + articleReply.body + '</td>';
		html += '<td>';
		html += '<a href="#">삭제</a>';
		html += '<a href="#">수정</a>';
		html += '</td>';
		html += '</tr>';
		 */
		ArticleReply__$listTbody.prepend(html);
	}

	$(function() {
		ArticleReply__$listTbody = $('.article-reply-list-box > table tbody');

		ArticleReply__loadList();
	});

	function ArticleReply__enableModifyMode(obj) {
		var $clickedBtn = $(obj);
		var $tr = $clickedBtn.closest('tr');

		var $replyBodyText = $tr.find('.reply-body-text');
		var $textarea = $tr.find('form textarea');

		$textarea.val($replyBodyText.text().trim());

		$tr.attr('data-modify-mode', 'Y');
	}

	function ArticleReply__disableModifyMode(obj) {
		var $clickedBtn = $(obj);
		var $tr = $clickedBtn.closest('tr');

		$tr.attr('data-modify-mode', 'N');
	}

	function ArticleReply__submitModifyReplyForm(form) {
		var $tr = $(form).closest('tr');
		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('댓글내용을 입력 해주세요.');
			form.body.focus();

			return false;
		}

		var replyId = parseInt($tr.attr('data-article-reply-id'));
		var body = form.body.value;

		$tr.attr('data-loading', 'Y');
		$tr.attr('data-loading-modify', 'Y');

		$.post('./doModifyReplyAjax', {
			id : replyId,
			body : body
		}, function(data) {
			$tr.attr('data-loading', 'N');
			$tr.attr('data-loading-modify', 'N');

			ArticleReply__disableModifyMode(form);

			if (data.resultCode.substr(0, 2) == 'S-') {
				var $replyBodyText = $tr.find('.reply-body-text');
				var $textarea = $tr.find('form textarea');

				$replyBodyText.text($textarea.val());
			} else {
				if (data.msg) {
					alert(data.msg)
				}
			}
		});
	}

	function ArticleReply__delete(obj) {
		var $clickedBtn = $(obj);
		var $tr = $clickedBtn.closest('tr');

		var replyId = parseInt($tr.attr('data-article-reply-id'));

		$tr.attr('data-loading', 'Y');
		$tr.attr('data-loading-delete', 'Y');

		$.post('./doDeleteReplyAjax', {
			id : replyId
		}, function(data) {
			$tr.attr('data-loading', 'N');
			$tr.attr('data-loading-delete', 'N');

			if (data.resultCode.substr(0, 2) == 'S-') {
				$tr.remove();
			} else {
				if (data.msg) {
					alert(data.msg)
				}
			}
		}, 'json');
	}
</script>

<div class="template-box template-box-1">
	<table border="1">
		<tbody>
			<tr data-article-reply-id="{$번호}">
				<td>{$번호}</td>
				<td>{$날짜}</td>
				<td>
					<div class="reply-body-text modify-mode-none">{$내용}</div>

					<div class="modify-mode-block">
						<form
							onsubmit="ArticleReply__submitModifyReplyForm(this); return false;">
							<textarea style="width: 100%; resize: none" maxlength="300"
								class="min-height-100px" name="body">{$내용}</textarea>
							<br /> <input class="loading-none" type="submit" value="수정" />
						</form>
					</div>
				</td>
				<td><span class="loading-delete-inline">삭제중입니다...</span> <a
					class="loading-none" href="#"
					onclick="if ( confirm('정말 삭제하시겠습니까?') ) { ArticleReply__delete(this); } return false;">삭제</a>
					<a class="loading-none modify-mode-none" href="#"
					onclick="ArticleReply__enableModifyMode(this); return false;">수정</a>
					<a class="loading-none modify-mode-inline" href="#"
					onclick="ArticleReply__disableModifyMode(this); return false;">수정취소</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="article-reply-list-box table-box con">
	<table>
		<colgroup>
			<col width="80">
			<col width="180">
			<col>
			<col width="200">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>내용</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>
</div>
<div class="backHome">
	<a href="list">리스트로 돌아가기</a>
</div>
<%@ include file="../part/foot.jspf"%>