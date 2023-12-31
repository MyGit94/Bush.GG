package com.pinkward.bushgg.web.summoner.controller;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.challenges.dto.ChallengeRankingPlayerDTO;
import com.pinkward.bushgg.domain.challenges.dto.ChallengesInfoDTO;
import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.challenges.mapper.ChallengesMapper;
import com.pinkward.bushgg.domain.challenges.service.ChallengesService;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 도전과제 페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayerChallengesController {

    private final APIServiceKo apiServiceKo;
    private final ChallengesService challengesService;

    @Autowired
    ChallengesMapper challengesMapper;

    /**
     * 해당 유저의 달성 도전과제 정보를 처리하는 메소드
     * @return 유저의 달성 도전과제 정보 페이지
     */
    @GetMapping(value="/challenge")
    public String playerChallenges (@RequestParam("summonerName") String summonerName, Model model){
        if (summonerName.length() == 2) {
            summonerName = summonerName.charAt(0) + " " + summonerName.substring(1);
        }
        String esummonerName = null;
        esummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8);

        Map<String, Integer> levelOrder = new HashMap<>();
        levelOrder.put("CHALLENGER", 1);
        levelOrder.put("GRANDMASTER", 2);
        levelOrder.put("MASTER", 3);
        levelOrder.put("DIAMOND", 4);
        levelOrder.put("PLATINUM", 5);
        levelOrder.put("GOLD", 6);
        levelOrder.put("SILVER", 7);
        levelOrder.put("BRONZE", 8);
        levelOrder.put("IRON", 9);
        SummonerDTO summoner = apiServiceKo.getSummonerInfo(esummonerName);
        PlayerChallengesInfoDTO playerChallengesInfo = apiServiceKo.getPlayerChallengesInfo(summoner.getPuuid());

        List<ChallengesInfoDTO> filter = playerChallengesInfo.getChallenges().stream()
                .filter(challenge -> challenge.getChallengeId() >= 101000)
                .sorted(Comparator.comparingInt(challenge -> levelOrder.getOrDefault(challenge.getLevel(), Integer.MAX_VALUE)))
                .collect(Collectors.toList());

        List<Integer> challengeIds = filter.stream()
                .map(ChallengesInfoDTO::getChallengeId)
                .collect(Collectors.toList());

        model.addAttribute("challengeIds", challengeIds);
        model.addAttribute("summoner", summoner);
        model.addAttribute("playerChallengesInfo", playerChallengesInfo);
        model.addAttribute("filter", filter);

        return "challenge";
    }

    /**
     * 도전과제 종류별 랭킹 정보를 처리하는 메소드
     * @return 해당 도전과제 랭킹 정보
     */
    @GetMapping(value="/challenge/rank/{challengeId}")
    public String challengeRankPage(@PathVariable("challengeId") int challengeId, Model model){
        model.addAttribute("challengeId", challengeId);

        List<ChallengeRankingPlayerDTO> ranking = challengesService.challengeRanking(challengeId);
        for (ChallengeRankingPlayerDTO list: ranking) {
            SummonerDTO summonerDTO =apiServiceKo.getSummonerInfoByPuuid(list.getPuuid());
            list.setName(summonerDTO.getName());
            list.setProfileIcon(summonerDTO.getProfileIconId());
        }
        model.addAttribute("ranking", ranking);

        return "challenge-ranking";
    }
}
