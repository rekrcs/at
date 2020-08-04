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

	public List<Article> getForPrintArticles(int page, int itemsInAPage, int limitFrom) {
		List<Article> articles = articleDao.getForPrintArticles(page, itemsInAPage, limitFrom);

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

	public int getFirstIdFromArticle() {
		return articleDao.getFirstIdFromArticle();
	}

	public int getLastIdFromArticle() {
		return articleDao.getListIdFromArticle();
	}

	public Article getNextArticle(long id) {
		return articleDao.getNextArticle(id);
	}

	public Article getPreviousArticle(long id) {
		return articleDao.getPreviousArticle(id);
	}

	public int getTotalCount() {
		return articleDao.getTotalCount();
	}
}
