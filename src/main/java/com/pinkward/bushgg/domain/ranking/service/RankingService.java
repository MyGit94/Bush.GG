package com.pinkward.bushgg.domain.ranking.service;

import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;

import java.util.List;

public interface RankingService {

    List<RankingDTO> ranking();

    List<RankingDTO> challengerRanking(int start, int end);
}
