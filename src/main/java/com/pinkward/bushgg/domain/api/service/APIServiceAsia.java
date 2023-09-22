package com.pinkward.bushgg.domain.api.service;

import java.util.List;
import java.util.Map;

/**
 * Riot Asia API에서 Match 관련 json 가져오는 Service
 */
public interface APIServiceAsia {

    public List<String> getMatchId(String puuid);

    public Map<String, Object> getMatch(String matchId);

}
