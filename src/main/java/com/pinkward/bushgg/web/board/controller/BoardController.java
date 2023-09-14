package com.pinkward.bushgg.web.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
	
	@GetMapping
	public String article(Model model) {
		log.info("게시판 목록 요청");
		return "article/board";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		log.info("게시글 목록 요청");
		return "article/board-register";
	}
	
	@GetMapping("/detail")
	public String detail(Model model) {
		log.info("게시글 쓰기 화면 요청");
		return  "article/board-detail";
	}
}





