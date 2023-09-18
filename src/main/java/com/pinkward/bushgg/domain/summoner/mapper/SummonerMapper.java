package com.pinkward.bushgg.domain.summoner.mapper;

import com.pinkward.bushgg.domain.ranking.dto.RankingDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface SummonerMapper {

    @Delete("Delete From TIER")
    void deleteTier();

    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('CHALLENGER', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertChallenger(RankingDTO rankingDTO);

    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('GRANDMASTER', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertGrandmaster(RankingDTO rankingDTO);

    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('MASTER', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertMaster(RankingDTO rankingDTO);

    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('DIAMOND 1', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertDiamond1(RankingDTO rankingDTO);

    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('DIAMOND 2', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertDiamond2(RankingDTO rankingDTO);

    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('DIAMOND 3', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertDiamond3(RankingDTO rankingDTO);

    @Transactional
    @Insert("Insert into TIER (TIER, SUMMONERID, SUMMONERNAME, WINS, LOSSES, LEAGUEPOINTS)" +
            "VALUES ('DIAMOND 4', #{summonerId}, #{summonerName}, #{wins}, #{losses}, #{leaguePoints})")
    void insertDiamond4(RankingDTO rankingDTO);
}
