package com.pinkward.bushgg.domain.champion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class ChampionServiceImpl implements ChampionService{

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Value("${riot.api.key}")
    private String mykey;
    String serverUrl = "https://kr.api.riotgames.com";

    /**
     * 챔피언 로테이션 가져오는 메소드
     * @return 챔피언 로테이션
     */
    @Override
    public List<Integer> championLotation() {
        List<Integer> championLotation = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/platform/v3/champion-rotations?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            Map<String, Object> champions = objectMapper.readValue(entity.getContent(), Map.class);
            championLotation = (List<Integer>) champions.get("freeChampionIds");
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return championLotation;
    }

    /**
     * 최근 20게임 플레이한 챔피언 카운트 증가 메소드
     * @param championCounts 챔피언 승률 객체
     * @param participant 게임 참가자 정보 객체
     * @return 최근 20게임 플레이한 챔피언 별 승률 List
     */
    public List<ChampionCount> getChampionCounts (List<ChampionCount> championCounts, ParticipantsDTO participant){

        boolean found = false;

        for (ChampionCount championCount : championCounts) {
            if (participant.getChampionName().equals(championCount.getChampionName())) {
                championCount.setCount(championCount.getCount() + 1);
                if(participant.isWin()){
                    championCount.setWin(championCount.getWin() + 1);
                }
                found = true;
                break;
            }
        }

        if (!found) {

            ChampionCount newChampionCount = new ChampionCount();
            newChampionCount.setChampionName(participant.getChampionName());
            newChampionCount.setCount(1);
            if (participant.isWin()) {
                newChampionCount.setWin(1);
            } else {
                newChampionCount.setWin(0);
            }
            championCounts.add(newChampionCount);
        }
        return championCounts;
    }

    /**
     * 플레이한 챔피언별 승률 List를 플레이한 횟수의 내림차순으로 정렬하는 메소드
     * @param championCounts 플레이한 챔피언별 승률 List
     * @return 정렬한 최근 20게임 플레이한 챔피언 별 승률 List
     */
    public List<ChampionCount> sortChampionCounts(List<ChampionCount> championCounts){
        Iterator<ChampionCount> iterator = championCounts.iterator();
        while (iterator.hasNext()) {
            ChampionCount championCount = iterator.next();
            if (championCount.getCount() == 1) {
                iterator.remove();
            }
        }

        Collections.sort(championCounts, Comparator.comparing(ChampionCount::getCount).reversed());
        List<ChampionCount> filteredChampionCounts = championCounts.subList(0, Math.min(5, championCounts.size()));

        for (ChampionCount championCount: filteredChampionCounts) {
            double winRate = ((double)championCount.getWin() / championCount.getCount()) * 100;
            championCount.setWinRate((int)Math.round(winRate));
        }
        return filteredChampionCounts;
    }
}
