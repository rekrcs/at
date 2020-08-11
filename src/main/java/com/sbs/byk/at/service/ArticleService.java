package com.sbs.byk.at.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.byk.at.Util.Util;
import com.sbs.byk.at.dao.ArticleDao;
import com.sbs.byk.at.dto.Article;
import com.sbs.byk.at.dto.Reply;
import com.sbs.byk.at.dto.Member;
import com.sbs.byk.at.dto.ResultData;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public int getCount() {
		return 5;
	}

	public List<Article> getForPrintArticles(int page, int itemsInAPage, int limitFrom, String searchKeyword,
			String searchKeywordType) {
		List<Article> articles = articleDao.getForPrintArticles(page, itemsInAPage, limitFrom, searchKeyword,
				searchKeywordType);

		return articles;
	}

	public Article getOne(int id) {
		Article article = articleDao.getOne(id);
		return article;
	}

	public int write(Map<String, Object> param) {
		articleDao.write(param);

		return Util.getAsInt(param.get("id"));
	}

	public void modify(Map<String, Object> param) {
		articleDao.modify(param);

	}

	public void delete(int id) {
		articleDao.delete(id);
	}

	public int getFirstIdFromArticle() {
		return articleDao.getFirstIdFromArticle();
	}

	public int getLastIdFromArticle() {
		return articleDao.getListIdFromArticle();
	}

	public Article getNextArticle(int id) {
		return articleDao.getNextArticle(id);
	}

	public Article getPreviousArticle(int id) {
		return articleDao.getPreviousArticle(id);
	}

	public int getTotalCount(String searchKeyword, String searchKeywordTypeString) {
		return articleDao.getTotalCount(searchKeyword, searchKeywordTypeString);
	}

	public int writeReply(Map<String, Object> param) {
		articleDao.writeReply(param);

		return Util.getAsInt(param.get("id"));
	}

	public List<Reply> getForPrintReplies(int articleId) {
		return articleDao.getForPrintReplies(articleId);
	}

	public void deleteReply(int id) {
		articleDao.deleteReply(id);
	}

	public Reply getReplyById(int id) {
		return articleDao.getReplyById(id);
	}

	public ResultData modifyReply(Map<String, Object> param) {
		articleDao.modifyReply(param);
		return new ResultData("S-1", String.format("%d번 댓글을 수정하였습니다.", Util.getAsInt(param.get("id"))), param);
	}

	public List<Reply> getForPrintReplies(@RequestParam Map<String, Object> param) {
		List<Reply> replies = articleDao.getForPrintRepliesFrom(param);

		Member actor = (Member) param.get("actor");

		for (Reply reply : replies) {
			// 출력용 부가데이터를 추가한다.
			updateForPrintInfo(actor, reply);
		}

		return replies;

	}

	private void updateForPrintInfo(Member actor, Reply reply) {
		reply.getExtra().put("actorCanDelete", actorCanDelete(actor, reply));
		reply.getExtra().put("actorCanModify", actorCanModify(actor, reply));
	}

	// 액터가 해당 댓글을 수정할 수 있는지 알려준다.
	public boolean actorCanModify(Member actor, Reply reply) {
		return actor != null && actor.getId() == reply.getMemberId() ? true : false;
	}

	// 액터가 해당 댓글을 삭제할 수 있는지 알려준다.
	public boolean actorCanDelete(Member actor, Reply reply) {
		return actorCanModify(actor, reply);
	}
}
