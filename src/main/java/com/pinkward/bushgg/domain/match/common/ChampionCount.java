package com.pinkward.bushgg.domain.match.common;

import lombok.Data;

/**
 * 챔피언 승률 클래스
 */
@Data
public class ChampionCount {
    private String championName;
    private int count;
    private int win;
    private int winRate;
}
