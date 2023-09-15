package com.pinkward.bushgg.domain.ranking.service;

import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;

import java.util.List;

public interface RankingService {

    public List<RankingDTO> ranking();

    public List<RankingDTO> challengerRanking(int start, int end);
}
