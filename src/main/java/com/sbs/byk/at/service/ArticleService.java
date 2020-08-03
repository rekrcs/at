package com.sbs.byk.at.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.byk.at.Util.Util;
import com.sbs.byk.at.dao.ArticleDao;
import com.sbs.byk.at.dto.Article;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public int getCount() {
		return 5;
	}

	public List<Article> getForPrintArticles() {
		List<Article> articles = articleDao.getForPrintArticles();

		return articles;
	}
}
