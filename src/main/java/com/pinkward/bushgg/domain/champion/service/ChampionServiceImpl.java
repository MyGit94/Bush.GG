package com.pinkward.bushgg.domain.champion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
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
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class ChampionServiceImpl implements ChampionService{

    private ObjectMapper objectMapper = new ObjectMapper();



    @Value("${riot.api.key}")
    private String mykey;

    @Override
    public String getChampionNameKo(String getId) {
        String championName = null;
        try {
            // JSON 데이터 다운로드
            URL url = new URL("http://ddragon.leagueoflegends.com/cdn/13.17.1/data/ko_KR/champion.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            // JSON 파싱
            JSONObject data = new JSONObject(response.toString());


            // 여기에서 필요한 작업 수행
            // 예를 들어, 챔피언 정보 추출 등을 수행할 수 있습니다.
            JSONObject champions = data.getJSONObject("data");
            // 찾고자 하는 key
            String targetKey = getId; // 여기에 찾고자 하는 key를 넣으세요
            // key에 해당하는 챔피언의 이름을 가져옴
            for (String championKey : champions.keySet()) {
                JSONObject championData = champions.getJSONObject(championKey);
                String championId = championData.getString("key");

                if (championId.equals(targetKey)) {
                    championName = championData.getString("name");

                    break; // 원하는 챔피언을 찾았으므로 루프 종료
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return championName;

    }

    @Override
    public String getChampionNameEn(String getId) {
        String championName = null;
        try {
            // JSON 데이터 다운로드
            URL url = new URL("http://ddragon.leagueoflegends.com/cdn/13.17.1/data/en_US/champion.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            // JSON 파싱
            JSONObject data = new JSONObject(response.toString());


            // 여기에서 필요한 작업 수행
            // 예를 들어, 챔피언 정보 추출 등을 수행할 수 있습니다.
            JSONObject champions = data.getJSONObject("data");
            // 찾고자 하는 key
            String targetKey = getId; // 여기에 찾고자 하는 key를 넣으세요
            // key에 해당하는 챔피언의 이름을 가져옴
            for (String championKey : champions.keySet()) {
                JSONObject championData = champions.getJSONObject(championKey);
                String championId = championData.getString("key");

                if (championId.equals(targetKey)) {
                    championName = championData.getString("name");

                    // 벨베스
                    championName = championName.replace("Bel'Veth", "Belveth");

                    break; // 원하는 챔피언을 찾았으므로 루프 종료
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return championName;

    }

    @Override
    public String getChampionIdByName(String championName) {
        String championId = null;
        try {
            // JSON 데이터 다운로드
            URL url = new URL("http://ddragon.leagueoflegends.com/cdn/13.17.1/data/en_US/champion.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            // JSON 파싱
            JSONObject data = new JSONObject(response.toString());

            // 여기에서 필요한 작업 수행
            // 예를 들어, 챔피언 정보 추출 등을 수행할 수 있습니다.
            JSONObject champions = data.getJSONObject("data");

            for (String championKey : champions.keySet()) {
                JSONObject championData = champions.getJSONObject(championKey);
                String name = championData.getString("name");

                if (name.equals(championName)) {
                    championId = championData.getString("key");

                    break; // 원하는 챔피언을 찾았으므로 루프 종료
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return championId;
    }

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


}
