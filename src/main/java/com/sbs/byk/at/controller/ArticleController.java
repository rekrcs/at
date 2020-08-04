package com.sbs.byk.at.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.byk.at.dto.Article;
import com.sbs.byk.at.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/article/list")
	public String showList(Model model, String page) {
		if (page == null) {
			page = "1";
		}

		int page1 = Integer.parseInt(page);

		int itemsInAPage = 5;
		int totalCount = articleService.getTotalCount();
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
		int limitFrom = (page1 - 1) * itemsInAPage;

		List<Article> articles = articleService.getForPrintArticles(page1, itemsInAPage, limitFrom);

		model.addAttribute("page", page1);
		model.addAttribute("articles", articles);
		model.addAttribute("totalPage", totalPage);

		return "article/list";
	}

	@RequestMapping("/article/detail")
	public String showDetail(Model model, long id) {
		Article article = articleService.getOne(id);

		int firstId = articleService.getFirstIdFromArticle();
		int lastId = articleService.getLastIdFromArticle();

		Article articleNext = articleService.getNextArticle(id);

		Article articlePrevious = articleService.getPreviousArticle(id);

		model.addAttribute("article", article);
		model.addAttribute("firstId", firstId);
		model.addAttribute("lastId", lastId);
		model.addAttribute("articleNext", articleNext);
		model.addAttribute("articlePrevious", articlePrevious);

		return "article/detail";
	}

	@RequestMapping("/article/write")
	public String showWrite() {
		return "article/write";
	}

	@RequestMapping("/article/doWrite")
	public String doWrite(@RequestParam Map<String, Object> param) {
		int newArticleId = articleService.write(param);

		String redirectUrl = (String) param.get("redirectUrl");
		redirectUrl = redirectUrl.replace("#id", newArticleId + "");

		return "redirect:" + redirectUrl;
	}

	@RequestMapping("/article/modify")
	public String showModify(Model model, long id) {
		Article article = articleService.getOne(id);

		model.addAttribute("article", article);

		return "article/modify";
	}

	@RequestMapping("/article/doModify")
	@ResponseBody
	public String doModify(@RequestParam Map<String, Object> param, long id) {
		articleService.modify(param);

		String msg = id + "번 게시물이 수정되었습니다.";

		StringBuilder sb = new StringBuilder();

		sb.append("alert('" + msg + "');");
		sb.append("location.replace('./detail?id=" + id + "');");

		sb.insert(0, "<script>");
		sb.append("</script>");

		return sb.toString();
	}

	@RequestMapping("/article/doDelete")
	@ResponseBody
	public String doDelete(long id) {
		articleService.delete(id);

		String msg = id + "번 게시물이 삭제되었습니다.";

		StringBuilder sb = new StringBuilder();

		sb.append("alert('" + msg + "');");
		sb.append("location.replace('./list');");

		sb.insert(0, "<script>");
		sb.append("</script>");

		return sb.toString();
	}
}
