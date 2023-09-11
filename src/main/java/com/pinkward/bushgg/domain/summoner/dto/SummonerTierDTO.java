package com.pinkward.bushgg.domain.summoner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class SummonerTierDTO {
    private String tier;
    private int leaguePoints;
    private int wins;
    private int losses;
    private int winRate;

}
