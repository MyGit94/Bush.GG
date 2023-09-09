package com.pinkward.bushgg.web.summoner.controller;

import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
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

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayerChallengesController {

    private final SummonerServiceImpl summonerService;


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
        model.addAttribute("summoner", summoner);
        model.addAttribute("playerChallengesInfo", playerChallengesInfo);

        return "challenge";
    }

}
