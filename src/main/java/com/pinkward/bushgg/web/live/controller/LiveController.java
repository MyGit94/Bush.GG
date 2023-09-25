package com.pinkward.bushgg.web.live.controller;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.currentgame.service.CurrentGameService;
import com.pinkward.bushgg.domain.ranking.mapper.TierMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 실시간 정보 페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Controller
@RequestMapping("/live")
@Slf4j
@RequiredArgsConstructor
public class LiveController {
    private final CurrentGameService currentGameService;
    private final TierMapper tierMapper;
    private final APIServiceKo apiServiceKo;

    /**
     * 실시간 게임 페이지 실행시 실행되는 메소드
     * @return 실시간 게임 페이지
     */
    @GetMapping("")
    public String live(Model model){

        List<Map<String, Object>> currentGames = new ArrayList<>();
        int count = 1;
        int index = 1;
        List<String> challengerIds = tierMapper.getChallengerInfo();

        for (String participantId : challengerIds) {
            index++;
            if (count > 3 || index >200) {
                break;
            }
            Map<String, Object> currentGame = apiServiceKo.getCurrentGame(participantId);

            if (currentGame != null) {
                boolean isDuplicate = false;
                int queueId = (int)currentGame.get("gameQueueConfigId");
                if(queueId==0){
                    break;
                }
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
        model.addAttribute("currentGameInfo", currentGameInfo);
        return "live";
    }


}