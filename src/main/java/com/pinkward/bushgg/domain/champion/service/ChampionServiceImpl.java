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

    private ObjectMapper objectMapper = new ObjectMapper();



    @Value("${riot.api.key}")
    private String mykey;

    @Override
    public List<Integer> championLotation() {
        List<Integer> championLotation = null;

        String serverUrl = "https://kr.api.riotgames.com";

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
            // 챔피언 이름이 리스트에 없으면 새 객체 생성하여 리스트에 추가
            ChampionCount newChampionCount = new ChampionCount();
            newChampionCount.setChampionName(participant.getChampionName());
            newChampionCount.setCount(1);
            if (participant.isWin()) {
                newChampionCount.setWin(1);
            } else {
                newChampionCount.setWin(0); // 이긴 횟수를 초기화
            }
            championCounts.add(newChampionCount);
        }
        return championCounts;
    }

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

        // 이후에 winRate를 계산하고 설정
        for (ChampionCount championCount: filteredChampionCounts) {
            double winRate = ((double)championCount.getWin() / championCount.getCount()) * 100;
            championCount.setWinRate((int)Math.round(winRate));
        }
        return filteredChampionCounts;
    }
}
