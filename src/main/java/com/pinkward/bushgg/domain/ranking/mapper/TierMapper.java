package com.pinkward.bushgg.domain.ranking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Tier 관련 SQL Mapper
 */
@Mapper
public interface TierMapper {

    /** 티어가 CHALLENGER인 유저아이디 반환 */
    @Select("SELECT summonerId FROM TIER WHERE TIER = 'CHALLENGER'")
    public List<String> getChallengerInfo();

    /** 유저아이디로 유저 티어 반환*/
    @Select("SELECT tier FROM TIER WHERE SUMMONERID = #{summonerId}")
    public String getTierById(String summonerId);

    /** 유저 아이디로 유저 닉네임 반환 */
    @Select("SELECT summonerName FROM TIER WHERE SUMMONERID = #{summonerId}")
    public String getNameById(String summonerId);


}
