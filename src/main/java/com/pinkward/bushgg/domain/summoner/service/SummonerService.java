package com.pinkward.bushgg.domain.summoner.service;

import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.match.dto.SummonerTierDTO;
import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;


@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public interface SummonerService {

    // 소환자이름으로 소환사정보 가져오기
    public SummonerDTO summonerInfo(String summonerName);


    // puuid로 matchId list 가져오기
    public List<String> getMatchId(String puuid);

    // matchId로 해당 match Map으로 가져오기
    public Map<String, Object> getMatch(String matchId);

    // match에서 필요한 데이터만 Map으로 가져오기
    public Map<String, Object> matchInfo(Map<String, Object> match);

    // 소환사의 티어정보 가져오기
    public SummonerTierDTO getTierInfo(String summonerId);

    // champion 로테이션 가져오기 (메인페이지 용)
    public List<Integer> championLotation();

    // 랭킹 순위 가져오기
    public List<RankingDTO> ranking();

    // puuid로 유저 challenges list가져오기
    public PlayerChallengesInfoDTO getPlayerChallengesInfo(String puuid);


}
