package com.pinkward.bushgg.domain.summoner.dto;

import lombok.Data;

@Data
public class SummonerTierDTO {
    private String tier;
    private String tierName;
    private int leaguePoints;
    private int wins;
    private int losses;
    private int winRate;
}
