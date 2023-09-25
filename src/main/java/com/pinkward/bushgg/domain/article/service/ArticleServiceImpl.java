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

    /**
     * 게시글 등록하는 메소드
     * @param articleDTO 게시글 정보를 담는 객체
     */
    @Override
    @Transactional
    public void write(ArticleDTO articleDTO) {
        articleMapper.create(articleDTO);
    }

    /**
     * 댓글 등록하는 메소드
     * @param articleDTO 게시글 정보를 담는 객체
     */
    @Override
    @Transactional
    public void createComment(ArticleDTO articleDTO) {
        articleMapper.createComment(articleDTO);
    }

    /**
     * 게시글 조회수 증가 메소드
     * @param articleDTO 게시글 정보를 담는 객체
     */
    @Override
    public void updateHitcount(ArticleDTO articleDTO) {
        articleMapper.updateHitcount(articleDTO);
    }

    /**
     * 게시글 댓글 수 계산 하는 메소드
     * @param groupNo 게시글 식별 코드
     * @return 댓글 수
     */
    @Override
    public int cellComments(int groupNo) {
        return articleMapper.cellComments(groupNo);
    }

    /**
     * 게시글 제목으로 게시글 검색하는 메소드
     * @param subject 게시글 제목
     * @return 해당 제목이 포함된 게시글 List
     */
    @Override
    public List<ArticleDTO> findSubject(String subject) {
        return articleMapper.findSubject(subject);
    }

    /**
     * 전체 게시글 조회수 가져오는 메소드
     * @return 게시글 별 조회수
     */
    @Override
    public List<ArticleDTO> findAllByHitcount() {
        return articleMapper.findAllByHitcount();
    }

    /**
     * 게시글 상세 정보 가져오는 메소드
     * @param articleId 게시글 번호
     * @return
     */
    @Override
    public ArticleDTO detail(int articleId) {
        return articleMapper.detail(articleId);
    }

    /**
     * 게시글 상세 보기 시 댓글 리스트 출력하는 메소드
     * @param groupNo 게시글 식별 코드
     * @return 해당 게시글의 댓글 List
     */
    @Override
    public List<ArticleDTO> read(int groupNo) {
        return articleMapper.readComment(groupNo);
    }

    /**
     * 전체 게시글 수를 가져오는 메소드
     * @return 전체 게시글 수
     */
    @Override
    public int countAll() {
        return articleMapper.getCountAll();
    }

    /**
     * 페이징 처리해서 전체 게시글 가져오는 메소드
     * @param pageParams 전체 게시글 수로 페이징 처리한 객체
     * @return 페이지 별 게시글 리스트
     */
    @Override
    public List<ArticleDTO> findByAll2(PageParams pageParams) {
        return articleMapper.findByAll(pageParams);
    }

}
