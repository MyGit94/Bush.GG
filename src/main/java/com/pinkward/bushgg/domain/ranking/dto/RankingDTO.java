package com.pinkward.bushgg.domain.ranking.dto;

import lombok.Data;

@Data
public class RankingDTO {
    private String summonerId;
    private String tier;
    private String summonerName;
    private int leaguePoints;
    private int wins;
    private int losses;
    private int winRate;

}
