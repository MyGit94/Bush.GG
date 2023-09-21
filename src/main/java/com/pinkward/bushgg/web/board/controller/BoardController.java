package com.pinkward.bushgg.web.board.controller;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.article.mapper.ArticleMapper;
import com.pinkward.bushgg.domain.article.service.ArticleService;
import com.pinkward.bushgg.domain.common.web.PageParams;
import com.pinkward.bushgg.domain.common.web.Pagination;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Board 페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Controller
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final ArticleMapper articleMapper;
    private final ArticleService articleService;
    private final int ELEMENT_SIZE = 8;
    private final int PAGE_SIZE = 5;

    @GetMapping
    public String article(
            Model model,
            HttpSession session,
            @RequestParam(defaultValue = "1") int requestPage,
            @RequestParam(defaultValue = "0" , required = false) int status,
            @RequestParam(name = "searchSubject", required = false) String subject
    ) {
        int selectPage = requestPage;
        int rowCount = articleService.countAll();
        PageParams pageParams = new PageParams(ELEMENT_SIZE, PAGE_SIZE, selectPage, rowCount);
        Pagination pagination = new Pagination(pageParams);
        List<ArticleDTO> list;
        if (requestPage != 0) {
            if (status == 0) {
                list = articleService.findByAll2(pageParams);
                model.addAttribute("list", list);
            } else if (status == 1) {
                list = articleMapper.findAllByHitcount();
                model.addAttribute("list", list);
            } else if (status == 2) {
                list = articleMapper.findSubject(subject);
                model.addAttribute("list", list);
            }
        }
            model.addAttribute("pagination", pagination);
            model.addAttribute("requestPage", requestPage);
            return "article/board";
        }

        @PostMapping("")
        public String Article2 (RedirectAttributes redirectAttributes){
            redirectAttributes.addAttribute("status", 1);
            return "redirect:/board";
        }

        @PostMapping("/search")
        public String Article3 (@RequestParam("searchSubject") String subject , RedirectAttributes redirectAttributes){
            redirectAttributes.addFlashAttribute("status", 2);
            redirectAttributes.addFlashAttribute("subject", subject);
            return "redirect:/board";
        }

        @GetMapping("/register")
        public String register (Model model){
            ArticleDTO articleDTO = ArticleDTO.builder().build();

            model.addAttribute("articleDTO", articleDTO);
            return "article/board-register";
        }

        @PostMapping("/register")
        public String register2 (RedirectAttributes redirectAttributes,
                @RequestParam("subject") String subject,
                @RequestParam("content") String content,
                @ModelAttribute("articleDTO") ArticleDTO articleDTO ,
        @RequestParam("category") int category){

            articleDTO.setBoardId(category);
            articleDTO.setSubject(subject);
            articleDTO.setContent(content);
            redirectAttributes.addFlashAttribute("registeredArticle", articleDTO);
            articleMapper.create(articleDTO);
            return "redirect:/board";
        }

        @GetMapping("/detail/{articleId}")
        public String detail ( @PathVariable int articleId, Model model){
            ArticleDTO articleDTO = articleService.detail(articleId);
            articleMapper.updateHitcount(articleDTO);
            int groupNo = articleDTO.getGroupNo();
            List<ArticleDTO> comments = articleService.read(groupNo);

            model.addAttribute("comments", comments);
            model.addAttribute("articleDTO", articleDTO);
            return "article/board-detail";
        }

        @PostMapping("/detail")
        public String comment (
                RedirectAttributes redirectAttributes,
                Model model,
                HttpSession session,
                @Valid @RequestParam("comment") String comment
    ){
            MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
            ArticleDTO articleDTO = (ArticleDTO) model.getAttribute("comments");

            articleDTO.setWriter(member.getLoginId());
            articleDTO.setContent(comment);
            articleDTO.setPasswd(member.getPasswd());
            articleDTO.setGroupNo(articleDTO.getGroupNo());
            return "redirect:/board/detail";
        }

    }