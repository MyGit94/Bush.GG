package com.pinkward.bushgg.domain.match.common;

import lombok.Data;

import java.util.Objects;

@Data
public class SummonerWithCount {
    private String summonerName;
    private int count;
    private int win;
    private int lose;
    private int winRate;

    // equals 메서드 오버라이드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummonerWithCount that = (SummonerWithCount) o;
        return Objects.equals(summonerName, that.summonerName);
    }

    // hashCode 메서드 오버라이드
    @Override
    public int hashCode() {
        return Objects.hash(summonerName);
    }
}
