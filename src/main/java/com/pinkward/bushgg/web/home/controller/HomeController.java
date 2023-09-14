package com.pinkward.bushgg.web.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 홈페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 *
 * @author  에너자이조 김기정
 * @since   2023. 9. 4.
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	public String home(Model model) {
		return "index";
	}


}
