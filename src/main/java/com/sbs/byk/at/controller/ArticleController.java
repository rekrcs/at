package com.sbs.byk.at.controller;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbs.byk.at.dto.Article;
import com.sbs.byk.at.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/article/list")
	public String showList(Model model) {
		int count = articleService.getCount();
		List<Article> articles = articleService.getForPrintArticles();

		model.addAttribute("count", count);
		model.addAttribute("articles", articles);

		return "article/list";
	}

	@RequestMapping("/article/detail")
	public String showDetail(Model model, long id) {
		Article article = articleService.getOne(id);

		model.addAttribute(article);

		return "article/detail";
	}

	@RequestMapping("/article/write")
	public String showWrite(Model model) {
		return "article/write";

	}

	@RequestMapping("/article/doWrite")
	public String doWrite(Model model, String title, String body) {
		articleService.write(title, body);
		
		String msg = id + "번 게시물이 수정되었습니다.";

		StringBuilder sb = new StringBuilder();

		sb.append("alert('" + msg + "');");
		sb.append("location.replace('./detail?id=" + id + "');");

		sb.insert(0, "<script>");
		sb.append("</script>");

		return sb.toString();
	}

	}
}
