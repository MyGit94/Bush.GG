package com.pinkward.bushgg.domain.summoner.dto;

import lombok.Data;

/**
 * 유저 티어 관련 DTO
 */
@Data
public class SummonerTierDTO {
    private String tier;
    private String tierName;
    private int leaguePoints;
    private int wins;
    private int losses;
    private int winRate;
}
