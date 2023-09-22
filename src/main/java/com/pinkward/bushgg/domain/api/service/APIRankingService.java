package com.pinkward.bushgg.domain.api.service;

import java.util.List;
import java.util.Map;

/**
 * Riot API로 상위 티어 랭킹 가져오는 Service
 */
public interface APIRankingService {
    public List<Map<String, Object>> challengerRanking();

    public List<Map<String, Object>> grandMasterRanking();
}