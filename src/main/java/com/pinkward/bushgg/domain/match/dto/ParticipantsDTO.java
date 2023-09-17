package com.pinkward.bushgg.domain.match.dto;

import lombok.Data;

@Data
public class ParticipantsDTO {

    // 유저 및 팀
    private String summonerName;
    private String summonerId;
    private int champLevel;
    private int championId;
    private String championName;
    private int teamId;
    private boolean win;
    private String tier;

    // KDA관련
    private int assists;
    private int deaths;
    private int kills;
    private String kda;

    // 아이템, 돈
    private int goldEarned;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;

    //최대킬
    private int largestMultiKill;

    //스펠
    private int summoner1Id;
    private int summoner2Id;

    // 데미지
    private int totalDamageDealtToChampions;
    private int totalDamageTaken;

    // 미니언
    private int totalMinionsKilled;
    private int neutralMinionsKilled;
    private double minionsKilledMin;

    // 와드
    private int visionScore;
    private int wardsKilled;
    private int wardsPlaced;

    //룬
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

    // 아레나
    private int placement;
    private int playerAugment1;
    private int playerAugment2;
    private int playerAugment3;
    private int playerAugment4;

}
