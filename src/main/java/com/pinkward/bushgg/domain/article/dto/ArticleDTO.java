package com.pinkward.bushgg.domain.article.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    String content;
    String regdate;
    int hitcount;
    String passwd;
    int groupNo;
    int levelNo;
    int orderNo;

}
