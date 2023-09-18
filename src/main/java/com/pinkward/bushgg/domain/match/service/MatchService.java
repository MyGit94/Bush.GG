package com.pinkward.bushgg.domain.match.service;

import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.match.dto.RecentDTO;

import java.util.List;
import java.util.Map;

public interface MatchService {

    MatchInfoDTO matchInfoDTO(Map<String, Object> match);
    Map<String, Object> matchInfo(Map<String, Object> match);

    MatchInfoDTO getMatchInfoDTO(MatchInfoDTO matchInfoDTO, Map<String, Object> matchInfo);
}
