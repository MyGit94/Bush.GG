package com.pinkward.bushgg.domain.match.dto;

import lombok.Data;

/**
 * 게임 경기 관련 정보 DTO
 */
@Data
public class MatchInfoDTO {

    private String matchId;
    private int gameDuration;
    private String changeGameDuration;
    private String endGame;
    private long gameEndTimestamp;
    private int queueId;
    private String queueName;

    private int blueBaron;
    private int blueKills;
    private int blueDragon;
    private int blueTowerKills;
    private boolean blueWin;
    private int blueGold;
    private int blueTotalDamageDealtToChampions;
    private int blueWardsPlaced;
    private int blueTotalDamageTaken;
    private int blueTotalMinionsKilled;

    private int redBaron;
    private int redKills;
    private int redDragon;
    private int redTowerKills;
    private boolean redWin;
    private int redGold;
    private int redTotalDamageDealtToChampions;
    private int redWardsPlaced;
    private int redTotalDamageTaken;
    private int redTotalMinionsKilled;
}
