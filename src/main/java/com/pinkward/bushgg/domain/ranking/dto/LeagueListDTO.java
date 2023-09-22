package com.pinkward.bushgg.domain.ranking.dto;

import lombok.Data;
import java.util.List;

@Data
public class LeagueListDTO {
    private String leagueId;
    private List<ChallengerRankingDTO> entries;
    private String tier;
    private String name;
    private String queue;
}
