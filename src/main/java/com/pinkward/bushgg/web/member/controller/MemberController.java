package com.pinkward.bushgg.web.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Member 관련 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

	private final MemberService memberService;
	
	@GetMapping("/register")
	public String register(Model model) {
	    MemberDTO memberDTO = MemberDTO.builder().build();
	    model.addAttribute("memberDTO", memberDTO);
	    return "member/register";
	}
	
	@PostMapping("/register")
	public String register(@Validated @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult,
	        RedirectAttributes redirectAttributes, Model model) {
		
	    if (bindingResult.hasErrors()) {
	        return "member/register";
	    }
		
	    memberService.register(memberDTO);
		
	    redirectAttributes.addFlashAttribute("successMessage", "회원 가입이 성공적으로 완료되었습니다.");
	    return "redirect:/member/login";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
	    MemberDTO memberDTO = MemberDTO.builder().build();
	    model.addAttribute("memberDTO", memberDTO);
	    return "member/login";
	}
	
	@PostMapping("/login") 
	public String login(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult, HttpServletRequest request) {
		
		MemberDTO loginMember = memberService.isMember(memberDTO.getLoginId(), memberDTO.getPasswd());
		
		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "member/login";
		}
		HttpSession session =  request.getSession();
		session.setAttribute("loginMember", loginMember);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")  
	public String logout(HttpServletRequest request) {

		HttpSession session =  request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

	@GetMapping("/nickname")
	public String nickname(Model model) {
		model.addAttribute("successMessage2", "닉네임 등록이 성공적으로 완료되었습니다.");
		return "member/nickname";
	}

	@PostMapping("/nickname")
	public String nicknameFinish(@RequestParam String nickName, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		String userEmail = (String) session.getAttribute("userEmail");

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setNickName(nickName);
		memberDTO.setPasswd("1111");
		memberDTO.setLoginId(userId);
		memberDTO.setEmail(userEmail);
		memberService.register(memberDTO);
		MemberDTO loginMember = memberService.isMember(memberDTO.getLoginId(), memberDTO.getPasswd());
		session.setAttribute("loginMember", loginMember);
		return "redirect:/";
	}

	@GetMapping("/googleLogin")
	public String gooleLogin(HttpSession session){
		MemberDTO member = (MemberDTO) session.getAttribute("Member");
		session.setAttribute("loginMember", member);
		return "redirect:/";
	}


}
