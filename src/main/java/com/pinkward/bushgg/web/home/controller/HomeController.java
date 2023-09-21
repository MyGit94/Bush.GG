package com.pinkward.bushgg.web.home.controller;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.article.mapper.ArticleMapper;
import com.pinkward.bushgg.domain.champion.mapper.ChampionMapper;
import com.pinkward.bushgg.domain.champion.service.ChampionService;
import com.pinkward.bushgg.domain.currentgame.service.CurrentGameService;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.ranking.mapper.TierMapper;
import com.pinkward.bushgg.domain.ranking.service.RankingAPIService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 홈페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 *
 * @author  에너자이조 김기정
 * @since   2023. 9. 4.
 * @version 1.0
 */
@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class HomeController {
	private	final ArticleMapper articleMapper;
	private final ChampionService championService;
	private final ChampionMapper championMapper;
	private final CurrentGameService currentGameService;
	private final TierMapper challengerMapper;
	private final APIServiceKo apiServiceKo;
	private final RankingAPIService rankingAPIService;


	@GetMapping("/")
	public String home(@SessionAttribute(name="loginMember", required = false) MemberDTO loginMember, Model model , HttpSession httpSession) {

		if (loginMember != null) {
			model.addAttribute("loginMember", loginMember);
		}

		List<Integer> championIds = championService.championLotation();
		List<String> championNamesEn = new ArrayList<>();
		List<String> championNamesKo = new ArrayList<>();

		for (Integer championId : championIds) {
			championNamesEn.add(championMapper.getChampionEnName(championId));
			championNamesKo.add(championMapper.getChampionKoName(championId));
		}

		model.addAttribute("championNamesEn",championNamesEn);
		model.addAttribute("championNamesKo",championNamesKo);


		// 09/18 추가 -송우성- 커뮤니티
		List<ArticleDTO> articleDTO= articleMapper.findAllByHitcount();
		List<ArticleDTO> limitedList = articleDTO.subList(0, 10); // 최대 요소 개수 제한

		model.addAttribute("community", limitedList);
		httpSession.setAttribute("status" , 0);
		return "index";
	}
}
