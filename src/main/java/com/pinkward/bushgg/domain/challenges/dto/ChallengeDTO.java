package com.pinkward.bushgg.domain.challenges.dto;

import lombok.Data;

/**
 * 도전과제 정보 DTO
 */
@Data
public class ChallengeDTO {
    private int challengeId;
    private String name;
    private String description;
    private String shortDescription;
}
