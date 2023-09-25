package com.pinkward.bushgg.web.ranking.controller;

import com.pinkward.bushgg.domain.api.service.APIRankingService;
import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import com.pinkward.bushgg.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 랭킹 페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;
    private final APIRankingService apiRankingService;

    /**
     * 페이지 번호를 받아 해당 랭킹 페이지를 출력하는 메소드
     * @return 해당 랭킹 페이지
     */
    @GetMapping(value="/ranking")
    public String raking(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        List<Map<String, Object>> entries1 = apiRankingService.challengerRanking();
        List<Map<String, Object>> entries2 = apiRankingService.grandMasterRanking();

        int start = 0;
        int end = 100;
        List<RankingDTO> challengerRanking = null;
        if(page==1 || page==4){
            start =0;
            end = 100;
        } else if(page ==2 || page==5) {
            start= 100;
            end = 200;
        } else if(page ==3 || page==6) {
            start= 200;
            end = 300;
        } else if(page ==7) {
            start= 300;
            end = 400;
        } else if(page ==8) {
            start= 400;
            end = 500;
        } else if(page ==9) {
            start= 500;
            end = 600;
        } else if(page ==10) {
            start= 600;
            end = 700;
        }

        if (page<4) {
            challengerRanking =  rankingService.challengerRanking(start, end, entries1);
        } else {
            challengerRanking =  rankingService.grandMasterRanking(start, end, entries2);
        }

        int challengerPoint = rankingService.challengerPoint(entries1);
        int grandmasterPoint =rankingService.grandmasterPoint(entries2);

        model.addAttribute("challengerRanking",challengerRanking);
        model.addAttribute("grandmasterPoint",grandmasterPoint);
        model.addAttribute("page",page);
        model.addAttribute("challengerPoint",challengerPoint);
        return "summoner-ranking";
    }


}