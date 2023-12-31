package com.pinkward.bushgg.domain.summoner.service;

import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Summoner 관련 비즈니스 로직 처리
 */
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public interface SummonerService {

    SummonerTierDTO getTierInfo(Set<Map<String,Object>> summonerTier);
    List<SummonerWithCount> getSummonerWith(Map<String, Object> matchInfo, int teamId, String name, List<SummonerWithCount> summonerWithCounts);
    List<SummonerWithCount> sortSummonerWith(List<SummonerWithCount> summonerWithCounts);
    public String changeTierName(String tier);
    public String changeRank(String rank);

}
