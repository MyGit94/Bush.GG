package com.pinkward.bushgg.web.board.controller;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.article.service.ArticleService;
import com.pinkward.bushgg.domain.common.web.PageParams;
import com.pinkward.bushgg.domain.common.web.Pagination;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Article 페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Controller
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final ArticleService articleService;
    private final int ELEMENT_SIZE = 8;
    private final int PAGE_SIZE = 5;

    /**
     * Article 페이지 요청시 실행되는 메소드
     * @return 게시글 페이지
     */
    @GetMapping
    public String article(Model model, HttpSession session, @RequestParam(defaultValue = "1") int requestPage) {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        int selectPage = requestPage;
        int rowCount = articleService.countAll();
        PageParams pageParams = new PageParams(ELEMENT_SIZE, PAGE_SIZE, selectPage, rowCount);
        Pagination pagination = new Pagination(pageParams);

        if (requestPage != 0) {
            List<ArticleDTO> list;
            int status = (int) session.getAttribute("status");
            switch (status) {
                case 0:
                    list = articleService.findByAll2(pageParams);
                    model.addAttribute("list", list);
                    break;
                case 1:
                    list = articleService.findAllByHitcount();
                    model.addAttribute("list", list);
                    break;
                case 2:
                    String subject = (String)session.getAttribute("subjectContent");
                    model.addAttribute("searchSubject" , subject);
                    list = articleService.findSubject(subject);
                    model.addAttribute("list", list);
                    break;
                default:
                    break;
            }
        }
        session.setAttribute("loginMember", member);
        model.addAttribute("pageParams" , pageParams);
        model.addAttribute("pagination", pagination);
        model.addAttribute("requestPage", requestPage);

        return "article/board";
    }

    /**
     * 인기글 버튼 클릭시 status 값을 바꾸고 redirect 하는 메소드
     * @return 인기글  페이지
     */
    @PostMapping("")
    public String Article2 (HttpSession session ,RedirectAttributes redirectAttributes){
        // status 값을 변경
        session.setAttribute("status", 1);
        return "redirect:/board";
    }

    /**
     * 게시글을 검색하면 status 값을 바꾸고 redirect 하는 메소드
     * @return 검색한 게시글 페이지
     */
    @PostMapping("/search")
    public String Article3 (HttpSession session,@RequestParam("searchSubject") String subject , RedirectAttributes redirectAttributes){
        session.setAttribute("status", 2);
        session.setAttribute("subjectContent" , subject);
        return "redirect:/board";
    }

    /**
     * 게시글 작성하는 페이지로 이동하는 메소드
     * @return 게시글 작성 페이지
     */
    @GetMapping("/register")
    public String register (Model model , HttpSession session){
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        ArticleDTO articleDTO = ArticleDTO.builder().build();
        model.addAttribute("articleDTO", articleDTO);

        return "article/board-register";
    }

    /**
     * 작성한 게시글을 DB에 저장하고 전체 게시글 페이지로 이동하는 메소드
     * @return 전체 게시글 페이지
     */
    @PostMapping("/register")
    public @ResponseBody String register2 (RedirectAttributes redirectAttributes,
                             @RequestParam("subject") String subject,
                             @RequestParam("content") String content,
                             @ModelAttribute("articleDTO") ArticleDTO articleDTO ,
                             @RequestParam("category") int category ,
                             HttpSession session){

        MemberDTO memberDTO=(MemberDTO) session.getAttribute("loginMember");

        if (!"admin".equals(memberDTO.getRole())) {
            if (category == 30) {
                return "error";
            }
        }

        articleDTO.setWriter(memberDTO.getNickName());
        articleDTO.setPasswd(memberDTO.getPasswd());
        articleDTO.setBoardId(category);
        articleDTO.setSubject(subject);
        articleDTO.setContent(content);

        redirectAttributes.addFlashAttribute("registeredArticle", articleDTO);
        articleService.write(articleDTO);

        return "success";
    }

    /**
     * 게시글 클릭 시 해당 게시글 상세 페이지로 이동하는 메소드
     * @return 게시글 상세 페이지
     */
    @GetMapping("/detail/{articleId}")
    public String detail ( @PathVariable int articleId, Model model , HttpSession session){
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        ArticleDTO articleDTO = articleService.detail(articleId);
        articleService.updateHitcount(articleDTO);

        int groupNo = articleDTO.getGroupNo();
        List<ArticleDTO> comments = articleService.read(groupNo);

        int countComments  = articleService.cellComments(groupNo);

        model.addAttribute("countComments" , countComments);
        model.addAttribute("comments", comments);
        model.addAttribute("articleDTO", articleDTO);
        session.setAttribute("articleDTO", articleDTO);
        return "article/board-detail";
    }


    /**
     * 해당 게시글에 댓글을 등록하고 해당 게시글로 redirect 하는 메소드
     * @return 해당 게시글 상세 페이지
     */
    @PostMapping("/detail/comment")
    public String comment (
            RedirectAttributes redirectAttributes,
            HttpSession session,
            @Valid @RequestParam("comment") String comment
    ){
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        ArticleDTO articleDTO = (ArticleDTO) session.getAttribute("articleDTO");
        if(member != null){
            articleDTO.setSubject(articleDTO.getSubject());
            articleDTO.setWriter(member.getNickName());
            articleDTO.setContent(comment);
            articleDTO.setPasswd(member.getPasswd());
            articleDTO.setGroupNo(articleDTO.getGroupNo());
            articleDTO.setBoardId(articleDTO.getBoardId());
        }

        articleService.createComment(articleDTO);
        redirectAttributes.addFlashAttribute("writeComment" , articleDTO);
        return "redirect:/board/detail/"+articleDTO.getArticleId();
    }

    /**
     * 사이드 바에서 커뮤니티 클릭 시 status를 변경하고 redirect 하는 메소드
     * @return 전체 게시글 페이지
     */
    @GetMapping("/change-session-status")
    public String changeSessionStatus(HttpSession session) {
        session.setAttribute("status", 0);
        return "redirect:/board";
    }
}