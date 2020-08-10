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
import com.sbs.byk.at.dto.ArticleReply;
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

	public List<ArticleReply> getForPrintArticleReplies(int articleId) {
		return articleDao.getForPrintArticleReplies(articleId);
	}

	public void deleteReply(int id) {
		articleDao.deleteReply(id);
	}

	public ArticleReply getArticleReplyById(int id) {
		return articleDao.getArticleReplyById(id);
	}

	public ResultData modifyReply(Map<String, Object> param) {
		articleDao.modifyReply(param);
		return new ResultData("S-1", String.format("%d번 댓글을 수정하였습니다.", Util.getAsInt(param.get("id"))), param);
	}

	public List<ArticleReply> getForPrintArticleReplies(@RequestParam Map<String, Object> param) {
		List<ArticleReply> articleReplies = articleDao.getForPrintArticleRepliesFrom(param);

		Member actor = (Member) param.get("actor");

		for (ArticleReply articleReply : articleReplies) {
			// 출력용 부가데이터를 추가한다.
			updateForPrintInfo(actor, articleReply);
		}

		return articleReplies;

	}

	private void updateForPrintInfo(Member actor, ArticleReply articleReply) {
		articleReply.getExtra().put("actorCanDelete", actorCanDelete(actor, articleReply));
		articleReply.getExtra().put("actorCanModify", actorCanModify(actor, articleReply));
	}

	// 액터가 해당 댓글을 수정할 수 있는지 알려준다.
	public boolean actorCanModify(Member actor, ArticleReply articleReply) {
		return actor != null && actor.getId() == articleReply.getMemberId() ? true : false;
	}

	// 액터가 해당 댓글을 삭제할 수 있는지 알려준다.
	public boolean actorCanDelete(Member actor, ArticleReply articleReply) {
		return actorCanModify(actor, articleReply);
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
