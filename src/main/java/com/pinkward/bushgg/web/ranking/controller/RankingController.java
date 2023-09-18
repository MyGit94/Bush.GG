package com.pinkward.bushgg.web.ranking.controller;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import com.pinkward.bushgg.domain.ranking.service.RankingAPIService;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class RankingController {
    private final RankingAPIService rankingService;
    private final SummonerService summonerService;
    private final APIServiceKo apiServiceKo;

    @GetMapping(value="/ranking")

    public String raking(Model model) {
        int start = 0;
        int end = 50;
        List<RankingDTO> challengerRanking =  rankingService.challengerRanking(start, end);
        int count =1;
        for(RankingDTO rankingDTO : challengerRanking){
            SummonerDTO summoner = apiServiceKo.getSummonerInfo(rankingDTO.getSummonerName().replace(" ","%20"));
//            rankingDTO.setLevel(summoner.getSummonerLevel());
            log.info("{} {} ",count, rankingDTO);
            count++;
        }


        log.info("{}",challengerRanking);
        model.addAttribute("challengerRanking",challengerRanking);
        return "summoner-ranking";
    }

}
