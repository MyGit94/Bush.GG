package com.pinkward.bushgg.domain.article.service;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.article.mapper.ArticleMapper;
import com.pinkward.bushgg.domain.common.web.PageParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    public final ArticleMapper articleMapper;

    @Override
    @Transactional
    public void write(ArticleDTO articleDTO) {
        articleMapper.create(articleDTO);
    }

    @Override
    public List<ArticleDTO> findByAll() {
        return articleMapper.findAll();
    }

    @Override
    @Transactional
    public void createComment(ArticleDTO articleDTO) {
        articleMapper.createComment(articleDTO);
    }

    @Override
    @Transactional
    public void commentByComment(ArticleDTO articleDTO) {
        articleMapper.commentByComment(articleDTO);
    }

    @Override
    @Transactional
    public void removeComment(int articleId, String passwd) {
        articleMapper.removeComment(articleId, passwd);
    }

    @Override
    @Transactional
    public void updateComment(ArticleDTO articleDTO) {
        articleMapper.update(articleDTO);
    }

    @Override
    public ArticleDTO detail(int articleId) {
        return articleMapper.detail(articleId);
    }

    public List<ArticleDTO> read(int groupNo) {
        return articleMapper.readComment(groupNo);
    }

    public int countAll() {
        return articleMapper.getCountAll();
    }

    @Override
    public List<ArticleDTO> findByAll2(PageParams pageParams) {
        return articleMapper.findByAll(pageParams);
    }


}
