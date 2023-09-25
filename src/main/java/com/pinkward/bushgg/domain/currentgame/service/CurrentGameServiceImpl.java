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

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrentGameServiceImpl implements CurrentGameService {

    private final MatchService matchService;
    private final ChampionMapper championMapper;
    private final TierMapper challengerMapper;
    private final SummonerService summonerService;
    private final APIServiceKo apiServiceKo;


    /**
     * 실시간 게임의 상세 정보를 반환하는 메소드
     * @param currentGames 실시간 게임 리스트
     * @return 실시간 게임의 상세정보 List
     */
    @Override
    public List<Map<String,Object>> getCurrentGameInfo(List<Map<String, Object>> currentGames) {
        List<Map<String,Object>> currentGameDTOS = new ArrayList<>();

        for (Map<String, Object> currentGame : currentGames){
            Map<String, Object> currentGameInfo = new HashMap<>();
            List<CurrentGameDTO> currentSummoner = new ArrayList<>();

            currentGameInfo.put("gameQueueName",matchService.matchQueueName((int) currentGame.get("gameQueueConfigId")) );
            currentGameInfo.put("summoners",currentSummoner);
            currentGameInfo.put("gameLength", TimeTranslator.currentGameTime((long) currentGame.get("gameStartTime")));

            List<Map<String, Object>> participants = (List<Map<String, Object>>) currentGame.get("participants");
            for (int i = 0; i < participants.size(); i++) {
                CurrentGameDTO currentGameDTO = new CurrentGameDTO();
                Map<String, Object> participant = participants.get(i);

                currentGameDTO.setSpell1Id((int)participant.get("spell1Id"));
                currentGameDTO.setSpell2Id((int)participant.get("spell2Id"));
                currentGameDTO.setTeamId((int)participant.get("teamId"));
                currentGameDTO.setChampionName(championMapper.getChampionEnName((int)participant.get("championId")));
                currentGameDTO.setSummonerName((String) participant.get("summonerName"));
                currentGameDTO.setSummonerId((String) participant.get("summonerId"));

                if(challengerMapper.getTierById(currentGameDTO.getSummonerId())!= null){
                    currentGameDTO.setSummonerTier(summonerService.changeTierName(challengerMapper.getTierById(currentGameDTO.getSummonerId())));
                } else {
                    SummonerTierDTO summonerTierDTO = summonerService.getTierInfo(apiServiceKo.getTierInfo(currentGameDTO.getSummonerId()));
                    currentGameDTO.setSummonerTier(summonerService.changeTierName(summonerTierDTO.getTierName()));
                }

                currentSummoner.add(currentGameDTO);
            }
            currentGameDTOS.add(currentGameInfo);
        }
        return currentGameDTOS;
    }
}
