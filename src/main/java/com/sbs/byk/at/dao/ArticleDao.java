package com.sbs.byk.at.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.byk.at.dto.Article;
import com.sbs.byk.at.dto.Reply;

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

	List<Reply> getForPrintReplies(int articleId);

	Reply getReplyById(int id);

	List<Reply> getForPrintRepliesFrom(Map<String, Object> param);

	void deleteReply(@Param("id") int id);

	void modifyReply(Map<String, Object> param);

}
