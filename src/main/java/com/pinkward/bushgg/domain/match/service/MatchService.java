package com.pinkward.bushgg.domain.match.service;

import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.match.dto.RecentDTO;

import java.util.List;
import java.util.Map;

public interface MatchService {
    // matchInfo로 상세정보 가져오기
    public MatchInfoDTO getMatchInfo(Map<String, Object> matchInfo);


}
