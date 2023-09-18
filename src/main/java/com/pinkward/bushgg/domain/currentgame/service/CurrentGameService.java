package com.pinkward.bushgg.domain.currentgame.service;

import java.util.List;
import java.util.Map;

public interface CurrentGameService {
    List<String> getSummonerName();
    String getSummonerId(String summonerName);
    Map<String, Object> getCurrentGame(String summonerId);
    List<Map<String,Object>> getCurrentGameInfo(List<Map<String, Object>> currentGames);

}
