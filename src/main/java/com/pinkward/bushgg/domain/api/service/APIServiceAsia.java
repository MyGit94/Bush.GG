package com.pinkward.bushgg.domain.api.service;

import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Asia API에서 json 가져오는 Service
 */
public interface APIServiceAsia {


    public List<String> getMatchId(String puuid);

    public Map<String, Object> getMatch(String matchId);

}
