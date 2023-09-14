package com.pinkward.bushgg.domain.champion.service;

import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;

import java.util.List;

public interface ChampionService {
    public String getChampionNameKo(String championId);
    public String getChampionNameEn(String championId);

    // champion 로테이션 가져오기 (메인페이지 용)
    public List<Integer> championLotation();
    public String getChampionIdByName(String championName);

    public List<ChampionCount> getChampionCounts (List<ChampionCount> championCounts, ParticipantsDTO participant);

    public List<ChampionCount> sortChampionCounts(List<ChampionCount> championCounts);
}
