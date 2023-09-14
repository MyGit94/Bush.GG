package com.pinkward.bushgg.domain.ranking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChallengerRankingDTO {
    private String summonerId;
    private String summonerName;
}
