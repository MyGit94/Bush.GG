package com.pinkward.bushgg.domain.match.dto;

import lombok.Data;

@Data
public class MatchInfoDTO {

    // 게임정보
    private String matchId;
    private int gameDuration;
    private String changeGameDuration;
    private String endGame;
    private long gameEndTimestamp;
    private int queueId;
    private String queueName;

    // blue팀 정보
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

    // red팀 정보
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
