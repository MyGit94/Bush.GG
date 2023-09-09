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
	public String boardList(Model model) {
		log.info("게시판 목록 요청");
		return "board/list";
	}
	
	@GetMapping("/article")
	public String articleList(Model model) {
		log.info("게시글 목록 요청");
		return "board/article/list";
	}
	
	@GetMapping("/article/register")
	public String register(Model model) {
		log.info("게시글 쓰기 화면 요청");
		return  "board/article/register";
	}
}





