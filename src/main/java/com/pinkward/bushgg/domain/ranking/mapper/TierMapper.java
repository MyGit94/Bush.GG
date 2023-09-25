package com.pinkward.bushgg.domain.ranking.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Tier 관련 SQL Mapper
 */
@Mapper
public interface TierMapper {
    @Insert("INSERT INTO CHALLENGER (SUMMONERID, SUMMONERNAME) VALUES (#{summonerId}, #{summonerName})")
    void insertChallenger(ChallengerRankingDTO challengerRankingDTO);

    @Delete("Delete FROM CHALLENGER")
    void deleteChallenger();

    @Select("SELECT summonerId FROM TIER WHERE TIER = 'CHALLENGER'")
    public List<String> getChallengerInfo();

    @Select("SELECT tier FROM TIER WHERE SUMMONERID = #{summonerId}")
    public String getTierById(String summonerId);

    @Select("SELECT summonerName FROM TIER WHERE SUMMONERID = #{summonerId}")
    public String getNameById(String summonerId);


}
