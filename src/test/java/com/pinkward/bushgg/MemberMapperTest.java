package com.pinkward.bushgg;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberMapperTest {
	
	@Autowired
	private MemberMapper memberMapper;
	
	// 멤버 전체 조회
	@Test
	@Disabled
	public void findByAllTest() {
		List<MemberDTO> list = memberMapper.findByAll();
		for (MemberDTO member : list) {
			log.info(member.toString());
		}
	}
	
	// loginId, nickName, email 이용해 사용자 정보조회
	@Test
	@Disabled
	public void findByPasswdTest() {
		// given
		String loginId = "userTest3";
		String nickName = "유저테스트3";
		String email = "user3@test.com";
		// when
		MemberDTO memberDTO = memberMapper.findByPasswd(loginId, nickName, email);
		// then
		assertThat(memberDTO)
			.isNotNull();
		log.info("회원정보 : {}", memberDTO.toString());
	}
	
	
	// value값이 포함된 닉네임 조회
	@Test
	@Disabled
	void findBySearchNickTest() {
		//List<Member> list = memberMapper.findBySearchType("id", "bangry");
		List<MemberDTO> list = memberMapper.findBySearchNick("nickName", "유");
		log.info("검색 타입별 검색 회원 전체목록 : {}", list);
	}
	
	// 로그인 아이디로 닉네임 수정
	@Test
	@Disabled
	void updateNickNameTest() {
		MemberDTO updateMember = MemberDTO
				.builder()
				.loginId("userTest3")
				.nickName("테스트")
				.build();
		memberMapper.updateNickName(updateMember);
		log.info("회원 수정 완료 : {}", updateMember);
	}
	
	// 회원 가입
	@Test
//	@Disabled
	void createTest() {
		MemberDTO member = MemberDTO
				.builder()
				.loginId("userTest5")
				.nickName("유저테스트5")
				.passwd("1234")
				.email("user5@test.com")
				.role("유저")
				.build();
		memberMapper.create(member);
		log.info("회원 등록 완료 : {}", member);
	}
	
	
//	@Test
//	public void findBySearchNickTest() {
//	    // Given
//	    String nickName = "유저테스트"; 
//	    
//	    // When
//	    List<MemberDTO> members = memberMapper.findBySearchNick(nickName);
//
//	    // Then
//	    assertThat(members).isNotNull(); 
//	    assertThat(members).isNotEmpty();
//	    for (MemberDTO member : members) {
//	        assertThat(member.getNickName()).isEqualTo(nickName);
//	    }
//	    for (MemberDTO member : members) {
//	        log.info("Member information: {}", member.toString());
//	    }
//	}
	

//	
//	@Test
//	@Disabled
//	void updateTest() {
//		Member updateMember = Member
//				.builder()
//				.id("sony")
//				.passwd("2222")
//				.build();
//		memberMapper.update(updateMember);
//		log.info("회원 수정 완료 : {}", updateMember);
//	}
//	
//	
//	@Test
//	@Disabled
//	void findBySearchTypeTest() {
////		List<Member> list = memberMapper.findBySearchType("id", "bangry");
//		List<Member> list = memberMapper.findBySearchType("name", "김");
//		log.info("검색 타입별 검색 회원 전체목록 : {}", list);
//	}
//	
//	@Test
//	@Disabled
//	void findBySearchAllTest() {
////		List<Member> list = memberMapper.findBySearchAll("bangry");
//		List<Member> list = memberMapper.findBySearchAll("김");
//		log.info("모든 검색 전체목록 : {}", list);
//	}
//	
//	@Test
////	@Disabled
//	void findBySearchAllOptionTest() {
//		MemberSearchCondition searchCondition = 
//				MemberSearchCondition
//				.builder()
////				.memberId("bangry")
////				.name("김")
////				.email("bangry@gmail.com")
//				.build();
//		List<Member> list = memberMapper.findBySearchAllOption(searchCondition);
//		log.info("검색 옵션별 전체목록 : {}", list);
//		log.info("검색 수 : {}", list.size());
//	}
	

}
