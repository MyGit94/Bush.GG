package com.pinkward.bushgg.domain.champion.service;

import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;

import java.util.List;

/**
 *  Champion 관련 비즈니스 로직 처리
 */
public interface ChampionService {

    public List<Integer> championLotation();

    public List<ChampionCount> getChampionCounts (List<ChampionCount> championCounts, ParticipantsDTO participant);

    public List<ChampionCount> sortChampionCounts(List<ChampionCount> championCounts);
}
