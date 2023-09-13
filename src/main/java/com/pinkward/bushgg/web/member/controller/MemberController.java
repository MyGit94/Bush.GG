package com.pinkward.bushgg.web.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pinkward.bushgg.domain.member.dto.LoginForm;
import com.pinkward.bushgg.domain.member.dto.Member;
import com.pinkward.bushgg.domain.member.service.MemberService;
import com.pinkward.bushgg.web.member.exception.MemberException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 관련 웹 요청을 처리하는 세부 컨트롤러 구현 클래스
 *
 * @author  에너자이조 김기정
 * @since   2023. 9. 4.
 * @version 1.0
 * @see     com.pinkward.bushgg.domain.member.service.MemberService
 * @see     com.pinkward.bushgg.domain.member.service.MemberServiceImpl
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
	
	/** 회원 관련 비즈니스 메소드 제공 */
	private final MemberService memberService;

	/**
	 * 사용자 회원가입 화면 요청 처리 
	 * @param model  뷰에서 필요한 데이터 저장
	 * @return  뷰의 논리적 이름
	 */
	@GetMapping("/register")
	public String registerForm(Model model) {
		Member member = Member.builder().build();
		model.addAttribute("member", member);
		return "member/register";
	}
	
	/**
	 * 사용자 회원가입 요청 처리
	 * 회원 데이터 검증 시 Bean Validation 사용
	 * @param @validated  Global Validator 사용 설정 
	 * @param member  사용자 입력 정보
	 * @param bindingResult  검증 오류 메시지 설정
	 * @param redirectAttributes  회원 가입 요청 정상 처리 후 리다이렉트 정보 설정 
	 * @param model  뷰에서 필요한 데이터 저장
	 * @return  뷰의 논리적 이름
	 */
	@PostMapping("/register")
	public String register(@Validated @ModelAttribute Member member, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		// 데이터 검증 실패 시 회원가입 화면으로 포워드
		if (bindingResult.hasErrors()) {
			// model에 bindingResult 자동 저장
			return "member/register";
		}

		// 데이터 검증 성공 시 회원 등록 처리 후 회원 상세 페이지로 리다이렉트
		memberService.register(member);
		// 일반 회원 상세 조회 페이지와 구별하기 status 속성 추가
		redirectAttributes.addFlashAttribute("status", true);
		return "redirect:/member/" + member.getId();
	}
	
	/** 회원 상세 조회 요청 처리 */
	@GetMapping("/{id}")
	public String read(@PathVariable("id") String id, Model model) {
		Member member = memberService.getMember(id);
		model.addAttribute("member", member);
		return "member/read";
	}
	
	/** 회원 목록 조회 요청 처리 */
	@GetMapping
	public String list(Model model) {
		List<Member> list = memberService.getMemberList();
		model.addAttribute("list", list);
		return "member/list";
	}
	
	/** 회원 정보 수정 화면 요청 처리 */
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") String id, Model model) {
		Member member = memberService.getMember(id);
		model.addAttribute("member", member);
		return "member/edit";
	}
	
	/** 회원 정보 수정 요청 처리 */
	@PostMapping("/edit")
	public String edit(@ModelAttribute Member member, Model model) {
		memberService.editMember(member);
		return "redirect:/member/" + member.getId();
	}
	
	/** 회원 로그인 화면 요청 처리 */
	@GetMapping("/login")
	public String loginForm(Model model) {
		LoginForm loginForm = LoginForm.builder().build();
		model.addAttribute("loginForm", loginForm);
		return "member/login";
	}
	
	/** 회원 로그인 요청 처리 */
	@PostMapping("/login") 
	public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
		
		// 데이터 검증 실패 시 로그인 화면으로 포워드
		if (bindingResult.hasErrors()) {
			return "member/login";
		}
		
		// 데이터 검증 정상 처리 시
		Member loginMember = memberService.isMember(loginForm.getLoginId(), loginForm.getPasswd());
		
		// 회원이 아닌 경우
		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "member/login";
		}
		
		// 회원인 경우 세션 생성 및 로그인 아이디 설정
		HttpSession session =  request.getSession();
		session.setAttribute("loginMember", loginMember);
		
		// 로그인 처리 후 사용자가 원래 요청하려던 URL로 리다이렉트 처리
		String redirectURI = (String)session.getAttribute("redirectURI");
		log.warn(redirectURI);
		String uri = redirectURI == null ? "/" : redirectURI;
		return "redirect:" + uri;
	}
	
	/** 회원 로그아웃 요청 처리 */
	@GetMapping("/logout")  
	public String logout(HttpServletRequest request) {
		// 세션이 있으면 기존 세션 반환, 없으면 생성하지 않고 null 반환
		HttpSession session =  request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
	
	/** 아이디 중복 여부 요청 처리 */
	@GetMapping("/valid/{id}")
	@ResponseBody
	public boolean isMemberId(@PathVariable("id") String id) {
		Member member = memberService.getMember(id);
		return member != null ? true : false;
	}
	
	/** API 서비스 시 예외 처리를 위한 테스트 */
	@GetMapping("/rest/{id}")
	@ResponseBody
	public Member read(@PathVariable String id) {
		// 테스트를 위한 코드
		if(id.equals("bangry1")) {
			throw new IllegalArgumentException("잘못된 값을 입력하였습니다.");
		}
		
		if(id.equals("bangry2")) {
			throw new RuntimeException("서버 장애가 발생하였습니다.");
		}
		
		if(id.equals("bangry3")) {
			throw new MemberException("인증되지 않은 사용자입니다.");
		}
		
		return new Member(id, "1111", "looney", "looney@gmail.com", "2023/09/02");
	}
	
}
