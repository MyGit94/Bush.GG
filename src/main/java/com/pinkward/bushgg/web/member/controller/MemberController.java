package com.pinkward.bushgg.web.member.controller;

import com.pinkward.bushgg.domain.member.dto.LoginForm;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원 관련 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

	private final MemberService memberService;

    /**
     * 회원 가입 페이지로 이동하는 메소드
     * @return 회원 가입 페이지
     */
    @GetMapping("/register")
    public String register(Model model) {
        MemberDTO memberDTO = MemberDTO.builder().build();
        model.addAttribute("memberDTO", memberDTO);
        return "member/register";
    }

    /**
     * 회원 가입 정보를 받아 유효성 검증을 하고 DB에 저장하는 메소드
     * @return 로그인 페이지, 유효성 검증 실패시 회원가입 페이지
     */
    @PostMapping("/register")
    public String register(@Validated @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/register";
        }

        if (
        	memberService.checkLoginId(memberDTO.getLoginId())) {
            model.addAttribute("duplicateWarning", "중복된 값으로는 회원가입이 불가능합니다.");
            return "member/register";
        }

        if (memberService.checkNickName(memberDTO.getNickName())) {
            model.addAttribute("duplicateWarning", "중복된 닉네임으로는 회원가입이 불가능합니다.");
            return "member/register";
        }

        if (memberService.checkEmail(memberDTO.getEmail())) {
            model.addAttribute("duplicateWarning", "중복된 이메일 주소로는 회원가입이 불가능합니다.");
            return "member/register";
        }

        if (!memberDTO.getPasswd().equals(memberDTO.getCheckpasswd())) {
            model.addAttribute("checkPasswordError", "비밀번호가 일치하지 않습니다.");
            return "member/register";
        }

        memberService.register(memberDTO);

        redirectAttributes.addFlashAttribute("successMessage", "회원 가입이 성공적으로 완료되었습니다.");
        return "redirect:/member/login";
    }

    /**
     * ajax 실시간 중복체크 하는 메소드
     * @return 통과
     */
    @GetMapping("/api/check-duplicate-loginid/{loginId}")
    public ResponseEntity<Map<String, Boolean>> checkLoginId(@PathVariable String loginId) {
        boolean loginIdCheck = memberService.checkLoginId(loginId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("loginIdCheck", loginIdCheck);
        return ResponseEntity.ok(response);
    }

    /**
     * ajax 실시간 중복체크 하는 메소드
     * @return 통과
     */
    @GetMapping("/api/check-duplicate-nickname/{nickName}")
    public ResponseEntity<Map<String, Boolean>> checkNickName(@PathVariable String nickName) {
        boolean nickNameCheck = memberService.checkNickName(nickName);
        Map<String, Boolean> response = new HashMap<>();
        response.put("nickNameCheck", nickNameCheck);
        return ResponseEntity.ok(response);
    }

    /**
     * ajax 실시간 중복체크 하는 메소드
     * @return 통과
     */
    @GetMapping("/api/check-duplicate-email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@PathVariable String email) {
        boolean EmailCheck = memberService.checkEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("EmailCheck", EmailCheck);
        return ResponseEntity.ok(response);
    }

    /**
     * 쿠키에 userId가 저장되어 있으면 모델에 담고 로그인 페이지로 이동하는 메소드
     * @return 로그인 페이지
     */
	@GetMapping("/login")
	public String login(Model model, @CookieValue(name = "userId", required = false) String userId) {
        LoginForm loginForm = LoginForm.builder().build();
        model.addAttribute("loginForm", loginForm);
		model.addAttribute("userId", userId);
	    return "member/login";
	}

    /**
     * 로그인 정보로 로그인 처리하는 메소드
     * @return index 페이지
     */
	@PostMapping("/login") 
	public String login(@RequestParam(name = "saveId", required = false) boolean saveId, @Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "member/login";
        }

        MemberDTO loginMember = memberService.isMember(loginForm.getLoginId(), loginForm.getPasswd());

		if (saveId) {
			Cookie cookie = new Cookie("userId", loginForm.getLoginId());
			cookie.setMaxAge(30 * 24 * 60 * 60);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("userId", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		if (loginMember == null) {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                return "member/login";
		}

		HttpSession session =  request.getSession();
		session.setAttribute("loginMember", loginMember);
		return "redirect:/";
	}

    /**
     * 로그아웃 처리 메소드
     * @return index 페이지
     */
	@GetMapping("/logout")  
	public String logout(HttpServletRequest request) {
		HttpSession session =  request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}


    /**
     * 구글 로그인 후 DB에 회원 정보가 없으면 닉네임 생성 페이지로 이동하는 메소드
     * @return 닉네임 생성 페이지
     */
    @GetMapping("/nickname")
    public String nickname(Model model) {
        return "member/nickname";
    }

    /**
     * 닉네임 정보를 받아 회원 정보를 DB에 저장하는 메소드
     * @return index 페이지
     */
    @PostMapping("/nickname")
    public String nicknameFinish(@RequestParam String nickName, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        String userEmail = (String) session.getAttribute("userEmail");
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setNickName(nickName);
        if (memberService.checkNickName(memberDTO.getNickName())) {
            model.addAttribute("duplicateWarning", "중복된 닉네임으로는 회원가입이 불가능합니다.");
            return "member/nickname";
        }
        memberDTO.setPasswd("1111");
        memberDTO.setLoginId(userId);
        memberDTO.setEmail(userEmail);
        
        memberService.register(memberDTO);
        MemberDTO loginMember = memberService.isMember(memberDTO.getLoginId(), memberDTO.getPasswd());
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    /**
     * 구글 로그인 처리 메소드
     * @return index 페이지
     */
    @GetMapping("/googleLogin")
    public String gooleLogin(HttpSession session){
        MemberDTO member = (MemberDTO) session.getAttribute("Member");
        session.setAttribute("loginMember", member);
        return "redirect:/";
    }


}
