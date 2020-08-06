package com.sbs.byk.at.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.byk.at.Util.Util;
import com.sbs.byk.at.dao.ArticleDao;
import com.sbs.byk.at.dto.Article;
import com.sbs.byk.at.dto.ArticleReply;

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
		return articleDao.writeReply(param);
	}

	public List<ArticleReply> getForPrintArticleReplies(int articleId) {
		return articleDao.getForPrintArticleReplies(articleId);
	}

	public void deleteReply(int id) {
		articleDao.deleteReply(id);
	}

	public ArticleReply getArticleReplyById(int id) {
		return articleDao.getArticleReplyById(id);
	}

	public void modifyReply(Map<String, Object> param) {
		articleDao.modifyReply(param);

	}

	public Map<String, Object> writeReply1(Map<String, Object> param) {
		articleDao.writeReply(param);
		int id = Util.getAsInt(param.get("id"));
		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물 댓글이 생성되었습니다.", id));

		return rs;
	}

	public List<ArticleReply> getForPrintArticleReplies(int articleId, int from) {
		return articleDao.getForPrintArticleRepliesFrom(articleId, from);
	}

	public Map<String, Object> deleteArticleReply(int id) {
		articleDao.deleteArticleReply(id);
		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물 댓글이 삭제되었습니다.", id));

		return rs;
	}

	public Map<String, Object> modifyArticleReply(Map<String, Object> param) {
		articleDao.modifyArticleReply(param);
		int id = Util.getAsInt(param.get("id"));
		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("%d번 게시물 댓글이 수정되었습니다.", id));

		return rs;
	}
}
