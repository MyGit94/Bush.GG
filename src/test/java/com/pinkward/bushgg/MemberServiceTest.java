package com.pinkward.bushgg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberServiceTest {
	
	@Autowired
	private MemberService memberService;
	
	@Test
//	@Disabled
	public void isMemberTest() {
		String loginId = "register", passwd = "1234";
		MemberDTO loginMember = memberService.isMember(loginId, passwd);
		log.info("로그인 사용자 정보 {}",loginMember);
	}

	
	
	

}