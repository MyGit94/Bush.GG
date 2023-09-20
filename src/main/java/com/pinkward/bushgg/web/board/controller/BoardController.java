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
    @GetMapping
    public String article(
            Model model,
            HttpSession session,
            @RequestParam(defaultValue = "1") int requestPage
    ) {


        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        int selectPage = requestPage;
        int rowCount = articleService.countAll();
        PageParams pageParams = new PageParams(ELEMENT_SIZE, PAGE_SIZE, selectPage, rowCount);
        Pagination pagination = new Pagination(pageParams);



        if (requestPage != 0) {
            List<ArticleDTO> list;
           int status = (int) session.getAttribute("status");
           log.info("if 문에서 실행된 status 값 : {}" , status);
            switch (status) {
                case 0: // 페이징
                    list = articleService.findByAll2(pageParams);
                    model.addAttribute("list", list);
                    log.info("페이징이 포함된 list 실행됨 {} ", list);
                    break;
                case 1: // 추천글보기
                    list = articleMapper.findAllByHitcount();
                    model.addAttribute("list", list);
                    log.info("추천글 list 활성화됨 {} ", list);
                    break;
                case 2: // 검색
                    String subject = (String)session.getAttribute("subjectContent");
                    log.info("subject 값 : {}" , subject);
                    model.addAttribute("searchSubject" , subject);
                    list = articleMapper.findSubject(subject);
                    model.addAttribute("list", list);
                    log.info("입력하신 키워드에 해당하는 제목의 리스트를 불러왔습니다 {}", list);
                    break;
                default:
                    break;
            }
        }


        log.info("pagenation : {}" , pagination);
        model.addAttribute("pageParams" , pageParams);
        model.addAttribute("pagination", pagination);
        model.addAttribute("requestPage", requestPage);

        return "article/board";
    }


    //   게시판메인 :: 인기글 버튼 눌렀을시
    @PostMapping("")
    public String Article2 (HttpSession session ,RedirectAttributes redirectAttributes){
        log.info("인기글 post 실행됨");
        // status 값을 변경
        session.setAttribute("status", 1);
        return "redirect:/board";
    }

    //    게시판메인 : 검색창 사용시
    @PostMapping("/search")
    public String Article3 (HttpSession session,@RequestParam("searchSubject") String subject , RedirectAttributes redirectAttributes){

        log.info("search post 실행됨");
        // status 값을 변경
        session.setAttribute("status", 2);
        session.setAttribute("subjectContent" , subject);
        log.info("subjectContent {} " , subject);
        return "redirect:/board";
    }

    //    글쓰기 페이지 입장
    @GetMapping("/register")
    public String register (Model model , HttpSession session){
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        log.info("글쓰기 페이지 실행됨");
        ArticleDTO articleDTO = ArticleDTO.builder().build();
        model.addAttribute("articleDTO", articleDTO);
        log.info("articleDTO : {}", articleDTO);

        return "article/board-register";
    }

    //    글쓰기 페이지 서버 작업
    @PostMapping("/register")
    public String register2 (RedirectAttributes redirectAttributes,
                             @RequestParam("subject") String subject,
                             @RequestParam("content") String content,
                             @ModelAttribute("articleDTO") ArticleDTO articleDTO ,
                             @RequestParam("category") int category){

        log.info("글쓰기 페이지 서버작업 실행됨");
        articleDTO.setBoardId(category);
        articleDTO.setSubject(subject);
        articleDTO.setContent(content);
        redirectAttributes.addFlashAttribute("registeredArticle", articleDTO);
        articleMapper.create(articleDTO);
        log.info("db에 보낸 articleDTO 정보 : {}", articleDTO);
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
        log.info("읽어온 게시글 상세보기 정보 : {}", articleDTO);

        int countComments  = articleMapper.cellComments(groupNo);
        log.info("댓글수 계산 결과 입니다 : {} " ,countComments);

//        댓글수 계산
        model.addAttribute("countComments" , countComments);
//            댓글 읽기
        model.addAttribute("comments", comments);
        log.info("comment 정보 : {}" , comments);
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
        log.info("(detail) 읽어온 회원 정보 : {}" , member);
//        게시글 디테일 정보 받기
        ArticleDTO articleDTO = (ArticleDTO) session.getAttribute("articleDTO");
        log.info("post요청 :: 받아온 articleDTO 정보 : {}" , articleDTO);
        if(member != null){
            articleDTO.setHitcount(articleDTO.getHitcount());
            articleDTO.setSubject(articleDTO.getSubject());
            articleDTO.setWriter(member.getNickName());
            articleDTO.setContent(comment);
            articleDTO.setPasswd(member.getPasswd());
            articleDTO.setGroupNo(articleDTO.getGroupNo());
            articleDTO.setBoardId(articleDTO.getBoardId());

        } else {
            log.info("로그인이 되지 않아 댓글쓰기를 실패하였습니다");
        }

        articleMapper.createComment(articleDTO);
        redirectAttributes.addFlashAttribute("writeComment" , articleDTO);


        return "redirect:/board/detail/"+articleDTO.getArticleId();
    }

}