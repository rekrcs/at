package com.sbs.byk.at.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.byk.at.dto.Reply;

@Mapper
public interface ReplyDao {
	List<Reply> getForPrintRepliesFrom(Map<String, Object> param);

	void writeReply(Map<String, Object> param);

	Reply getReplyById(@Param("id") int id);

	void deleteReply(@Param("id") int id);

	void modifyReply(Map<String, Object> param);
}
