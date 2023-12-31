package com.pinkward.bushgg.domain.api.service;

import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Riot Ko API에서 json 가져오는 Service
 */
public interface APIServiceKo {
    public SummonerDTO getSummonerInfo(String summonerName);
    public SummonerDTO getSummonerInfoByPuuid(String puuid);
    public PlayerChallengesInfoDTO getPlayerChallengesInfo(String puuid);
    public Set<Map<String,Object>> getTierInfo(String summonerId);
    public Map<String, Object> getCurrentGame(String summonerId);

}
