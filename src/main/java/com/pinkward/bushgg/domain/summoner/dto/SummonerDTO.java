package com.pinkward.bushgg.domain.summoner.dto;

import lombok.Data;

@Data
public class SummonerDTO {
    private String accountId;
    private int profileIconId;
    private long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;
}
