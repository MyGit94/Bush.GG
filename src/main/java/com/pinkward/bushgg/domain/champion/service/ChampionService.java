package com.pinkward.bushgg.domain.champion.service;

import java.util.List;

public interface ChampionService {
    public String getChampionNameKo(String championId);
    public String getChampionNameEn(String championId);

    // champion 로테이션 가져오기 (메인페이지 용)
    public List<Integer> championLotation();
    public String getChampionIdByName(String championName);
}
