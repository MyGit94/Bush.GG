package com.pinkward.bushgg.domain.ranking.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.ranking.dto.ChallengerRankingDTO;
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
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class ChallengerRankingServiceImpl implements ChallengerRankingService{

    @Value("${riot.api.key}")
    private String apikey;

    @Override
    public List<ChallengerRankingDTO> getChallengerInfo() {
        List<ChallengerRankingDTO> list = new ArrayList<>();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=" + apikey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                // 에러 처리
                return list; // 빈 리스트를 반환하거나 예외를 던지거나 에러 처리 방법을 선택하세요.
            }
            org.apache.http.HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(entity.getContent());

            // rootNode가 배열이 아닌 경우에 대한 처리는 필요하지 않습니다.

            for (JsonNode node : rootNode.get("entries")) {
                ChallengerRankingDTO challenger = objectMapper.treeToValue(node, ChallengerRankingDTO.class);
                list.add(challenger);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
            return list; // 빈 리스트를 반환하거나 예외를 던지거나 에러 처리 방법을 선택하세요.
        }
        return list;
    }
}
