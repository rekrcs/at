package com.sbs.byk.at.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbs.byk.at.dto.Article;

@Mapper
public interface ArticleDao {
	List<Article> getForPrintArticles();

	Article getOne(long id);

	void write(String title, String body);

}
