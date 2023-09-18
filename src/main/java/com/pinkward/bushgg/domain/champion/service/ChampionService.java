package com.pinkward.bushgg.domain.champion.service;

import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;

import java.util.List;

public interface ChampionService {
    String getChampionNameKo(String championId);
    String getChampionNameEn(String championId);

    // champion 로테이션 가져오기 (메인페이지 용)
    List<Integer> championLotation();
    String getChampionIdByName(String championName);

    List<ChampionCount> getChampionCounts(List<ChampionCount> championCounts, ParticipantsDTO participant);

    List<ChampionCount> sortChampionCounts(List<ChampionCount> championCounts);
}
