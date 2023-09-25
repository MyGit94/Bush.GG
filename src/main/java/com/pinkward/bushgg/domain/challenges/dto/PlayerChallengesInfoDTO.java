package com.pinkward.bushgg.domain.challenges.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 유저의 달성 도전과제 점수 DTO
 */
@Data
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"preferences"}, ignoreUnknown = true)
public class PlayerChallengesInfoDTO {
    private ChallengePoints totalPoints;
    private Map<String, ChallengePoints> categoryPoints;
    private List<ChallengesInfoDTO> challenges;
}
