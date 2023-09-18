package com.pinkward.bushgg.web.board.controller;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.article.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

	private final ArticleMapper articleMapper;

	private final int ELEMENT_SIZE = 8;

	private final int PAGE_SIZE = 5;
	
	@GetMapping
	public String article(Model model) {
		List<ArticleDTO> list = articleMapper.findAll();
		log.info("{}",list);
		model.addAttribute("list" , list);
		return "article/board";
	}
	
	@GetMapping("/register")
	public String register(Model model) {

		return "article/board-register";
	}
	
	@GetMapping("/detail")
	public String detail(Model model) {
		log.info("게시글 쓰기 화면 요청");
		return  "article/board-detail";
	}
}





