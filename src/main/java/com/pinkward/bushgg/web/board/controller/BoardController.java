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

import java.lang.reflect.Member;
import java.util.List;

@Controller
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final ArticleMapper articleMapper;
    private final ArticleService articleService;
    private final int ELEMENT_SIZE = 8;
    private final int PAGE_SIZE = 5;


    //    게시판 입장
    @GetMapping("")
    public String article(Model model, HttpSession session, @RequestParam(defaultValue = "1") int requestPage) {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        int selectPage = requestPage;
        int rowCount = articleService.countAll();
        PageParams pageParams = new PageParams(ELEMENT_SIZE, PAGE_SIZE, selectPage, rowCount);
        Pagination pagination = new Pagination(pageParams);

        if (requestPage != 0) {
            List<ArticleDTO> list;
            int status = (int) session.getAttribute("status");
//           log.info("if 문에서 실행된 status 값 : {}" , status);
            switch (status) {
                case 0: // 페이징
                    list = articleService.findByAll2(pageParams);
                    model.addAttribute("list", list);
                    break;
                case 1: // 추천글보기
                    list = articleMapper.findAllByHitcount();
                    model.addAttribute("list", list);
                    break;
                case 2: // 검색
                    String subject = (String)session.getAttribute("subjectContent");
                    model.addAttribute("searchSubject" , subject);
                    list = articleMapper.findSubject(subject);
                    model.addAttribute("list", list);
                    break;
                default:
                    break;
            }
        }

        model.addAttribute("pageParams" , pageParams);
        model.addAttribute("pagination", pagination);
        model.addAttribute("requestPage", requestPage);

        return "article/board";
    }


    //   게시판메인 :: 인기글 버튼 눌렀을시
    @PostMapping("")
    public String Article2 (HttpSession session ,RedirectAttributes redirectAttributes){
        // status 값을 변경
        session.setAttribute("status", 1);
        return "redirect:/board";
    }

    //    게시판메인 : 검색창 사용시
    @PostMapping("/search")
    public String Article3 (HttpSession session,@RequestParam("searchSubject") String subject , RedirectAttributes redirectAttributes){
        // status 값을 변경
        session.setAttribute("status", 2);
        session.setAttribute("subjectContent" , subject);
        return "redirect:/board";
    }

    //    글쓰기 페이지 입장
    @GetMapping("/register")
    public String register (Model model , HttpSession session){
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        ArticleDTO articleDTO = ArticleDTO.builder().build();
        model.addAttribute("articleDTO", articleDTO);

        return "article/board-register";
    }

    //    글쓰기 페이지 서버 작업
    @PostMapping("/register")
    public String register2 (RedirectAttributes redirectAttributes,
                             @RequestParam("subject") String subject,
                             @RequestParam("content") String content,
                             @ModelAttribute("articleDTO") ArticleDTO articleDTO ,
                             @RequestParam("category") int category,
                             HttpSession session){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        articleDTO.setWriter(memberDTO.getNickName());
        articleDTO.setPasswd(memberDTO.getPasswd());
        articleDTO.setBoardId(category);
        articleDTO.setSubject(subject);
        articleDTO.setContent(content);
        redirectAttributes.addFlashAttribute("registeredArticle", articleDTO);
        articleMapper.create(articleDTO);
        return "redirect:/board";
    }


//    게시글 상세보기

    @GetMapping("/detail/{articleId}")
    public String detail ( @PathVariable int articleId, Model model , HttpSession session){
//       회원 정보 불러오기
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
//       게시글 상세정보
        ArticleDTO articleDTO = articleService.detail(articleId);
//        조회수 증가
        articleMapper.updateHitcount(articleDTO);

        int groupNo = articleDTO.getGroupNo();
        List<ArticleDTO> comments = articleService.read(groupNo);

        int countComments  = articleMapper.cellComments(groupNo);

//        댓글수 계산
        model.addAttribute("countComments" , countComments);
//            댓글 읽기
        model.addAttribute("comments", comments);
//            게시글 정보
        model.addAttribute("articleDTO", articleDTO);
//        게시글 정보 세션 저장
        session.setAttribute("articleDTO", articleDTO);
        return "article/board-detail";
    }


    //    댓글쓰기
    @PostMapping("/detail/comment")
    public String comment (
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session,
            @Valid @RequestParam("comment") String comment
    ){
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
//        게시글 디테일 정보 받기
        ArticleDTO articleDTO = (ArticleDTO) session.getAttribute("articleDTO");
        if(member != null){
            articleDTO.setHitcount(articleDTO.getHitcount());
            articleDTO.setSubject(articleDTO.getSubject());
            articleDTO.setWriter(member.getNickName());
            articleDTO.setContent(comment);
            articleDTO.setPasswd(member.getPasswd());
            articleDTO.setGroupNo(articleDTO.getGroupNo());
            articleDTO.setBoardId(articleDTO.getBoardId());

        } else {
        }

        articleMapper.createComment(articleDTO);
        redirectAttributes.addFlashAttribute("writeComment" , articleDTO);


        return "redirect:/board/detail/"+articleDTO.getArticleId();
    }

}