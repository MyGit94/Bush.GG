package com.pinkward.bushgg.domain.api.service;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class APIRankingServiceImpl implements APIRankingService{

    private ObjectMapper objectMapper = new ObjectMapper();
    @Value("${riot.api.key}")
    private String mykey;
    String serverUrl = "https://kr.api.riotgames.com/lol/";


    /**
     * ChallengerRanking을 List로 가져오는 메소드
     * @return  ChallengerRanking 정보를 담고 있는 Map의 List, 서버 응답을 받지 못하면 null
     */
    @Override
    public List<Map<String, Object>> challengerRanking() {

        List<Map<String, Object>> entries = new ArrayList<>();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Map<String, Object> ranking = objectMapper.readValue(entity.getContent(), Map.class);
            entries = (List<Map<String, Object>>) ranking.get("entries");

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } return entries;
    }


    /**
     * GrandMasterRanking을 List로 가져오는 메소드
     * @return  GrandMasterRanking 정보를 담고 있는 Map의 List, 서버 응답을 받지 못하면 null
     */
    @Override
    public List<Map<String, Object>> grandMasterRanking() {

        List<Map<String, Object>> entries = new ArrayList<>();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/grandmasterleagues/by-queue/RANKED_SOLO_5x5?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Map<String, Object> ranking = objectMapper.readValue(entity.getContent(), Map.class);
            entries = (List<Map<String, Object>>) ranking.get("entries");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return entries;
    }
}