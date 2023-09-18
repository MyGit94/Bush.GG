package com.pinkward.bushgg.domain.article.service;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.common.web.PageParams;

import java.util.List;

public interface ArticleService {
    public void write(ArticleDTO articleDTO);
    public List<ArticleDTO> findByAll();
    public void createComment(ArticleDTO articleDTO);
    public void commentByComment(ArticleDTO articleDTO);
    public void removeComment(int articleId, String passwd);
    public void updateComment(ArticleDTO articleDTO);
    public ArticleDTO detail(int articleId);
    public List<ArticleDTO> read (int groupNo);
    public int countAll ();
    public List<ArticleDTO> findByAll2(PageParams pageParams);
}
