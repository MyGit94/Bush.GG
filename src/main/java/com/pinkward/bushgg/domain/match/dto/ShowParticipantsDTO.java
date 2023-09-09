package com.pinkward.bushgg.domain.match.dto;

import lombok.Data;

@Data
public class ShowParticipantsDTO {
    private int championId;
    private String championName;
    private String summonerName;
    private int summoner1Id;
    private int summoner2Id;
    private int mainRune1;
    private int subRune;
    private int assists;
    private int deaths;
    private int kills;

}
