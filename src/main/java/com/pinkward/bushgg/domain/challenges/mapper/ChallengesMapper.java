package com.pinkward.bushgg.domain.challenges.mapper;

import com.pinkward.bushgg.domain.challenges.dto.ChallengeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ChallengesMapper {
    @Select("SELECT * FROM CHALLENGES WHERE CHALLENGEID = #{challengeId}")
    public ChallengeDTO getChallengeInfoById (int challengeId);
}
