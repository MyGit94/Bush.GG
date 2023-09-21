package com.pinkward.bushgg.web.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pinkward.bushgg.domain.member.dto.LoginForm;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

	private final MemberService memberService;
	
	  @GetMapping("/api/check-duplicate-loginid/{loginId}")
	    public ResponseEntity<Map<String, Boolean>> checkDuplicateLoginId(@PathVariable String loginId) {
	        boolean isDuplicate = memberService.checkDuplicate(loginId);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("isDuplicate", isDuplicate);
	        return ResponseEntity.ok(response);
	    }

	    @GetMapping("/register")
	    public String register(Model model) {
	        MemberDTO memberDTO = MemberDTO.builder().build();
	        model.addAttribute("memberDTO", memberDTO);
	        return "member/register";
	    }

	    // 회원 데이터 검증 - #3. Bean Validation 사용
	    @PostMapping("/register")
	    public String register(@Validated @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult,
	            RedirectAttributes redirectAttributes,HttpServletRequest request, Model model) {

	        // 데이터 검증 실패 시 회원가입 화면으로 Forward
	        if (bindingResult.hasErrors()) {
	            return "member/register";
	        }

	        // 중복 체크
	        boolean isDuplicate = memberService.checkDuplicate(memberDTO.getLoginId());
	        if (isDuplicate) {
	        	
	    		// 세션이 있으면 기존 세션 반환, 없으면 생성하지 않고 null 반환
	    		HttpSession session =  request.getSession(false);
	    		if(session != null) {
	    			session.invalidate();
	    		}
	    		
		        // 데이터 검증 성공 시 비밀번호 확인
		        if (!memberDTO.getPasswd().equals(memberDTO.getCheckpasswd())) {
		            model.addAttribute("checkPasswordError", "비밀번호가 일치하지 않습니다.");
		            return "member/register";
		        }
	        	
	            model.addAttribute("loginIdError", "이미 사용 중인 아이디입니다.");
	            return "member/register";
	        }

	        // 데이터 검증 성공 시 DB 저장 후 성공 메시지와 함께 홈페이지로 리다이렉트
	        memberService.register(memberDTO);

	        log.info("DB 저장 완료 : {}", memberDTO.getLoginId());

	        // 리다이렉트 시 메시지 전달
	        redirectAttributes.addFlashAttribute("successMessage", "회원 가입이 성공적으로 완료되었습니다.");
	        return "redirect:/member/login";
	    }

	
	@GetMapping("/login")
	public String loginForm(Model model) {
		LoginForm loginForm = LoginForm.builder().build();
		model.addAttribute("loginForm", loginForm);
		return "member/login";
	}

	@PostMapping("/login") 
	public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
		
//		log.info("{}",10/0);
		
		if (bindingResult.hasErrors()) {
		    return "member/login";
		}

		MemberDTO loginMember = memberService.isMember(loginForm.getLoginId(), loginForm.getPasswd());
		
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

	
}
