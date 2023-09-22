package com.pinkward.bushgg.domain.currentgame.service;

import java.util.List;
import java.util.Map;

/**
 * 최근 게임 정보 가져오는 Service
 */
public interface CurrentGameService {

    public List<Map<String,Object>> getCurrentGameInfo(List<Map<String, Object>> currentGames);

}
