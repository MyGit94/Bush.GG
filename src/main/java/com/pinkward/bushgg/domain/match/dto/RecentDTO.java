package com.pinkward.bushgg.domain.match.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecentDTO {
    private int win;
    private int lose;
    private int winRate;

    private int kills;
    private int assists;
    private int deaths;
    private String kda;

    private List<com.pinkward.bushgg.domain.match.common.ChampionCount> championCounts;
    private List<com.pinkward.bushgg.domain.match.common.SummonerWithCount> summonerWithCounts;


}
