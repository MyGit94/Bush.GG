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
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class APIServiceAsiaImpl implements APIServiceAsia{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.api.key}")
    private String mykey;

    String serverUrl = "https://asia.api.riotgames.com/lol/";

    @Override
    public List<String> getMatchId(String puuid) {
        List<String> matchId = null;
        int start = 0;
        int count = 10;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "match/v5/matches/by-puuid/" + puuid + "/ids?start="+start+"&count="+count+"&api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            matchId = objectMapper.readValue(entity.getContent(), List.class);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return matchId;
    }

    @Override
    public Map<String, Object> getMatch(String matchId) {

        Map<String, Object> match = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "match/v5/matches/" + matchId + "?api_key=" + mykey);
            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();

            match = objectMapper.readValue(entity.getContent(), Map.class);


        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return match;
    }
}
