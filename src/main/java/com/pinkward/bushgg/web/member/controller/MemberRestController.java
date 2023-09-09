//package com.pinkward.bushgg.web.member.controller;
//
//import java.util.List;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.pinkward.bushgg.domain.member.dto.Member;
//import com.pinkward.bushgg.domain.member.service.MemberService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Controller
////@RestController
//@RequiredArgsConstructor
//@RequestMapping("/member2")
//@Slf4j
//public class MemberRestController {
//	
//	private final MemberService memberService;
//	
//	// 회원 데이터 검증 -  #3. Bean Validation 사용
//	@PostMapping("/register")
//	@ResponseBody
//	public Object register(@RequestBody @Validated  Member member, BindingResult bindingResult) {
//		log.info("회원 가입 API 요청");
//		if (bindingResult.hasErrors()) {
//			// bindingResult를 모델에 저장하고, 타임리프 HTML 템플릿 페이지에서 렌더링하는 것이 아니라
//			// bindingResult에 저장한 오류 메시지를 읽어와서 응답메시지 바디에 JSON으로 출력한다.
//			return bindingResult.getAllErrors();
//		}
//		
//		// 데이터 검증 성공 시 DB 저장 후 응답메시지 바디에 회원정보를 JSON으로 출력한다.
//		memberService.register(member);
//		return member;
//	}
//}
//
//
//
//
//
//
