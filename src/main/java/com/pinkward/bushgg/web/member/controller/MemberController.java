package com.pinkward.bushgg.web.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/login")
	public String login(Model model) {
	    MemberDTO member = MemberDTO.builder().build();
	    model.addAttribute("member", member);
	    return "member/login";
	}

	@PostMapping("/login") 
	public String login(@ModelAttribute MemberDTO member, HttpServletRequest request , RedirectAttributes redirectAttributes) {
	    String loginId = member.getLoginId();
	    String passwd = member.getPasswd();
	    
	    // 아이디와 비밀번호로 회원 정보 조회
	    MemberDTO loginMember = memberService.isMember(loginId, passwd);
	    
	    // 회원이 아닌 경우
	    if (loginMember == null) {
	        return "redirect:/member/login";
	    }
	    
	    // 회원인 경우
	    HttpSession session = request.getSession();
	    session.setAttribute("loginMember", loginMember);
	    
	    // nickName 값을 redirectAttributes에 추가
	    redirectAttributes.addAttribute("loginMember", loginMember);
	    log.info("수신받은 loginMember 객체 : {} ", loginMember);
	    
	    return "redirect:/";
	}

	
	@GetMapping("/register")
	public String register(Model model) {
	    MemberDTO member = MemberDTO.builder().build();
	    model.addAttribute("member", member);
	    return "member/register"; // 수정된 경로
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute MemberDTO member,
	                       RedirectAttributes redirectAttributes, Model model) {

	    // 데이터 저장 시 데이터 검증을 수행하지 않고 직접 DB에 저장
	    memberService.register(member);
	    
	    log.info("DB 저장 완료");

	    // 회원 가입 후 보여주는 회원 상세 페이지 타이틀과
	    // 회원 정보 조회 시 보여주는 회원 상세 페이지 타이틀을 변경할 수 있도록 status 속성 추가
//	    redirectAttributes.addFlashAttribute("status", true);
	    return "redirect:/member/login";
	}

	// 회원 데이터 검증 - #3. Bean Validation 사용
//	@PostMapping("/register")
//	public String register(@Validated @ModelAttribute MemberDTO member, BindingResult bindingResult,
//			RedirectAttributes redirectAttributes, Model model) {
//
//		// 데이터 검증 실패 시 회원가입 화면으로 Forward
//		if (bindingResult.hasErrors()) {
//			return "member/register";
//		}
//		// 데이터 검증 성공 시 DB 저장 후 상세 페이지로 리다이렉트
//		memberService.register(member);
//		
//		log.info("db저장완료");
//
//		// 회원 가입 후 보여주는 회원 상세 페이지 타이틀과
//		// 회원 정보 조회 시 보여주는 회원 상세 페이지 타이틀을 변경할 수 있도록 status 속성 추가
//		redirectAttributes.addFlashAttribute("status", true);
//		return "redirect:/member/register";
//	}
	
}
