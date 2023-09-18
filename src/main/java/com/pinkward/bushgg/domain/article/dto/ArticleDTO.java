package com.pinkward.bushgg.domain.article.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {
    int articleId;
    String writer;
    int boardId;
    String adminNo;
    String subject;
    String content;
    String regdate;
    int hitcount;
    String passwd;
    int groupNo;
    int levelNo;
    int orderNo;
}
