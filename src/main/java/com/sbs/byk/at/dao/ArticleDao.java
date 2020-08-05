package com.sbs.byk.at.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sbs.byk.at.dto.Article;
import com.sbs.byk.at.dto.ArticleReply;

@Mapper
public interface ArticleDao {
	List<Article> getForPrintArticles(int page, int itemsInAPage, int limitFrom, String searchKeyword,
			String searchKeywordType);

	Article getOne(int id);

	void write(String title, String body);

	int write(Map<String, Object> param);

	void modify(Map<String, Object> param);

	void delete(int id);

	int getFirstIdFromArticle();

	int getListIdFromArticle();

	Article getNextArticle(int id);

	Article getPreviousArticle(int id);

	int getTotalCount(String searchKeyword, String searchKeywordType);

	int writeReply(Map<String, Object> param);

	List<ArticleReply> getForPrintArticleRelies(int articleId);

	void deleteReply(int id);

	ArticleReply getArticleReplyById(int id);

	void modifyReply(Map<String, Object> param);

}
