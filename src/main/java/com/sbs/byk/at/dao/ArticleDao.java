package com.sbs.byk.at.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sbs.byk.at.dto.Article;

@Mapper
public interface ArticleDao {
	List<Article> getForPrintArticles();

	Article getOne(long id);

	void write(String title, String body);

	long add(Map<String, Object> param);

	void modify(Map<String, Object> param);

	void delete(long id);
}
