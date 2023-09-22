package com.pinkward.bushgg.domain.ranking.service;

import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;

import java.util.List;

/**
 * Tier 정보를 DB에 넣기 위한 Service
 */
public interface RankingAPIService {

    public List<RankingDTO> challengerRanking(int start, int end);
    public List<RankingDTO> grandMasterRanking(int start, int end);
    public List<RankingDTO> masterRanking(int start, int end);
    public List<RankingDTO> diamond1Ranking(int start, int end);
    public List<RankingDTO> diamond2Ranking(int start, int end);
    public List<RankingDTO> diamond3Ranking(int start, int end);
    public List<RankingDTO> diamond4Ranking(int start, int end);
}
