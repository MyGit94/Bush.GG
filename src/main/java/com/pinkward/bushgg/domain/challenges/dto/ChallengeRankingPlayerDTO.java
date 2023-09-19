package com.pinkward.bushgg.domain.challenges.dto;

import lombok.Data;

@Data
public class ChallengeRankingPlayerDTO {
    private int position;
    private String puuid;
    private double value;
    private String name;
    private int profileIcon;
}
