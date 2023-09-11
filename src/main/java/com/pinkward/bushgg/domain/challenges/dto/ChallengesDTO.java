package com.pinkward.bushgg.domain.challenges.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class ChallengesDTO {
    private long id;
    private Map<String, LocalizedNameDTO> localizedNames;
    private boolean leaderboard;
    private Map<String, ThresholdsDTO> thresholds;
}
