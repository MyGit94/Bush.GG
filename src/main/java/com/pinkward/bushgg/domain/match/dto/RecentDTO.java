package com.pinkward.bushgg.domain.match.dto;

import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import lombok.Data;

import java.util.List;

/**
 * 최근 20게임 승률 및 킬데스 관련 DTO
 */
@Data
public class RecentDTO {
    private int win;
    private int lose;
    private int winRate;

    private int kills;
    private int assists;
    private int deaths;
    private String kda;

    private List<ChampionCount> championCounts;
    private List<SummonerWithCount> summonerWithCounts;


}
