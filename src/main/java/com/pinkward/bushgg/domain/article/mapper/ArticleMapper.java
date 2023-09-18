package com.pinkward.bushgg.domain.article.mapper;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

//	전체 게시글 목록 출력
	public List<ArticleDTO> findAll();
	//	게시글 생성
	public void create();
//	게시글 수정
	public void updateArticle(String passwd);
//	게시글 삭제
	public void delete(String passwd);

}