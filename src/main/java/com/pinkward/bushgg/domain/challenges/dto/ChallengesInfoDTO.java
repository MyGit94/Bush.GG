package com.pinkward.bushgg.domain.challenges.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저의 달성 도전과제 리스트 
 */
@Data
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"achievedTime"}, ignoreUnknown = true)
public class ChallengesInfoDTO {
    private int challengeId;
    private double percentile;
    private String level;
    private int value;
}
