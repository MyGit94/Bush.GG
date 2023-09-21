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

	@Override
	@Transactional
	public void register(MemberDTO member) {
		memberMapper.create(member);
		
	}

	@Override
	public MemberDTO isMember(String loginId, String passwd) {
		
		return memberMapper.findByIdAndPasswd(loginId,passwd);
	}
}
