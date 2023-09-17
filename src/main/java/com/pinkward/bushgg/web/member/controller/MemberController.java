package com.pinkward.bushgg.web.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

	private final MemberService memberService;
	
	@GetMapping("/register")
	public String register(Model model) {
	    MemberDTO member = MemberDTO.builder().build();
	    model.addAttribute("member", member);
	    return "member/register";
	}
	
	// 회원 데이터 검증 - #3. Bean Validation 사용
	@PostMapping("/register")
	public String register(@Validated @ModelAttribute MemberDTO member, BindingResult bindingResult,
	        RedirectAttributes redirectAttributes, Model model) {

	    // 데이터 검증 실패 시 회원가입 화면으로 Forward
	    if (bindingResult.hasErrors()) {
	        return "member/register";
	    }
	    
	    // 데이터 검증 성공 시 DB 저장 후 성공 메시지와 함께 홈페이지로 리다이렉트
	    memberService.register(member);
	    
	    // 리다이렉트 시 메시지 전달
	    redirectAttributes.addFlashAttribute("successMessage", "회원 가입이 성공적으로 완료되었습니다.");
	    return "redirect:/";
	}
		
	
//	@PostMapping("/register")
//	public String register(@ModelAttribute MemberDTO member,
//	                       RedirectAttributes redirectAttributes, Model model) {
//
//	    // 데이터 저장 시 데이터 검증을 수행하지 않고 직접 DB에 저장
//	    memberService.register(member);
//	    
//	    log.info("DB 저장 완료");
//
//	    // 회원 가입 후 보여주는 회원 상세 페이지 타이틀과
//	    // 회원 정보 조회 시 보여주는 회원 상세 페이지 타이틀을 변경할 수 있도록 status 속성 추가
////	    redirectAttributes.addFlashAttribute("status", true);
//	    return "redirect:/member/login";
//	}
	
	@GetMapping("/login")
	public String login(Model model) {
	    MemberDTO member = MemberDTO.builder().build();
	    model.addAttribute("member", member);
	    return "member/login";
	}
	
	@PostMapping("/login") 
	public String login(@ModelAttribute MemberDTO member, HttpServletRequest request) {
				
		MemberDTO loginMember = memberService.isMember(member.getLoginId(), member.getPasswd());
		
		// 회원이 아닌 경우
		if (loginMember == null) {
			
			return "member/login";
		}
		
		// 회원인 경우
		HttpSession session =  request.getSession();
	    session.setAttribute("loginMember", loginMember); 	
		
		log.info("로그인한 멤버 객체  : {} ", loginMember);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")  
	public String logout(HttpServletRequest request) {
		// 세션이 있으면 기존 세션 반환, 없으면 생성하지 않고 null 반환
		HttpSession session =  request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

	
}
