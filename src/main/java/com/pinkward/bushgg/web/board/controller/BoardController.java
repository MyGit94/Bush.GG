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
            @RequestParam(defaultValue = "1") int requestPage,
            @RequestParam(defaultValue = "0" , required = false) int status,
            @RequestParam(name = "searchSubject", required = false) String subject
    ) {
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        int selectPage = requestPage;
        int rowCount = articleService.countAll();
        PageParams pageParams = new PageParams(ELEMENT_SIZE, PAGE_SIZE, selectPage, rowCount);
        Pagination pagination = new Pagination(pageParams);
        List<ArticleDTO> list;
        if (requestPage != 0) {
//            페이징
            if (status == 0) {
                list = articleService.findByAll2(pageParams);
                model.addAttribute("list", list);
                log.info("페이징이 포함된 list 실행됨 {} ", list);
//                추천글보기
            } else if (status == 1) {
                list = articleMapper.findAllByHitcount();
                model.addAttribute("list", list);
                log.info("추천글 list 활성화됨 {} ", list);
//                검색
            } else if (status == 2) {
                list = articleMapper.findSubject(subject);
                model.addAttribute("list", list);
                log.info("입력하신 키워드에 해당하는 제목의 리스트를 불러왔습니다 {}", list);
            }

        }

            model.addAttribute("pagination", pagination);
            model.addAttribute("requestPage", requestPage);

            return "article/board";
        }


//   게시판메인 :: 인기글 버튼 눌렀을시
        @PostMapping("")
        public String Article2 (RedirectAttributes redirectAttributes){
            redirectAttributes.addAttribute("status", 1);
            return "redirect:/board";
        }

//    게시판메인 : 검색창 사용시
        @PostMapping("/search")
        public String Article3 (@RequestParam("searchSubject") String subject , RedirectAttributes redirectAttributes){
            redirectAttributes.addFlashAttribute("status", 2);
            redirectAttributes.addFlashAttribute("subject", subject);
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
            MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
            ArticleDTO articleDTO = articleService.detail(articleId);
            articleMapper.updateHitcount(articleDTO);
            int groupNo = articleDTO.getGroupNo();
            List<ArticleDTO> comments = articleService.read(groupNo);
            log.info("{}", articleDTO);

//            댓글 읽기
            model.addAttribute("comments", comments);
//            게시글 정보
            model.addAttribute("articleDTO", articleDTO);
            return "article/board-detail";
        }


//    댓글쓰기
        @PostMapping("/detail")
        public String comment (
                RedirectAttributes redirectAttributes,
                Model model,
                HttpSession session,
                @Valid @RequestParam("comment") String comment
    ){
            MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
            ArticleDTO articleDTO = (ArticleDTO) model.getAttribute("comments");

            log.info("불러들여온 회원 정보 {}" , member);
            if(member != null){
                articleDTO.setWriter(member.getNickName());
                articleDTO.setContent(comment);
                articleDTO.setPasswd(member.getPasswd());
                articleDTO.setGroupNo(articleDTO.getGroupNo());
            } else {
                log.info("로그인이 되지 않아 댓글쓰기를 실패하였습니다");
            }

           articleMapper.createComment(articleDTO);
            redirectAttributes.addFlashAttribute("writeComment" , articleDTO);
            log.info("정상적으로 댓글이 등록되었습니다 {} " , articleDTO);

            return "redirect:/board/detail";
        }

    }






