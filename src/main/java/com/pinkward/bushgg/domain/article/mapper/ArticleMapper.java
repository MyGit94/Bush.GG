package com.pinkward.bushgg.domain.article.mapper;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.common.web.PageParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Article 관련 SQL Mapper
 */
@Mapper
public interface ArticleMapper {

    /** 신규 게시글 등록 */
    void create(ArticleDTO articleDTO);

    /** 전체 게시글 목록 반환 */
    List<ArticleDTO> findAll();

    /** 전체 게시글 조회수 반환 */
    List<ArticleDTO> findAllByHitcount();

    /** 페이징 계산에 필요한 게시글 전체 갯수 반환 */
    int getCountAll();

    /** 요청 페이지, 페이지당 보여지는 목록 갯수에 따른 목록 반환 */
    public List<ArticleDTO> findByAll(PageParams pageParams);

    /** 댓글 등록 */
    void createComment(ArticleDTO articleDTO);

    /** 게시글 상세보기에서 댓글보기 */
    List<ArticleDTO> readComment(int groupNo);

    /** 게시글 상세보기 */
    ArticleDTO detail(int articleId);

    /** 게시글 상세보기 조회수 업데이트 */
    public void updateHitcount(ArticleDTO articleDTO);

    /** 댓글 갯수 계산 */
    public int cellComments(int groupNo);

    /** 제목으로 게시글 검색 */
    public List<ArticleDTO> findSubject(String subject);
}