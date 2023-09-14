package com.pinkward.bushgg.domain.challenges.dto;

import lombok.Data;

@Data
public class ChallengeDTO {
    private int challengeId;
    private String name;
    private String description;
    private String shortDescription;
}
