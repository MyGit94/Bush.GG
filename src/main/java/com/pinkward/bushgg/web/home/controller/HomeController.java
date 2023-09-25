package com.pinkward.bushgg.web.home.controller;

import com.pinkward.bushgg.domain.article.dto.ArticleDTO;
import com.pinkward.bushgg.domain.article.mapper.ArticleMapper;
import com.pinkward.bushgg.domain.champion.mapper.ChampionMapper;
import com.pinkward.bushgg.domain.champion.service.ChampionService;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import com.pinkward.bushgg.domain.ranking.service.RankingAPIService;
import com.pinkward.bushgg.domain.ranking.service.RankingService;
import com.pinkward.bushgg.domain.summoner.mapper.SummonerMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 홈페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class HomeController {
	private	final ArticleMapper articleMapper;
	private final ChampionService championService;
	private final ChampionMapper championMapper;
	private final RankingAPIService rankingAPIService;

	/**
	 * index 페이지 실행시 실행되는 메소드
	 * @return index 페이지
	 */
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

		List<ArticleDTO> articleDTO = articleMapper.findAllByHitcount();
		List<ArticleDTO> limitedList = articleDTO.subList(0, 10);

		for (ArticleDTO article : limitedList) {
			String articleTime = article.getRegdate();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime regDateTime = LocalDateTime.parse(articleTime, formatter);
			LocalDateTime currentDateTime = LocalDateTime.now();

			long times = ChronoUnit.HOURS.between(regDateTime, currentDateTime);
			if (times <= 24) {
				String changeHours = String.valueOf(times);
				article.setRegdate(changeHours + "시간 전");
			} else {
				long days = times / 24;
				String changeDays = String.valueOf(days);
				article.setRegdate(changeDays + "일 전");
			}
		}

		model.addAttribute("community", limitedList);
		httpSession.setAttribute("status", 0);
		return "index";
	}


	@GetMapping("/test")
	void test(){
		rankingAPIService.challengerRanking(0,999);
		rankingAPIService.grandMasterRanking(0,999);
		rankingAPIService.masterRanking(0,9999);
		rankingAPIService.diamond1Ranking(0,999);
		rankingAPIService.diamond2Ranking(0,999);
		rankingAPIService.diamond3Ranking(0,999);
		rankingAPIService.diamond4Ranking(0,999);
	}
}
