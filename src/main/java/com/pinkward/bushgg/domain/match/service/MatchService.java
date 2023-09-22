package com.pinkward.bushgg.domain.match.service;

import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import java.util.Map;

/**
 * Match 관련 비즈니스 로직 처리
 */
public interface MatchService {

    public MatchInfoDTO matchInfoDTO(Map<String, Object> match);
    Map<String, Object> matchInfo(Map<String, Object> match);
    public MatchInfoDTO getMatchInfoDTO(MatchInfoDTO matchInfoDTO, Map<String, Object> matchInfo);
    public String matchQueueName(int queueId);
}
