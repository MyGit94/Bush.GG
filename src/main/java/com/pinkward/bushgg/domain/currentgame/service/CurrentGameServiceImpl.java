package com.pinkward.bushgg.domain.currentgame.service;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.champion.mapper.ChampionMapper;
import com.pinkward.bushgg.domain.currentgame.dto.CurrentGameDTO;
import com.pinkward.bushgg.domain.match.common.TimeTranslator;
import com.pinkward.bushgg.domain.match.service.MatchService;
import com.pinkward.bushgg.domain.ranking.mapper.TierMapper;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrentGameServiceImpl implements CurrentGameService {

    private final MatchService matchService;
    private final ChampionMapper championMapper;
    private final TierMapper tierMapper;
    private final SummonerService summonerService;
    private final APIServiceKo apiServiceKo;

    @Override
    public List<Map<String,Object>> getCurrentGameInfo(List<Map<String, Object>> currentGames) {

        List<Map<String,Object>> currentGameDTOS = new ArrayList<>();

        for (Map<String, Object> currentGame : currentGames){
            Map<String, Object> currentGameInfo = new HashMap<>();
            List<CurrentGameDTO> currentSummoner = new ArrayList<>();

            currentGameInfo.put("gameQueueName",matchService.matchQueueName((int) currentGame.get("gameQueueConfigId")) );

            List<Map<String, Object>> participants = (List<Map<String, Object>>) currentGame.get("participants");
            for (Map<String, Object> participant : participants) {

                CurrentGameDTO currentGameDTO = new CurrentGameDTO();
                currentGameDTO.setSpell1Id((int) participant.get("spell1Id"));
                currentGameDTO.setSpell2Id((int) participant.get("spell2Id"));
                currentGameDTO.setTeamId((int) participant.get("teamId"));
                currentGameDTO.setChampionName(championMapper.getChampionEnName((int) participant.get("championId")));
                currentGameDTO.setSummonerName((String) participant.get("summonerName"));
                currentGameDTO.setSummonerId((String) participant.get("summonerId"));

                if (tierMapper.getTierById(currentGameDTO.getSummonerId()) != null) {
                    currentGameDTO.setSummonerTier(summonerService.changeTierName(tierMapper.getTierById(currentGameDTO.getSummonerId())));
                } else {
                    SummonerTierDTO summonerTierDTO = summonerService.getTierInfo(apiServiceKo.getTierInfo(currentGameDTO.getSummonerId()));
                    currentGameDTO.setSummonerTier(summonerService.changeTierName(summonerTierDTO.getTierName()));
                }
                currentSummoner.add(currentGameDTO);
            }

            currentGameInfo.put("summoners",currentSummoner);
            currentGameInfo.put("gameStartTime",currentGame.get("gameStartTime"));
            currentGameInfo.put("gameLength", TimeTranslator.unixMinAndSec((int)currentGame.get("gameLength")));
            currentGameDTOS.add(currentGameInfo);
        }
        return currentGameDTOS;
    }
}
