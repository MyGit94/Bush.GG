package com.pinkward.bushgg.domain.member.service;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

	private final MemberMapper memberMapper;

	/**
	 * 회원 가입 메소드
	 * @param member 회원 정보
	 */
	@Override
	@Transactional
	public void register(MemberDTO member) {
		memberMapper.create(member);

	}

	/**
	 * 회원인지 확인하는 메소드
	 * @param loginId 로그인 아이디
	 * @param passwd 비밀번호
	 * @return 회원 정보 객체
	 */
	@Override
	public MemberDTO isMember(String loginId, String passwd) {
		
		return memberMapper.findByIdAndPasswd(loginId,passwd);
	}

	/**
	 * 로그인 아이디 중복 체크 메소드
	 * @param loginId 로그인 아이디
	 * @return loginId가 존재하면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean checkLoginId(String loginId) {
	    return memberMapper.checkLoginId(loginId);
	}

	/**
	 * 닉네임 중복 체크 메소드
	 * @param nickName 닉네임
	 * @return nickName이 존재하면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean checkNickName(String nickName) {
	    return memberMapper.checkNickName(nickName);
	}

	/**
	 * 이메일 중복 체크 메소드
	 * @param email 이메일
	 * @return email이 존재하면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean checkEmail(String email) {
		return memberMapper.checkEmail(email);
	}

}

