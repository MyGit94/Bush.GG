package com.pinkward.bushgg.domain.ranking.dto;

import lombok.Data;

/**
 * 유저 랭킹 관련 DTO
 */
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
