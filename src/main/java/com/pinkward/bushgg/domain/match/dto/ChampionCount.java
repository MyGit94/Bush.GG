package com.pinkward.bushgg.domain.match.dto;

public class ChampionCount {
    private String championName;
    private int count;

    public ChampionCount(String championName) {
        this.championName = championName;
        this.count = 1; // 새로운 항목이므로 count를 1로 초기화
    }

    public String getChampionName() {
        return championName;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        this.count++;
    }
}
