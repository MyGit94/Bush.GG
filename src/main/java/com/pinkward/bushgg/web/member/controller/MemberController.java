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

import com.ezen.springmvc.domain.member.dto.Member;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.service.MemberService;

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
//		MemberDTO member = MemberDTO.builder().build();
//		model.addAttribute("member", member);
		return "member/login";
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
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
		// 데이터 검증 성공 시 DB 저장 후 상세 페이지로 리다이렉트
		memberService.register(member);
		
		log.info("db저장완료");

		// 회원 가입 후 보여주는 회원 상세 페이지 타이틀과
		// 회원 정보 조회 시 보여주는 회원 상세 페이지 타이틀을 변경할 수 있도록 status 속성 추가
		redirectAttributes.addFlashAttribute("status", true);
		return "redirect:/member/register";
	}
	
}
