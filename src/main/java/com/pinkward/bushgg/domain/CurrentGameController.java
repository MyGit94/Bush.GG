package com.pinkward.bushgg.domain;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.currentgame.service.CurrentGameService;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CurrentGameController {

    private final CurrentGameService currentGameService;
    private final SummonerService summonerService;
    private final ChallengerMapper challengerMapper;
    private final APIServiceKo apiServiceKo;

    @GetMapping(value="/test")

    public String raking(Model model) {
        List<Map<String, Object>> currentGames = new ArrayList<>();
        List<String> summonerNames = currentGameService.getSummonerName();
        int count = 1;

        for (String summonerName:  summonerNames){
            if (count > 3) {
                break; // count가 3 이상이면 for 루프 종료
            }
           // String summonerId = currentGameService.getSummonerId(summonerName.replace(" ","%20"));
            Map<String, Object> currentGame = currentGameService.getCurrentGame(challengerMapper.getChallengerInfo(summonerName.replace("Trust me05","2977583581284160")).getId());

            // 중복된 currentGame 패스
            if (currentGame != null) {
                boolean isDuplicate = false;
                for (Map<String, Object> existingGame : currentGames) {
                    long existingGameId = (long) existingGame.get("gameId");
                    long currentGameId = (long) currentGame.get("gameId");

                    if (currentGameId==(existingGameId)) {
                        isDuplicate = true;
                        break; // 이미 리스트에 존재하는 경우 중복이므로 추가하지 않음
                    }
                }

                if (!isDuplicate) {
                    currentGames.add(currentGame);
                    count++;
                }
            }
        }

        log.info("{}",currentGames);
        List<Map<String,Object>> currentGameInfo = currentGameService.getCurrentGameInfo(currentGames);

        log.info("{}",currentGameInfo);
        model.addAttribute("currentGameInfo",currentGameInfo);
        return "index";
    }
    
    @GetMapping("/ttest")
    public void doTask(){
        SummonerDTO summoner = apiServiceKo.getSummonerInfo("란브로대머리");
        challengerMapper.summonerInfo(summoner);

    }


}
