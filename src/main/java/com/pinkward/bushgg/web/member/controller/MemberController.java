package com.pinkward.bushgg.web.member.controller;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	// 회원 데이터 검증 - #3. Bean Validation 사용
	@PostMapping("/register")
	public String register(@Validated @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult,
	        RedirectAttributes redirectAttributes, Model model) {

	    // 데이터 검증 실패 시 회원가입 화면으로 Forward
	    if (bindingResult.hasErrors()) {
	        return "member/register";
	    }
	    
	    // 데이터 검증 성공 시 DB 저장 후 성공 메시지와 함께 홈페이지로 리다이렉트
	    memberService.register(memberDTO);
	    
	    log.info("DB저장완료 : {}");
	    
	    // 리다이렉트 시 메시지 전달
	    redirectAttributes.addFlashAttribute("successMessage", "회원 가입이 성공적으로 완료되었습니다.");
	    return "redirect:/member/login";
	}

	
	@GetMapping("/login")
	public String login(Model model, @CookieValue(name = "userId", required = false) String userId) {
	    MemberDTO memberDTO = MemberDTO.builder().build();
	    model.addAttribute("memberDTO", memberDTO);
		model.addAttribute("userId",userId);
		log.info("{}",userId);
	    return "member/login";
	}
	
	@PostMapping("/login") 
	public String login(@RequestParam(name = "saveId", required = false) boolean saveId, @Valid @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		log.info("{}",saveId);
		MemberDTO loginMember = memberService.isMember(memberDTO.getLoginId(), memberDTO.getPasswd());
		if (saveId) {
			// 아이디를 쿠키에 저장
			Cookie cookie = new Cookie("userId", memberDTO.getLoginId());
			cookie.setMaxAge(30 * 24 * 60 * 60);
			response.addCookie(cookie);
			log.info("Saved userId cookie with value: " + memberDTO.getLoginId());
		} else {
			// 쿠키 삭제
			Cookie cookie = new Cookie("userId", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			log.info("Removed userId cookie");
		}
		// 회원이 아닌 경우
		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "member/login";
		}
		// 회원인 경우
		HttpSession session =  request.getSession();
		session.setAttribute("loginMember", loginMember);
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

	@GetMapping("/nickname")
	public String nickname(Model model) {
		model.addAttribute("successMessage2", "닉네임 등록이 성공적으로 완료되었습니다.");
		return "member/nickname";
	}

	@PostMapping("/nickname")
	public String nicknameFinish(@RequestParam String nickName, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		String userEmail = (String) session.getAttribute("userEmail");
		log.info("닉네임 : {}",nickName);
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
