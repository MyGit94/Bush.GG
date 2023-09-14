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
	
//	@Test
//	@Disabled
//	public void findByAllTest() {
//		List<MemberDTO> list = memberMapper.findByAll();
//		for (MemberDTO member : list) {
//			log.info(member.toString());
//		}
//	}
//	
//	
//	@Test
//	@Disabled
//	public void findByIdTest() {
//		// given
//		String memberNickName = "user";
//		// when
//		MemberDTO member = memberMapper.findByNick(memberNickName);
//		// then
//		assertThat(memberNickName)
//			.isNotNull();
//		log.info("회원정보 : {}", member.toString());
//	}
//	
//	@Test
//	@Disabled
//	void findByNameLikeTest() {
//		String findName = "김";
//		List<Member> list = memberMapper.findByNameLike(findName);
//		log.info("이름 와일드카드 검색 : {}", list);
//		for (Member member : list) {
//			log.info(member.toString());
//		}
//	}
//	
	@Test
//	@Disabled
	void createTest() {
		MemberDTO member = MemberDTO
				.builder()
				.loginId("userTest")
				.nickName("유저테스트")
				.passwd("1234")
				.email("user@test.com")
				.role("유저")
				.build();
		memberMapper.create(member);
		log.info("회원 등록 완료 : {}", member);
	}
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
