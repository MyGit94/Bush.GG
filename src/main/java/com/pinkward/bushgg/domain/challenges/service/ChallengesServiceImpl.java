package com.pinkward.bushgg.domain.challenges.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.challenges.dto.ChallengeRankingPlayerDTO;
import lombok.extern.slf4j.Slf4j;
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


@Service
@Slf4j
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class ChallengesServiceImpl implements ChallengesService {

    @Value("${riot.challenges.key}")
    private String riotApiKey;

    private final String apiUrl = "https://kr.api.riotgames.com/lol/challenges/v1/challenges/config";

    private final String rankUrl = "https://kr.api.riotgames.com/lol/challenges/v1/challenges/";

    @Override
    public List<ChallengeRankingPlayerDTO> challengeRanking(int challengeId) {
        String serverUrl = "https://kr.api.riotgames.com";
        List<ChallengeRankingPlayerDTO> rankingList = new ArrayList<>();
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/challenges/v1/challenges/" + challengeId + "/leaderboards/by-level/CHALLENGER?limit=30&api_key=" + riotApiKey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            org.apache.http.HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(entity.getContent());

            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    ChallengeRankingPlayerDTO playerDto = objectMapper.treeToValue(node, ChallengeRankingPlayerDTO.class);
                    rankingList.add(playerDto);
                }
            } else {
                ChallengeRankingPlayerDTO playerDto = objectMapper.readValue(rootNode.traverse(), ChallengeRankingPlayerDTO.class);
                rankingList.add(playerDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return rankingList;
    }

}