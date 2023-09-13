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


//	게시글 전체목록
	@GetMapping
	public String boardList(Model model) {
		log.info("게시판 목록");
		return "article/board";
	}

//	게시글 쓰기
	@GetMapping("/register")
	public String register(Model model) {
		log.info("게시글 쓰기");
		return  "article/board-register";
	}

//	게시글 디테일
	@GetMapping("/detail")
	public String detail(Model model) {
		log.info("게시글 정보보기");
		return  "article/board-detail";
	}

}





