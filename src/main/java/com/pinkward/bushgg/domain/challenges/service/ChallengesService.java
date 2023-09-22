package com.pinkward.bushgg.domain.challenges.service;

import com.pinkward.bushgg.domain.challenges.dto.ChallengeRankingPlayerDTO;
import com.pinkward.bushgg.domain.challenges.dto.ChallengesDTO;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * Challenge 관련 비즈니스 로직 처리
 */
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.yml")
public interface ChallengesService {
    List<ChallengesDTO> allChallengesInfo();
    List<ChallengeRankingPlayerDTO> challengeRanking(int challengeId);
}
