package com.pinkward.bushgg.domain;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.champion.mapper.ChampionMapper;
import com.pinkward.bushgg.domain.currentgame.service.CurrentGameService;
import com.pinkward.bushgg.domain.ranking.mapper.ChallengerMapper;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CurrentGameController {

    private final CurrentGameService currentGameService;
    private final SummonerService summonerService;
    private final ChampionMapper championMapper;
    private final ChallengerMapper challengerMapper;
    private final APIServiceKo apiServiceKo;

    @GetMapping(value = "/test")

    public String currentGame(Model model) {
        List<Map<String, Object>> currentGames = new ArrayList<>();
        int count = 1;
        int index = 1;
        List<String> challengerIds = challengerMapper.getChallengerInfo();


        for (String participantId : challengerIds) {
            log.info("{}",index);
            index++;
            if (count > 3 || index >50) {
                break;
            }
            Map<String, Object> currentGame = apiServiceKo.getCurrentGame(participantId);

            // 중복된 currentGame 패스
            if (currentGame != null) {
                boolean isDuplicate = false;

                for (Map<String, Object> existingGame : currentGames) {
                    long existingGameId = (long) existingGame.get("gameId");
                    long currentGameId = (long) currentGame.get("gameId");

                    if (currentGameId==(existingGameId)) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    currentGames.add(currentGame);
                    count++;
                }
            }
        }
            List<Map<String, Object>> currentGameInfo = currentGameService.getCurrentGameInfo(currentGames);
            log.info("{}",currentGameInfo);

            model.addAttribute("currentGameInfo", currentGameInfo);
            return "index";
        }

}
