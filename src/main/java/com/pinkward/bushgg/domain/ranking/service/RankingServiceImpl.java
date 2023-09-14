package com.pinkward.bushgg.domain.ranking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
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
import java.util.*;

@Service
@Slf4j
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class RankingServiceImpl implements RankingService{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.ranking.key}")
    private String mykey;



    @Override
    public List<RankingDTO> ranking() {
        log.info("ranking 실행됨");
        List<RankingDTO> allRankings = new ArrayList<>();
        List<String> tiers = Arrays.asList("CHALLENGER", "GRANDMASTER", "MASTER");
        int page = 1;
        String serverUrl = "https://kr.api.riotgames.com";

        try {
            HttpClient client = HttpClientBuilder.create().build();

            for(String tier : tiers) {
                page = 1;

                while (true) {
                    HttpGet request = new HttpGet(serverUrl + "/lol/league-exp/v4/entries/RANKED_SOLO_5x5/" + tier + "/I?page=" + page + "&api_key=" + mykey);

                    HttpResponse response = client.execute(request);

                    if (response.getStatusLine().getStatusCode() != 200) {
                        //return null;
                        break;
                    }

                    HttpEntity entity = response.getEntity();
                    ObjectMapper objectMappers = new ObjectMapper();
                    objectMappers.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    List<RankingDTO> pageRanking = objectMappers.readValue(entity.getContent(), new TypeReference<List<RankingDTO>>() {});

                    if (pageRanking.isEmpty()) {
                        break; // 페이지 데이터가 없으면 종료
                    }

                    allRankings.addAll(pageRanking);
                    page++; // 다음 페이지로 이동
                    //log.info("Total {} rankings fetched", allRankings.size());
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        log.info("{}",allRankings);

        return allRankings;
    }

    @Override
    public List<RankingDTO> challengerRanking(int start, int end) {

        String serverUrl = "https://kr.api.riotgames.com";
        List<RankingDTO> challengerRanking = new ArrayList<>();

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
            for (int i = start; i < Math.min(end, entries.size()); i++) {
                Map<String, Object> entry = entries.get(i);
                RankingDTO rankingDTO = new RankingDTO();
                rankingDTO.setTier(tier);
                rankingDTO.setSummonerName((String) entry.get("summonerName"));
                rankingDTO.setLeaguePoints((Integer) entry.get("leaguePoints"));
                rankingDTO.setWins((Integer) entry.get("wins"));
                rankingDTO.setLosses((Integer) entry.get("losses"));

                challengerRanking.add(rankingDTO);
            }

            log.info("{}", challengerRanking);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return challengerRanking;
    }
}
