package com.pinkward.bushgg.domain.member.service;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;

/**
 * 회원 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface MemberService {
	public void register(MemberDTO member);
	public MemberDTO isMember(String loginId, String passwd);
	boolean checkLoginId(String loginId);
	boolean checkNickName(String nickName);
	boolean checkEmail(String email);

}
