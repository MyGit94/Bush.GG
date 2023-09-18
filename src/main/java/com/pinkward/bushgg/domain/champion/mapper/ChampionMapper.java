package com.pinkward.bushgg.domain.champion.mapper;

import com.pinkward.bushgg.domain.challenges.dto.ChallengeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChampionMapper {


    @Select("SELECT championNameKo FROM CHAMPION WHERE CHAMPIONID = #{championId}")
    public String getChampionKoName(int championId);

    @Select("SELECT championNameEn FROM CHAMPION WHERE CHAMPIONID = #{championId}")
    public String getChampionEnName(int championId);

}
