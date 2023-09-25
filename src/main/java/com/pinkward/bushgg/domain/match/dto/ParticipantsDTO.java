package com.pinkward.bushgg.domain.match.dto;

import lombok.Data;

/**
 * 게임 경기 참가자 정보 DTO
 */
@Data
public class ParticipantsDTO {

    private String summonerName;
    private String summonerId;
    private int champLevel;
    private int championId;
    private String championName;
    private int teamId;
    private boolean win;
    private String tier;

    private int assists;
    private int deaths;
    private int kills;
    private String kda;

    private int goldEarned;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;

    private int largestMultiKill;
    private int summoner1Id;
    private int summoner2Id;
    private int totalDamageDealtToChampions;
    private int totalDamageTaken;
    private int totalMinionsKilled;
    private int neutralMinionsKilled;
    private double minionsKilledMin;

    private int visionScore;
    private int wardsKilled;
    private int wardsPlaced;

    private int defense;
    private int flex;
    private int offense;
    private int mainRune;
    private int subRune;
    private int mainRune1;
    private int mainRune2;
    private int mainRune3;
    private int mainRune4;
    private int subRune1;
    private int subRune2;

    private int placement;
    private int playerAugment1;
    private int playerAugment2;
    private int playerAugment3;
    private int playerAugment4;

}
