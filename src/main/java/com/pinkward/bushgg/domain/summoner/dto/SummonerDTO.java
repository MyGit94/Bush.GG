package com.pinkward.bushgg.domain.summoner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerDTO {
    private int profileIconId;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;
}
