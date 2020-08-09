package com.sbs.byk.at.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.byk.at.dto.Member;



@Mapper
public interface MemberDao {

	Member getMemberById(@Param("id") int id);

}
