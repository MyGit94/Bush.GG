package com.pinkward.bushgg.domain.currentgame.dto;

import lombok.Data;

@Data
public class CurrentGameDTO {
    private int teamId;
    private int spell1Id;
    private int spell2Id;
    private int championId;
    private String championName;
    private String summonerName;
    private String summonerId;
    private String summonerTier;
}
