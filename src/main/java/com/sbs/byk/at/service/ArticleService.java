package com.sbs.byk.at.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Article getOne(long id) {
		Article article = articleDao.getOne(id);
		return article;
	}

	public long add(Map<String, Object> param) {
		long newId = articleDao.add(param);
		return newId;
	}

	public void modify(Map<String, Object> param) {
		articleDao.modify(param);

	}

	public void delete(long id) {
		articleDao.delete(id);
	}
}
