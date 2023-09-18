package com.pinkward.bushgg.domain.ranking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public class RankingServiceImpl implements RankingService{

    private ObjectMapper objectMapper = new ObjectMapper();
    private final RankingAPIService rankingAPIService;


//    @Value("${riot.ranking.key}")
//    private String mykey;

    @Value("${riot.api.key}")
    private String mykey;
    String serverUrl = "https://kr.api.riotgames.com/lol/";

    @Override
    public List<RankingDTO> challengerRanking(int start, int end, List<Map<String, Object>> entries) {

        List<RankingDTO> challengerRanking = new ArrayList<>();

            String tier = "CHALLENGER";

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
                double winRate = ((double)rankingDTO.getWins() / (rankingDTO.getWins() + rankingDTO.getLosses())) * 100;
                rankingDTO.setWins((int)Math.round(winRate));

                challengerRanking.add(rankingDTO);
            }

        return challengerRanking;
    }

    @Override
    public List<RankingDTO> grandMasterRanking(int start, int end, List<Map<String, Object>> entries) {

        List<RankingDTO> challengerRanking = new ArrayList<>();

        String tier = "GRANDMASTER";

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
            double winRate = ((double)rankingDTO.getWins() / (rankingDTO.getWins() + rankingDTO.getLosses())) * 100;
            rankingDTO.setWins((int)Math.round(winRate));

            challengerRanking.add(rankingDTO);
        }

        return challengerRanking;
    }

    @Override
    public int challengerPoint(List<Map<String, Object>> entries) {
        int challengerPoint = 0;
        entries.sort((entry1, entry2) -> {
            Integer lp1 = (Integer) entry1.get("leaguePoints");
            Integer lp2 = (Integer) entry2.get("leaguePoints");
            return lp1.compareTo(lp2);
        });
        Map<String, Object> entry = entries.get(0);
        challengerPoint = ((Integer) entry.get("leaguePoints"));
        return challengerPoint;
    }

    @Override
    public int grandmasterPoint(List<Map<String, Object>> entries) {
        int grandmasterPoint = 0;
        entries.sort((entry1, entry2) -> {
            Integer lp1 = (Integer) entry1.get("leaguePoints");
            Integer lp2 = (Integer) entry2.get("leaguePoints");
            return lp1.compareTo(lp2);
        });
        Map<String, Object> entry = entries.get(0);
        grandmasterPoint = ((Integer) entry.get("leaguePoints"));
        return grandmasterPoint;
    }
}
