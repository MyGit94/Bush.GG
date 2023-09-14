package com.pinkward.bushgg.domain.challenges.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.challenges.dto.ChallengeRankingPlayerDTO;
import com.pinkward.bushgg.domain.challenges.dto.ChallengesDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class ChallengesServiceImpl implements ChallengesService {

    @Value("${riot.challenges.key}")
    private String riotApiKey;

    private final String apiUrl = "https://kr.api.riotgames.com/lol/challenges/v1/challenges/config";

    private final String rankUrl = "https://kr.api.riotgames.com/lol/challenges/v1/challenges/";

    public List<ChallengesDTO> allChallengesInfo() {
        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", riotApiKey);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate을 사용하여 API에 GET 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        // API 응답 데이터(JSON) 가져오기
        String json = response.getBody();
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(List.class, ChallengesDTO.class));
        } catch (JsonProcessingException e) {
            log.error("JSON Parsing Error", e);
            return Collections.emptyList();  // In case of error return an empty list.
        }
    }

    @Override
    public List<ChallengeRankingPlayerDTO> challengeRanking(int challengeId) {
        String serverUrl = "https://kr.api.riotgames.com";
        List<ChallengeRankingPlayerDTO> rankingList = new ArrayList<>();
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/challenges/v1/challenges/" + challengeId + "/leaderboards/by-level/CHALLENGER?limit=50&api_key=" + riotApiKey);

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