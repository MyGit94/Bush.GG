package com.pinkward.bushgg.domain.champion.service;

import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;

import java.util.List;

public interface ChampionService {

    public List<Integer> championLotation();

    public List<ChampionCount> getChampionCounts (List<ChampionCount> championCounts, ParticipantsDTO participant);

    public List<ChampionCount> sortChampionCounts(List<ChampionCount> championCounts);
}
