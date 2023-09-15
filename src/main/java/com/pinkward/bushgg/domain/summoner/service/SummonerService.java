package com.pinkward.bushgg.domain.summoner.service;

import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;
import java.util.Set;


@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public interface SummonerService {

    SummonerTierDTO getTierInfo(Set<Map<String,Object>> summonerTier);
    public List<SummonerWithCount> getSummonerWith(Map<String, Object> matchInfo, int teamId, String name, List<SummonerWithCount> summonerWithCounts);
    public List<SummonerWithCount> sortSummonerWith(List<SummonerWithCount> summonerWithCounts);

    public String changeTierName(String tier);

}
