package com.pinkward.bushgg.domain.article.mapper;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.common.web.PageParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

//	/** 신규 게시글 등록 */
	public void create(ArticleDTO articleDTO);

//	/** 전체 게시글 목록 반환 */
	public List<ArticleDTO> findAll();

//	/** 페이징 계산에 필요한 게시글 전체 갯수 반환 */
	public int getCountAll();

//	/** 페이징 계산(검색값 포함)에 필요한 게시글 전체 갯수 반환 */
    public int getCountAll(PageParams pageParams);

//	/** 요청 페이지, 페이지당 보여지는 목록 갯수에 따른 목록 반환 */
//	public List<ArticleDTO> findByAll(PageParams pageParams);

//	/** 댓글, 대댓글 쓰기, 게시글 상세보기, 게시글 수정, 게시글 삭제 등 기능 추가*/

//	/** 댓글 등록*/
	public void createComment(ArticleDTO articleDTO);

//	/** 대댓글 등록*/
	public void commentByComment(ArticleDTO articleDTO);

	public List<ArticleDTO> readComment(int groupNo);


//	/** 댓글 삭제*/
	public void removeComment(int articleId, String passwd);

//	/** 댓글 수정*/
	public void update(ArticleDTO articleDTO);

//	/** 게시글 상세보기 */
	public ArticleDTO detail(int articleId);

//	게시글 상세보기 조회수 업
	public void updateHitcount(ArticleDTO articleDTO);
	
}