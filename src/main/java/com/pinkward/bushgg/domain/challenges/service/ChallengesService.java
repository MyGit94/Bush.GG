package com.pinkward.bushgg.domain.challenges.service;

import com.pinkward.bushgg.domain.challenges.dto.ChallengeRankingPlayerDTO;
import com.pinkward.bushgg.domain.challenges.dto.ChallengesDTO;
import org.springframework.context.annotation.PropertySource;

import java.util.List;


@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public interface ChallengesService {
    public List<ChallengesDTO> allChallengesInfo();
    public List<ChallengeRankingPlayerDTO> challengeRanking(int challengeId);
}