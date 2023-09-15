package com.pinkward.bushgg.domain.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class APIServiceKoImpl implements APIServiceKo {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.ranking.key}")
    private String mykey;
    @Value("${riot.challenges.key}")
    private String challengeKey;

    String serverUrl = "https://kr.api.riotgames.com/lol/";

    @Override
    public SummonerDTO getSummonerInfo(String summonerName) {
        SummonerDTO summoner = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            summoner = objectMapper.readValue(entity.getContent(), SummonerDTO.class);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return summoner;
    }

    @Override
    public SummonerDTO getSummonerInfoByPuuid(String puuid) {
        SummonerDTO summoner = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "summoner/v4/summoners/by-puuid/" + puuid + "?api_key=" + challengeKey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            summoner = objectMapper.readValue(entity.getContent(), SummonerDTO.class);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return summoner;
    }


    @Override
    public PlayerChallengesInfoDTO getPlayerChallengesInfo(String puuid) {

        PlayerChallengesInfoDTO playerChallengesInfo;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "challenges/v1/player-data/" + puuid + "?api_key="+ mykey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }
            HttpEntity entity = response.getEntity();
            playerChallengesInfo = objectMapper.readValue(entity.getContent(),PlayerChallengesInfoDTO.class);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return playerChallengesInfo;
    }

    @Override
    public Set<Map<String,Object>> getTierInfo(String summonerId) {
        Set<Map<String,Object>> summonerTier;
        SummonerTierDTO summonerTierDTO = new SummonerTierDTO();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/entries/by-summoner/" + summonerId + "?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            summonerTier = objectMapper.readValue(entity.getContent(), Set.class);

            if (summonerTier.isEmpty()) {
                // JSON 데이터가 비어있으면 저장하지 않음
                return null;
            }

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return summonerTier;

    }

    @Override
    public Map<String, Object> getCurrentGame(String summonerId) {


        Map<String, Object> currentGame;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "spectator/v4/active-games/by-summoner/" + summonerId + "?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            currentGame = objectMapper.readValue(entity.getContent(), Map.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return currentGame;
    }
}
