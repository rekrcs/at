package com.sbs.byk.at.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.byk.at.Util.Util;
import com.sbs.byk.at.dao.ReplyDao;
import com.sbs.byk.at.dto.Member;
import com.sbs.byk.at.dto.Reply;
import com.sbs.byk.at.dto.ResultData;

@Service
public class ReplyService {
	@Autowired
	private ReplyDao replyDao;

	public List<Reply> getForPrintReplies(@RequestParam Map<String, Object> param) {
		List<Reply> replies = replyDao.getForPrintRepliesFrom(param);

		Member actor = (Member) param.get("actor");

		for (Reply reply : replies) {
			// 출력용 부가데이터를 추가한다.
			updateForPrintInfo(actor, reply);
		}

		return replies;

	}

	private void updateForPrintInfo(Member actor, Reply reply) {
		reply.getExtra().put("actorCanDelete", actorCanDelete(actor, reply));
		reply.getExtra().put("actorCanModify", actorCanModify(actor, reply));
	}

	// 액터가 해당 댓글을 수정할 수 있는지 알려준다.
	public boolean actorCanModify(Member actor, Reply reply) {
		return actor != null && actor.getId() == reply.getMemberId() ? true : false;
	}

	// 액터가 해당 댓글을 삭제할 수 있는지 알려준다.
	public boolean actorCanDelete(Member actor, Reply reply) {
		return actorCanModify(actor, reply);
	}

	public int writeReply(Map<String, Object> param) {
		replyDao.writeReply(param);

		return Util.getAsInt(param.get("id"));
	}

	public Reply getReplyById(int id) {
		return replyDao.getReplyById(id);
	}

	public void deleteReply(int id) {
		replyDao.deleteReply(id);
	}

	public ResultData modifyReply(Map<String, Object> param) {
		replyDao.modifyReply(param);
		return new ResultData("S-1", String.format("%d번 댓글을 수정하였습니다.", Util.getAsInt(param.get("id"))), param);
	}

}
