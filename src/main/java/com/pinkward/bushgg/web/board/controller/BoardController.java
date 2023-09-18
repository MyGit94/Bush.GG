package com.pinkward.bushgg.web.board.controller;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.article.mapper.ArticleMapper;
import com.pinkward.bushgg.domain.article.service.ArticleService;
import com.pinkward.bushgg.domain.common.web.PageParams;
import com.pinkward.bushgg.domain.common.web.Pagination;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final ArticleMapper articleMapper;
    private  final ArticleService articleService;
    private final int ELEMENT_SIZE = 4;
    private final int PAGE_SIZE = 5;


//    게시판 입장
    @GetMapping
    public String article(
            Model model ,
            HttpSession session,
            @RequestParam(defaultValue = "1") int requestPage
    ) {
        int selectPage = requestPage;
        int rowCount = articleService.countAll();
        PageParams pageParams = new PageParams(ELEMENT_SIZE, PAGE_SIZE, selectPage, rowCount);
        Pagination pagination = new Pagination(pageParams);
        List<ArticleDTO> list;
        if(requestPage !=0){
            list = articleService.findByAll2(pageParams);
        } else {
            list = articleService.findByAll();
        }

        session.setAttribute("list", list);
        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);
        model.addAttribute("requestPage", requestPage);

        return "article/board";
    }

//    글쓰기 페이지 입장
    @GetMapping("/register")
    public String register(Model model) {
        log.info("글쓰기 페이지 실행됨");
        ArticleDTO articleDTO = ArticleDTO.builder().build();
        model.addAttribute("articleDTO", articleDTO);
        log.info("articleDTO : {}", articleDTO);

        return "article/board-register";
    }

//    글쓰기 페이지 서버 작업
    @PostMapping("/register")
    public String register2(RedirectAttributes redirectAttributes,
                            @RequestParam("subject") String subject,
                            @RequestParam("content") String content,
                            @ModelAttribute("articleDTO") ArticleDTO articleDTO ,
                            @RequestParam("category") int category) {

        log.info("글쓰기 페이지 서버작업 실행됨");
        articleDTO.setBoardId(category);
        articleDTO.setSubject(subject);
        articleDTO.setContent(content);
        redirectAttributes.addFlashAttribute("registeredArticle", articleDTO);
        articleMapper.create(articleDTO);
        log.info("db에 보낸 articleDTO 정보 : {}" , articleDTO);
        return "redirect:/board";
    }



//    게시글 상세보기

    @GetMapping("/detail/{articleId}")
    public String detail(@PathVariable int articleId, Model model){
        ArticleDTO articleDTO = articleService.detail(articleId);
        int groupNo = articleDTO.getGroupNo();
        List<ArticleDTO> comments = articleService.read(groupNo);
        log.info("{}",articleDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("articleDTO", articleDTO);
        return "article/board-detail";
    }


}





