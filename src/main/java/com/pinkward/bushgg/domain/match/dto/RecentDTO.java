package com.pinkward.bushgg.domain.match.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RecentDTO {
    private int win;
    private int lose;
    private int winRate;

    private int kills;
    private int assists;
    private int deaths;
    private String kda;

    private Map<String, Integer> recentChampion = new HashMap<>();
    private Map<String, Integer> recentChampionWin = new HashMap<>();

    private List<ChampionCount> championCounts;


}
