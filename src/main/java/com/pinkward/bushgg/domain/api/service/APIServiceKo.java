package com.pinkward.bushgg.domain.api.service;

import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Ko API에서 json 가져오는 Service
 */
public interface APIServiceKo {
    // 소환사정보
    public SummonerDTO getSummonerInfo(String summonerName);

    public PlayerChallengesInfoDTO getPlayerChallengesInfo(String puuid);

    public Set<Map<String,Object>> getTierInfo(String summonerId);

}
