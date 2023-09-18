package com.pinkward.bushgg;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.article.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class ApplicationTests {

	@Autowired
	ArticleMapper articleMapper;

	@Test
	void findAllTest() {
		List<ArticleDTO> list = articleMapper.findAll();
		log.info("받은 리스트" , list);
	}

}
