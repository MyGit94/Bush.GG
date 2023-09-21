package com.pinkward.bushgg.domain.article.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {
//    게시글 번호
    int articleId;
//    작성자 = 닉네임
    String writer;
//    게시판번호
    int boardId;
//    제목
    String subject;
//    내용
    @NotBlank
    String content;
//    가입날짜
    String regdate;
//    조회수
    int hitcount;
//    비밀번호
    String passwd;
    int groupNo;
    int levelNo;
    int orderNo;
    private List<ArticleDTO> replies;  // 대댓글 리스트


}
