package com.pinkward.bushgg.domain.ranking.mapper;

import com.pinkward.bushgg.domain.ranking.dto.ChallengerRankingDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ChallengerMapper {
    @Insert("INSERT INTO CHALLENGER (SUMMONERID, SUMMONERNAME) VALUES (#{summonerId}, #{summonerName})")
    void insertChallenger(ChallengerRankingDTO challengerRankingDTO);

    @Delete("Delete FROM CHALLENGER")
    void deleteChallenger();
}
