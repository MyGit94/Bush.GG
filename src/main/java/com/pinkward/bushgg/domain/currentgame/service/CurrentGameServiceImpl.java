package com.pinkward.bushgg.domain.currentgame.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.champion.service.ChampionService;
import com.pinkward.bushgg.domain.currentgame.dto.CurrentGameDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrentGameServiceImpl implements CurrentGameService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChampionService championService;

    @Value("${riot.api.key}")
    private String mykey;

    @Override
    public List<String> getSummonerName() {

        String serverUrl = "https://kr.api.riotgames.com";
        List<String> summonerNames = new ArrayList<>();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            Map<String, Object> ranking = objectMapper.readValue(entity.getContent(), Map.class);
            String tier = (String) ranking.get("tier");

            List<Map<String, Object>> entries = (List<Map<String, Object>>) ranking.get("entries");

            // leaguePoints를 기준으로 내림차순 정렬
            entries.sort((entry1, entry2) -> {
                Integer lp1 = (Integer) entry1.get("leaguePoints");
                Integer lp2 = (Integer) entry2.get("leaguePoints");
                return lp2.compareTo(lp1);
            });

            // 상위 50개 데이터 가져오기
            for (int i = 0; i < Math.min(300, entries.size()); i++) {
                Map<String, Object> entry = entries.get(i);
                String summonerName = (String) entry.get("summonerName");
                summonerNames.add(summonerName);
            }

            log.info("{}", summonerNames);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return summonerNames;
    }


    @Override
    public String getSummonerId(String summonerName) {

        SummonerDTO summoner;
        String summonerId;

        String serverUrl = "https://kr.api.riotgames.com";

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            summoner = objectMapper.readValue(entity.getContent(), SummonerDTO.class);
            summonerId = summoner.getId();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return summonerId;
    }

    @Override
    public Map<String, Object> getCurrentGame(String summonerId) {

        String serverUrl = "https://kr.api.riotgames.com";

        Map<String, Object> currentGame;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/spectator/v4/active-games/by-summoner/" + summonerId + "?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            currentGame = objectMapper.readValue(entity.getContent(), Map.class);
            log.info("{}", currentGame);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return currentGame;
    }

    @Override
    public List<Map<String,Object>> getCurrentGameInfo(List<Map<String, Object>> currentGames) {
        List<Map<String,Object>> currentGameDTOS = new ArrayList<>();


        for (Map<String, Object> currentGame : currentGames){
            Map<String, Object> currentGameInfo = new HashMap<>();
            List<CurrentGameDTO> currentSummoner = new ArrayList<>();
            currentGameInfo.put("gameQueueId", currentGame.get("gameQueueConfigId")); //큐 타입 420

            // for문
            List<Map<String, Object>> participants = (List<Map<String, Object>>) currentGame.get("participants");
            for (int i = 0; i < participants.size(); i++) {
                CurrentGameDTO currentGameDTO = new CurrentGameDTO();
                Map<String, Object> participant = participants.get(i);
                currentGameDTO.setSpell1Id((int)participant.get("spell1Id"));
                currentGameDTO.setSpell2Id((int)participant.get("spell2Id"));
                currentGameDTO.setTeamId((int)participant.get("teamId"));
                currentGameDTO.setChampionId((int)participant.get("championId"));
                currentGameDTO.setSummonerName((String) participant.get("summonerName"));
                currentGameDTO.setSummonerId((String) participant.get("summonerId"));
                currentGameDTO.setChampionName(championService.getChampionNameEn((Integer.toString(currentGameDTO.getChampionId()))));
                currentSummoner.add(currentGameDTO);
            }

            currentGameInfo.put("summoners",currentSummoner);

            currentGameInfo.put("gameStartTime",currentGame.get("gameStartTime"));  //게임 시작시간
            currentGameInfo.put("gameLength",currentGame.get("gameLength")); // 게임 진행시간
            currentGameDTOS.add(currentGameInfo);
        }


        return currentGameDTOS;
    }
}
