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
import com.sbs.byk.at.dto.ArticleReply;
import com.sbs.byk.at.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("article/getForPrintArticleRepliesRs")
	@ResponseBody
	public Map<String, Object> getForPrintArticleRepliesRs(int articleId, int from) {
		List<ArticleReply> articleReplies = articleService.getForPrintArticleReplies(articleId, from);

		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", "S-1");
		rs.put("msg", String.format("총 %d개의 댓글이 있습니다.", articleReplies.size()));
		rs.put("articleReplies", articleReplies);

		return rs;
	}

	@RequestMapping("/article/list")
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

	@RequestMapping("/article/detail")
	public String showDetail(Model model, int id) {
		Article article = articleService.getOne(id);

		int firstId = articleService.getFirstIdFromArticle();
		int lastId = articleService.getLastIdFromArticle();

		Article articleNext = articleService.getNextArticle(id);

		Article articlePrevious = articleService.getPreviousArticle(id);
		List<ArticleReply> articleReplies = articleService.getForPrintArticleReplies(id);

		model.addAttribute("article", article);
		model.addAttribute("firstId", firstId);
		model.addAttribute("lastId", lastId);
		model.addAttribute("articleNext", articleNext);
		model.addAttribute("articlePrevious", articlePrevious);
		model.addAttribute("articleReplies", articleReplies);

		return "article/detail";
	}

	@RequestMapping("/article/write")
	public String showWrite() {
		return "article/write";
	}

	@RequestMapping("/article/doWrite")
	@ResponseBody
	public String doWrite(@RequestParam Map<String, Object> param) {
		int newArticleId = articleService.write(param);

		String msg = newArticleId + "번 게시물이 생성되었습니다.";

		StringBuilder sb = new StringBuilder();

		sb.append("alert('" + msg + "');");
		sb.append("location.replace('./detail?id=" + newArticleId + "');");

		sb.insert(0, "<script>");
		sb.append("</script>");

		return sb.toString();
	}

	@RequestMapping("/article/modify")
	public String showModify(Model model, int id) {
		Article article = articleService.getOne(id);

		model.addAttribute("article", article);

		return "article/modify";
	}

	@RequestMapping("/article/doModify")
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

	@RequestMapping("/article/doDelete")
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

	@RequestMapping("/article/doWriteReply")
	@ResponseBody
	public String doWriteReply(@RequestParam Map<String, Object> param) {
		int articleId = Util.getAsInt(param.get("articleId"));
		articleService.writeReply(param);

		String msg = articleId + "번 게시물에 댓글을 작성했습니다.";

		StringBuilder sb = new StringBuilder();

		sb.append("alert('" + msg + "');");
		sb.append("location.replace('./detail?id=" + articleId + "');");

		sb.insert(0, "<script>");
		sb.append("</script>");
		return sb.toString();
	}

	@RequestMapping("/article/doDeleteReply")
	@ResponseBody
	public String doDeleteReply(int id, int articleId) {
		articleService.deleteReply(id);

		String msg = "댓글이 삭제되었습니다.";

		StringBuilder sb = new StringBuilder();

		sb.append("alert('" + msg + "');");
		sb.append("location.replace('./detail?id=" + articleId + "');");

		sb.insert(0, "<script>");
		sb.append("</script>");

		return sb.toString();
	}

	@RequestMapping("/article/doDeleteReplyAjax")
	@ResponseBody
	public Map<String, Object> doDeleteReply(int id) {

		Map<String, Object> rs = articleService.deleteArticleReply(id);

//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		return rs;
	}

	@RequestMapping("/article/modifyReply")
	public String showModifyReply(Model model, int id) {
		ArticleReply articleReply = articleService.getArticleReplyById(id);

		model.addAttribute("articleReply", articleReply);

		return "article/modifyReply";
	}

	@RequestMapping("/article/doModifyReply")
	@ResponseBody
	public String doModifyReply(@RequestParam Map<String, Object> param) {
		int id = Util.getAsInt(param.get("id"));
		int articleId = Util.getAsInt(param.get("articleId"));

		articleService.modifyReply(param);

		String msg = id + "번 댓글이 수정되었습니다.";

		StringBuilder sb = new StringBuilder();

		sb.append("alert('" + msg + "');");
		sb.append("location.replace('./detail?id=" + articleId + "');");

		sb.insert(0, "<script>");
		sb.append("</script>");

		return sb.toString();
	}

	@RequestMapping("article/doWriteReplyAjax")
	@ResponseBody
	public Map<String, Object> doWriteReplyAjax(@RequestParam Map<String, Object> param) {

		Map<String, Object> rs = articleService.writeReply1(param);

		return rs;
	}
}
