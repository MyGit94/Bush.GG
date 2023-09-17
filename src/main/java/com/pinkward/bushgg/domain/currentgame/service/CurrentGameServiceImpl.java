package com.pinkward.bushgg.domain.currentgame.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.champion.mapper.ChampionMapper;
import com.pinkward.bushgg.domain.champion.service.ChampionService;
import com.pinkward.bushgg.domain.currentgame.dto.CurrentGameDTO;
import com.pinkward.bushgg.domain.match.common.TimeTranslator;
import com.pinkward.bushgg.domain.match.service.MatchService;
import com.pinkward.bushgg.domain.ranking.mapper.ChallengerMapper;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrentGameServiceImpl implements CurrentGameService {

    private final MatchService matchService;
    private final ChampionMapper championMapper;
    private final ChallengerMapper challengerMapper;
    private final SummonerService summonerService;
    private final APIServiceKo apiServiceKo;


    @Value("${riot.api.key}")
    private String mykey;

    @Override
    public List<Map<String,Object>> getCurrentGameInfo(List<Map<String, Object>> currentGames) {
        List<Map<String,Object>> currentGameDTOS = new ArrayList<>();

        for (Map<String, Object> currentGame : currentGames){
            Map<String, Object> currentGameInfo = new HashMap<>();
            List<CurrentGameDTO> currentSummoner = new ArrayList<>();

            currentGameInfo.put("gameQueueName",matchService.matchQueueName((int) currentGame.get("gameQueueConfigId")) ); //큐 타입 420

            // for문
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
                log.info("{}",challengerMapper.getTierById(currentGameDTO.getSummonerId()));
                if(challengerMapper.getTierById(currentGameDTO.getSummonerId())!= null){
                    currentGameDTO.setSummonerTier(summonerService.changeTierName(challengerMapper.getTierById(currentGameDTO.getSummonerId())));
                } else {
                    SummonerTierDTO summonerTierDTO = summonerService.getTierInfo(apiServiceKo.getTierInfo(currentGameDTO.getSummonerId()));
                    log.info("{}",summonerTierDTO.getTier());
                    currentGameDTO.setSummonerTier(summonerService.changeTierName(summonerTierDTO.getTierName()));
                }

                log.info("{}",currentGameDTO.getSummonerTier());

                currentSummoner.add(currentGameDTO);
            }

            currentGameInfo.put("summoners",currentSummoner);
            currentGameInfo.put("gameStartTime",currentGame.get("gameStartTime"));  //게임 시작시간
            currentGameInfo.put("gameLength", TimeTranslator.unixMinAndSec((int)currentGame.get("gameLength"))); // 게임 진행시간
            currentGameDTOS.add(currentGameInfo);
        }

        return currentGameDTOS;
    }


}
