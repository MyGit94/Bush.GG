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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.ranking.key}")
    private String mykey;
    @Value("${riot.api.key}")
    private String mykey2;
    @Value("${riot.challenges.key}")
    private String challengeKey;

    String serverUrl = "https://kr.api.riotgames.com/lol/";

    /**
     * 유저의 닉네임으로 유저 정보를 가져오는 메소드
     * @param summonerName 유저의 닉네임
     * @return 유저 정보를 담은 객체, 서버 응답을 받지 못하면 null
     */
    @Override
    public SummonerDTO getSummonerInfo(String summonerName) {

        SummonerDTO summoner = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey2);

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

    /**
     * 유저의 puuid로 유저 정보를 가져오는 메소드
     * @param puuid Riot API에서 제공하는 유저의 puuid
     * @return 유저 정보를 담은 객체, 서버 응답을 받지 못하면 null
     */
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

    /**
     * 유저의 puuid로 유저의 도전과제 정보를 가져오는 메소드
     * @param puuid Riot API에서 제공하는 유저의 puuid
     * @return 도전과제 정보를 담은 객체, 서버 응답을 받지 못하면 null
     */
    @Override
    public PlayerChallengesInfoDTO getPlayerChallengesInfo(String puuid) {

        PlayerChallengesInfoDTO playerChallengesInfo = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "challenges/v1/player-data/" + puuid + "?api_key="+ mykey2);

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

    /**
     * 유저의 summonerId로 유저의 티어정보를 가져오는 메소드
     * @param summonerId 유저의 암호화된 id
     * @return 유저의 티어정보를 담고 있는 Map의 Set, 서버 응답을 받지 못하면 null
     */
    @Override
    public Set<Map<String,Object>> getTierInfo(String summonerId) {
        Set<Map<String,Object>> summonerTier = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/entries/by-summoner/" + summonerId + "?api_key=" + mykey2);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            summonerTier = objectMapper.readValue(entity.getContent(), Set.class);

            if (summonerTier.isEmpty()) {
                return null;
            }

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return summonerTier;
    }

    /**
     * 유저의 summonerId로 유저의 진행중인 게임 정보을 가져오는 메소드
     * @param summonerId 유저의 암호화된 id
     * @return 유저의 진행중인 게임 정보를 담은 Map, 서버 응답을 받지 못하면 null
     */
    @Override
    public Map<String, Object> getCurrentGame(String summonerId) {

        Map<String, Object> currentGame = null;
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
