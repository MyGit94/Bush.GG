package com.pinkward.bushgg.web.member.controller;

import com.pinkward.bushgg.domain.member.dto.LoginForm;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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
            RedirectAttributes redirectAttributes,HttpServletRequest request, Model model) {

        // 데이터 검증 실패 시 회원가입 화면으로 Forward
        if (bindingResult.hasErrors()) {
            return "member/register";
        }
     // 중복 체크

        if (
        	memberService.checkLoginId(memberDTO.getLoginId())) {
            model.addAttribute("duplicateWarning", "중복된 값으로는 회원가입이 불가능합니다.");
            log.info("중복된 아이디{}",memberDTO.getLoginId());

            return "member/register";
        }

        if (memberService.checkNickName(memberDTO.getNickName())) {
            model.addAttribute("duplicateWarning", "중복된 닉네임으로는 회원가입이 불가능합니다.");
            log.info("중복된 닉네임: {}", memberDTO.getNickName());
            return "member/register";
        }

        if (memberService.checkEmail(memberDTO.getEmail())) {
            model.addAttribute("duplicateWarning", "중복된 이메일 주소로는 회원가입이 불가능합니다.");
            log.info("중복된 이메일: {}", memberDTO.getEmail());
            return "member/register";
        }



        // 데이터 검증 성공 시 비밀번호 확인
        if (!memberDTO.getPasswd().equals(memberDTO.getCheckpasswd())) {
            model.addAttribute("checkPasswordError", "비밀번호가 일치하지 않습니다.");
            return "member/register";
        }

        // 데이터 검증 성공 시 DB 저장 후 성공 메시지와 함께 홈페이지로 리다이렉트
        memberService.register(memberDTO);

        log.info("DB 저장 완료 : {}", memberDTO.getLoginId());

        // 리다이렉트 시 메시지 전달
        redirectAttributes.addFlashAttribute("successMessage", "회원 가입이 성공적으로 완료되었습니다.");
        return "redirect:/member/login";
    }

	// 회원가입 실시간 중복체크
    @GetMapping("/api/check-duplicate-loginid/{loginId}")
    public ResponseEntity<Map<String, Boolean>> checkLoginId(@PathVariable String loginId) {
        boolean loginIdCheck = memberService.checkLoginId(loginId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("loginIdCheck", loginIdCheck);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/check-duplicate-nickname/{nickName}")
    public ResponseEntity<Map<String, Boolean>> checkNickName(@PathVariable String nickName) {
        boolean nickNameCheck = memberService.checkNickName(nickName);
        Map<String, Boolean> response = new HashMap<>();
        response.put("nickNameCheck", nickNameCheck);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/check-duplicate-email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@PathVariable String email) {
        boolean EmailCheck = memberService.checkEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("EmailCheck", EmailCheck);
        return ResponseEntity.ok(response);
    }

    // 로그인
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

	// 로그아웃
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
