package com.pinkward.bushgg.domain.ranking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import com.pinkward.bushgg.domain.summoner.mapper.SummonerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class RankingAPIServiceImpl implements RankingAPIService {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SummonerMapper summonerMapper;

    @Value("${riot.ranking.key}")
    private String mykey;
    String serverUrl = "https://kr.api.riotgames.com/lol/";

    /**
     * 챌린저 티어 랭킹 정보를 DB에 저장하는 메소드
     * @return 랭킹 정보 List
     */
    @Override
    public List<RankingDTO> challengerRanking(int start, int end) {

        List<RankingDTO> challengerRanking = new ArrayList<>();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Map<String, Object> ranking = objectMapper.readValue(entity.getContent(), Map.class);
            String tier = (String) ranking.get("tier");

            List<Map<String, Object>> entries = (List<Map<String, Object>>) ranking.get("entries");

            entries.sort((entry1, entry2) -> {
                Integer lp1 = (Integer) entry1.get("leaguePoints");
                Integer lp2 = (Integer) entry2.get("leaguePoints");
                return lp2.compareTo(lp1);
            });

            for (int i = start; i < Math.min(end, entries.size()); i++) {
                Map<String, Object> entry = entries.get(i);
                RankingDTO rankingDTO = new RankingDTO();
                rankingDTO.setTier(tier);
                rankingDTO.setSummonerId((String) entry.get("summonerId"));
                rankingDTO.setSummonerName((String) entry.get("summonerName"));
                rankingDTO.setLeaguePoints((Integer) entry.get("leaguePoints"));
                rankingDTO.setWins((Integer) entry.get("wins"));
                rankingDTO.setLosses((Integer) entry.get("losses"));

                summonerMapper.insertChallenger(rankingDTO);
                challengerRanking.add(rankingDTO);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return challengerRanking;
    }

    /**
     * 그랜드마스터 티어 랭킹 정보를 DB에 저장하는 메소드
     * @return 랭킹 정보 List
     */
    @Override
    public List<RankingDTO> grandMasterRanking(int start, int end) {

        List<RankingDTO> grandMasterRanking = new ArrayList<>();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/grandmasterleagues/by-queue/RANKED_SOLO_5x5?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Map<String, Object> ranking = objectMapper.readValue(entity.getContent(), Map.class);
            String tier = (String) ranking.get("tier");

            List<Map<String, Object>> entries = (List<Map<String, Object>>) ranking.get("entries");

            entries.sort((entry1, entry2) -> {
                Integer lp1 = (Integer) entry1.get("leaguePoints");
                Integer lp2 = (Integer) entry2.get("leaguePoints");
                return lp2.compareTo(lp1);
            });

            for (int i = start; i < Math.min(end, entries.size()); i++) {
                Map<String, Object> entry = entries.get(i);
                RankingDTO rankingDTO = new RankingDTO();
                rankingDTO.setTier(tier);
                rankingDTO.setSummonerId((String) entry.get("summonerId"));
                rankingDTO.setSummonerName((String) entry.get("summonerName"));
                rankingDTO.setLeaguePoints((Integer) entry.get("leaguePoints"));
                rankingDTO.setWins((Integer) entry.get("wins"));
                rankingDTO.setLosses((Integer) entry.get("losses"));

                summonerMapper.insertGrandmaster(rankingDTO);
                grandMasterRanking.add(rankingDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return grandMasterRanking;
    }

    /**
     * 마스터 티어 랭킹 정보를 DB에 저장하는 메소드
     * @return 랭킹 정보 List
     */
    @Override
    public List<RankingDTO> masterRanking(int start, int end) {

        List<RankingDTO> masterRanking = new ArrayList<>();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/masterleagues/by-queue/RANKED_SOLO_5x5?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Map<String, Object> ranking = objectMapper.readValue(entity.getContent(), Map.class);
            String tier = (String) ranking.get("tier");

            List<Map<String, Object>> entries = (List<Map<String, Object>>) ranking.get("entries");

            entries.sort((entry1, entry2) -> {
                Integer lp1 = (Integer) entry1.get("leaguePoints");
                Integer lp2 = (Integer) entry2.get("leaguePoints");
                return lp2.compareTo(lp1);
            });

            for (int i = start; i < Math.min(end, entries.size()); i++) {
                Map<String, Object> entry = entries.get(i);
                RankingDTO rankingDTO = new RankingDTO();
                rankingDTO.setTier(tier);
                rankingDTO.setSummonerId((String) entry.get("summonerId"));
                rankingDTO.setSummonerName((String) entry.get("summonerName"));
                rankingDTO.setLeaguePoints((Integer) entry.get("leaguePoints"));
                rankingDTO.setWins((Integer) entry.get("wins"));
                rankingDTO.setLosses((Integer) entry.get("losses"));

                summonerMapper.insertMaster(rankingDTO);
                masterRanking.add(rankingDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return masterRanking;
    }

    /**
     * 다이아 1티어 랭킹 정보를 DB에 저장하는 메소드
     * @return 랭킹 정보 List
     */
    @Override
    public List<RankingDTO> diamond1Ranking(int start, int end) {

        List<RankingDTO> diamondRanking = new ArrayList<>();

        int page = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/entries/RANKED_SOLO_5x5/DIAMOND/I?page=" + page + "&api_key=" + mykey);

            HttpResponse response;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Set<Map<String, Object>> currentPageRanking;

            try {
                currentPageRanking = objectMapper.readValue(entity.getContent(), Set.class);

                for (Map<String, Object> metadata : currentPageRanking) {
                    RankingDTO rankingDTO = new RankingDTO();

                    rankingDTO.setSummonerId((String) metadata.get("summonerId"));
                    rankingDTO.setSummonerName((String) metadata.get("summonerName"));
                    rankingDTO.setLeaguePoints((Integer) metadata.get("leaguePoints"));
                    rankingDTO.setWins((Integer) metadata.get("wins"));
                    rankingDTO.setLosses((Integer) metadata.get("losses"));

                    summonerMapper.insertDiamond1(rankingDTO);
                    diamondRanking.add(rankingDTO);
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (currentPageRanking.isEmpty()) {
                    hasMoreData = false;
                } else {
                    page++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return diamondRanking;
    }

    /**
     * 다이아 2티어 랭킹 정보를 DB에 저장하는 메소드
     * @return 랭킹 정보 List
     */
    @Override
    public List<RankingDTO> diamond2Ranking(int start, int end) {

        List<RankingDTO> diamondRanking = new ArrayList<>();

        int page = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/entries/RANKED_SOLO_5x5/DIAMOND/II?page=" + page + "&api_key=" + mykey);

            HttpResponse response;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Set<Map<String, Object>> currentPageRanking;

            try {
                currentPageRanking = objectMapper.readValue(entity.getContent(), Set.class);

                for (Map<String, Object> metadata : currentPageRanking) {
                    RankingDTO rankingDTO = new RankingDTO();

                    rankingDTO.setSummonerId((String) metadata.get("summonerId"));
                    rankingDTO.setSummonerName((String) metadata.get("summonerName"));
                    rankingDTO.setLeaguePoints((Integer) metadata.get("leaguePoints"));
                    rankingDTO.setWins((Integer) metadata.get("wins"));
                    rankingDTO.setLosses((Integer) metadata.get("losses"));

                    summonerMapper.insertDiamond2(rankingDTO);
                    diamondRanking.add(rankingDTO);
                }

                if (currentPageRanking.isEmpty()) {
                    hasMoreData = false;
                } else {
                    page++;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return diamondRanking;
    }

    /**
     * 다이아 3티어 랭킹 정보를 DB에 저장하는 메소드
     * @return 랭킹 정보 List
     */
    @Override
    public List<RankingDTO> diamond3Ranking(int start, int end) {

        List<RankingDTO> diamondRanking = new ArrayList<>();

        int page = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/entries/RANKED_SOLO_5x5/DIAMOND/III?page=" + page + "&api_key=" + mykey);

            HttpResponse response;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Set<Map<String, Object>> currentPageRanking;

            try {
                currentPageRanking = objectMapper.readValue(entity.getContent(), Set.class);

                for (Map<String, Object> metadata : currentPageRanking) {
                    RankingDTO rankingDTO = new RankingDTO();

                    rankingDTO.setSummonerId((String) metadata.get("summonerId"));
                    rankingDTO.setSummonerName((String) metadata.get("summonerName"));
                    rankingDTO.setLeaguePoints((Integer) metadata.get("leaguePoints"));
                    rankingDTO.setWins((Integer) metadata.get("wins"));
                    rankingDTO.setLosses((Integer) metadata.get("losses"));

                    summonerMapper.insertDiamond3(rankingDTO);
                    diamondRanking.add(rankingDTO);
                }

                if (currentPageRanking.isEmpty()) {
                    hasMoreData = false;
                } else {
                    page++;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return diamondRanking;
    }

    /**
     * 다이아 4티어 랭킹 정보를 DB에 저장하는 메소드
     * @return 랭킹 정보 List
     */
    @Override
    public List<RankingDTO> diamond4Ranking(int start, int end) {

        List<RankingDTO> diamondRanking = new ArrayList<>();

        int page = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "league/v4/entries/RANKED_SOLO_5x5/DIAMOND/IV?page=" + page + "&api_key=" + mykey);

            HttpResponse response;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            Set<Map<String, Object>> currentPageRanking;

            try {
                currentPageRanking = objectMapper.readValue(entity.getContent(), Set.class);

                for (Map<String, Object> metadata : currentPageRanking) {
                    RankingDTO rankingDTO = new RankingDTO();

                    rankingDTO.setSummonerId((String) metadata.get("summonerId"));
                    rankingDTO.setSummonerName((String) metadata.get("summonerName"));
                    rankingDTO.setLeaguePoints((Integer) metadata.get("leaguePoints"));
                    rankingDTO.setWins((Integer) metadata.get("wins"));
                    rankingDTO.setLosses((Integer) metadata.get("losses"));

                    summonerMapper.insertDiamond4(rankingDTO);
                    diamondRanking.add(rankingDTO);
                }

                if (currentPageRanking.isEmpty()) {
                    hasMoreData = false;
                } else {
                    page++;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } catch (IOException e) {
                try {
                    throw new Exception(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return diamondRanking;
    }
}