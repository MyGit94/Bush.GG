package com.pinkward.bushgg.domain.currentgame.dto;

import lombok.Data;

/**
 * 실시간 게임의 참가자 정보 DTO
 */
@Data
public class CurrentGameDTO {
    private int teamId;
    private int spell1Id;
    private int spell2Id;
    private String championName;
    private String summonerName;
    private String summonerId;
    private String summonerTier;
}
