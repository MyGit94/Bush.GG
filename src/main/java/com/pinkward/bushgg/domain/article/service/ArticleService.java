package com.pinkward.bushgg.domain.article.service;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.common.web.PageParams;

import java.util.List;

/**
 * Article 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface ArticleService {
    public void write(ArticleDTO articleDTO);

    public void createComment(ArticleDTO articleDTO);

    public void updateHitcount(ArticleDTO articleDTO);

    public int cellComments(int groupNo);

    public List<ArticleDTO> findSubject(String subject);

    public List<ArticleDTO> findAllByHitcount();

    public ArticleDTO detail(int articleId);

    public List<ArticleDTO> read (int groupNo);

    public int countAll ();

    public List<ArticleDTO> findByAll2(PageParams pageParams);

}
