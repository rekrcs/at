package com.sbs.byk.at.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.byk.at.Util.Util;
import com.sbs.byk.at.dto.Article;
import com.sbs.byk.at.dto.Reply;
import com.sbs.byk.at.dto.Member;
import com.sbs.byk.at.dto.ResultData;
import com.sbs.byk.at.service.ArticleService;
import com.sbs.byk.at.service.ReplyService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ReplyService replyService;

	@RequestMapping("/usr/article/getForPrintRepliesRs")
	@ResponseBody
	public Map<String, Object> getForPrintRepliesRs(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		param.put("actor", loginedMember);
		param.put("relTypeCode", "article");
		List<Reply> replies = replyService.getForPrintReplies(param);

		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("총 %d개의 댓글이 있습니다.", replies.size()));
		rs.put("replies", replies);

		return rs;
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, String page, String searchKeyword, String searchKeywordType) {
		if (page == null) {
			page = "1";
		}

		int page1 = Integer.parseInt(page);

		int itemsInAPage = 10;
		int totalCount = articleService.getTotalCount(searchKeyword, searchKeywordType);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
		int limitFrom = (page1 - 1) * itemsInAPage;

		List<Article> articles = articleService.getForPrintArticles(page1, itemsInAPage, limitFrom, searchKeyword,
				searchKeywordType);

		model.addAttribute("page", page1);
		model.addAttribute("articles", articles);
		model.addAttribute("totalPage", totalPage);

		return "article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
		Article article = articleService.getForPrintArticleById(id);

		int firstId = articleService.getFirstIdFromArticle();
		int lastId = articleService.getLastIdFromArticle();

		Article articleNext = articleService.getNextArticle(id);

		Article articlePrevious = articleService.getPreviousArticle(id);
		List<Reply> replies = articleService.getForPrintReplies(id);

		model.addAttribute("article", article);
		model.addAttribute("firstId", firstId);
		model.addAttribute("lastId", lastId);
		model.addAttribute("articleNext", articleNext);
		model.addAttribute("articlePrevious", articlePrevious);
		model.addAttribute("replies", replies);

		return "article/detail";
	}

	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	public String doWrite(@RequestParam Map<String, Object> param) {
		int newArticleId = articleService.write(param);

		String redirectUri = (String) param.get("redirectUri");
		redirectUri = redirectUri.replace("#id", newArticleId + "");

		return "redirect:" + redirectUri;
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {
		Article article = articleService.getForPrintArticleById(id);

		model.addAttribute("article", article);

		return "article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(@RequestParam Map<String, Object> param, int id) {
		articleService.modify(param);

		String msg = id + "번 게시물이 수정되었습니다.";

		StringBuilder sb = new StringBuilder();

		sb.append("alert('" + msg + "');");
		sb.append("location.replace('./detail?id=" + id + "');");

		sb.insert(0, "<script>");
		sb.append("</script>");

		return sb.toString();
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
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
