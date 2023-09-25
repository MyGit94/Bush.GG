package com.pinkward.bushgg.domain.challenges.dto;

import lombok.Data;

/**
 * 도전과제 순위 DTO
 */
@Data
public class ChallengeRankingPlayerDTO {
    private int position;
    private String puuid;
    private double value;
    private String name;
    private int profileIcon;
}
