package com.pinkward.bushgg.web.summoner.controller;

import com.pinkward.bushgg.domain.challenges.dto.ChallengesInfoDTO;
import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.challenges.service.ChallengesServiceImpl;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayerChallengesController {

    private final SummonerServiceImpl summonerService;

    private final ChallengesServiceImpl challengesService;

    @GetMapping(value="/challenge")
    public String playerChallenges (@RequestParam("summonerName") String summonerName, Model model){
        if (summonerName.length() == 2) {
            summonerName = summonerName.substring(0, 1) + " " + summonerName.substring(1);
        }
        String esummonerName;
        try {
            esummonerName = URLEncoder.encode(summonerName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        summonerName = summonerName.replaceAll(" ","").toLowerCase();
        log.info("esum:{}",esummonerName);
        log.info("소환사이름:{}",summonerName);

        SummonerDTO summoner = summonerService.summonerInfo(esummonerName);
        PlayerChallengesInfoDTO playerChallengesInfo = summonerService.getPlayerChallengesInfo(summoner.getPuuid());
        // 가져온 challenges 정보중 challengeId가 101000 이상인 값만 가져옴
        List<ChallengesInfoDTO> filter = playerChallengesInfo.getChallenges().stream()
                .filter(challenge -> challenge.getChallengeId() >= 101000)
                .collect(Collectors.toList());
        log.info("도전과제:{}", filter);

        List<Integer> challengeIds = filter.stream()
                .map(ChallengesInfoDTO::getChallengeId)
                .collect(Collectors.toList());
        log.info("아이디 : {}", challengeIds);


        model.addAttribute("challengeIds", challengeIds);
        model.addAttribute("summoner", summoner);
        model.addAttribute("playerChallengesInfo", playerChallengesInfo);
        model.addAttribute("filter", filter);

        return "challenge";
    }
}
