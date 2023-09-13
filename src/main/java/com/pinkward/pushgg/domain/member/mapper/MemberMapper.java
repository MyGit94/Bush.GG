package com.pinkward.pushgg.domain.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinkward.bushgg.domain.member.dto.Member;

public interface MemberMapper {

	public List<Member> findByAll();
	public Member findPasswd(@Param("loginid") String loginid,@Param("nickname") String nickname
			,@Param("email") String email);
}
