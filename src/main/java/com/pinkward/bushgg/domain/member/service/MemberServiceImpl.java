package com.pinkward.bushgg.domain.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
//	의존성주입... @RequiredArgsConstructor 이용해서 생성자 자동 생성
	private final MemberMapper memberMapper;

	@Override
	@Transactional
	public void register(MemberDTO member) {
		memberMapper.create(member);
		
	}

	@Override
	public MemberDTO isMember(String loginId, String passwd) {
		
		return memberMapper.findByIdAndPasswd(loginId,passwd);
	}

	@Override
	public List<MemberDTO> getMemberList() {
		return memberMapper.findByAll();
	}

	@Override
	public MemberDTO getMember(String nickName) {
		return memberMapper.findByNick(nickName);
	}

	@Override
	public boolean checkDuplicate(String loginId) {
	    return memberMapper.existsByLogin(loginId);
	}
}
