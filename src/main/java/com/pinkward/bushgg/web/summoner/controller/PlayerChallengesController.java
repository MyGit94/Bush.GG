package com.pinkward.bushgg.web.summoner.controller;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.challenges.dto.*;
import com.pinkward.bushgg.domain.challenges.mapper.ChallengesMapper;
import com.pinkward.bushgg.domain.challenges.service.ChallengesService;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayerChallengesController {

    private final SummonerService summonerService;
    private final APIServiceKo apiServiceKo;
    private final ChallengesService challengesService;

    @Autowired
    ChallengesMapper challengesMapper;

    @GetMapping(value="/challenge")
    public String playerChallenges (@RequestParam("summonerName") String summonerName, Model model){
        if (summonerName.length() == 2) {
            summonerName = summonerName.charAt(0) + " " + summonerName.substring(1);
        }
        String esummonerName = null;
        esummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8);

        // 등급순으로 정렬해주기 위한 작업
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
        // 가져온 challenges 정보중 challengeId가 101000 이상인 값만 가져옴
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
