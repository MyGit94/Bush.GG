package com.pinkward.bushgg.domain.summoner.mapper;

import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tier 정보를 DB에 넣기 위한 SQL Mapper
 */
@Mapper
public interface SummonerMapper {
    /** 티어 테이블 삭제 */
    @Delete("Delete From TIER")
    void deleteTier();

    /** 챌린저 티어인 유저 정보 저장 */
    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('CHALLENGER', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertChallenger(RankingDTO rankingDTO);

    /** 그랜드마스터 티어인 유저 정보 저장 */
    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('GRANDMASTER', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertGrandmaster(RankingDTO rankingDTO);

    /** 마스터 티어인 유저 정보 저장 */
    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('MASTER', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertMaster(RankingDTO rankingDTO);

    /** 다이아 1 티어인 유저 정보 저장 */
    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('DIAMOND 1', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertDiamond1(RankingDTO rankingDTO);

    /** 다이아 2 티어인 유저 정보 저장 */
    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('DIAMOND 2', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertDiamond2(RankingDTO rankingDTO);

    /** 다이아 3 티어인 유저 정보 저장 */
    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('DIAMOND 3', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertDiamond3(RankingDTO rankingDTO);

    /** 다이아 4 티어인 유저 정보 저장 */
    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('DIAMOND 4', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertDiamond4(RankingDTO rankingDTO);
}
