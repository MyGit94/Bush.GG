package com.pinkward.bushgg.domain.summoner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class SummonerTierDTO {
    private String tier; // JSON 데이터와 일치하게 문자열로 정의
    private int leaguePoints;
    private int wins;
    private int losses;
    private int winRate;
}
