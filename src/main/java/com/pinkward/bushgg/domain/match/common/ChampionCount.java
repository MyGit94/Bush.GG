package com.pinkward.bushgg.domain.match.common;

import lombok.Data;

@Data
public class ChampionCount {
    private String championName;
    private int count;
    private int win;
    private int winRate;
}
