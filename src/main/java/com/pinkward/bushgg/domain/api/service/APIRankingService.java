package com.pinkward.bushgg.domain.api.service;

import java.util.List;
import java.util.Map;

public interface APIRankingService {
    public List<Map<String, Object>> challengerRanking();

    public List<Map<String, Object>> grandMasterRanking();
}
