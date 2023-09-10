package com.pinkward.bushgg.domain.challenges.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.challenges.dto.ChallengesDTO;
import com.pinkward.bushgg.domain.challenges.dto.LocalizedNameDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
@Slf4j
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class ChallengesServiceImpl implements ChallengesService {

    @Value("${riot.challenges.key}")
    private String riotApiKey;

    private final String apiUrl = "https://kr.api.riotgames.com/lol/challenges/v1/challenges/config";

    private final String krUrl = "https://kr.api.riotgames.com/lol/challenges/v1/challenges/";

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

        public ChallengesDTO findChallengesInfoKR ( int challengeId){
            ChallengesDTO challengesDTO;
            // Build the URL
            String url = krUrl + challengeId + "/config";

            // Set up headers with API key
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Riot-Token", riotApiKey);

            // Create the request entity
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Send the GET request using RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            String json = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                ChallengesDTO result = objectMapper.readValue(json, ChallengesDTO.class);

                LocalizedNameDTO koLocalizedNames = result.getLocalizedNames().get("ko_KR");
                if (koLocalizedNames != null) {
                    Map<String, LocalizedNameDTO> newLocalizedNames = new HashMap<>();
                    newLocalizedNames.put("ko_KR", koLocalizedNames);
                    result.setLocalizedNames(newLocalizedNames);
                }
                return result;
            } catch (JsonProcessingException e) {
                log.error("JSON Parsing Error", e);
                return null;
            }
        }
    }
