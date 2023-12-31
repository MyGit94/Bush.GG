package com.pinkward.bushgg.domain.match.common;

import lombok.Data;
import java.util.Objects;

/**
 * 함께 플레이한 유저와의 승률 클래스
 */
@Data
public class SummonerWithCount {
    private String summonerName;
    private int count;
    private int win;
    private int lose;
    private int winRate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummonerWithCount that = (SummonerWithCount) o;
        return Objects.equals(summonerName, that.summonerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summonerName);
    }
}
