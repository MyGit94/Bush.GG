package com.pinkward.bushgg.domain.match.service;

import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.match.dto.RecentDTO;

import java.util.List;
import java.util.Map;

public interface MatchService {

    public MatchInfoDTO matchInfoDTO(Map<String, Object> match);
    Map<String, Object> matchInfo(Map<String, Object> match);

    public MatchInfoDTO getMatchInfoDTO(MatchInfoDTO matchInfoDTO, Map<String, Object> matchInfo);

    public String matchQueueName(int queueId);
}
