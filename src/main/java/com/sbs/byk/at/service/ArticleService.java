package com.sbs.byk.at.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sbs.byk.at.Util.Util;
import com.sbs.byk.at.dto.Article;

@Service
public class ArticleService {
	public int getCount() {
		return 5;
	}

	public List<Article> getForPrintArticles() {
		List<Article> articles = new ArrayList<>();

		articles.add(new Article(1, Util.getNowDateStr(), Util.getNowDateStr(), false, "", true, "제목1", "내용1"));
		articles.add(new Article(2, Util.getNowDateStr(), Util.getNowDateStr(), false, "", true, "제목2", "내용2"));

		return articles;
	}
}
