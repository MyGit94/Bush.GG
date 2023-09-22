package com.pinkward.bushgg.domain.challenges.mapper;

import com.pinkward.bushgg.domain.challenges.dto.ChallengeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * challengeId로 challenge 정보를 제공 받는 Mapper
 */
@Mapper
public interface ChallengesMapper {
    @Select("SELECT * FROM CHALLENGES WHERE CHALLENGEID = #{challengeId}")
    ChallengeDTO getChallengeInfoById(int challengeId);
}
