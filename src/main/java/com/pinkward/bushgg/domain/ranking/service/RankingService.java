package com.pinkward.bushgg.domain.ranking.service;

import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;

import java.util.List;
import java.util.Map;

public interface RankingService {

    public List<RankingDTO> challengerRanking(int start, int end, List<Map<String, Object>> entries);

    public List<RankingDTO> grandMasterRanking(int start, int end, List<Map<String, Object>> entries);

    public int challengerPoint(List<Map<String, Object>> entries);
    public int grandmasterPoint(List<Map<String, Object>> entries);
}
