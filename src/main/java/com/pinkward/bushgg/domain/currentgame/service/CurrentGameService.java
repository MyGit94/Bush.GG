package com.pinkward.bushgg.domain.currentgame.service;

import java.util.List;
import java.util.Map;

public interface CurrentGameService {
    public List<String> getSummonerName();
    public String getSummonerId(String summonerName);
    public Map<String, Object> getCurrentGame(String summonerId);
    public List<Map<String,Object>> getCurrentGameInfo(List<Map<String, Object>> currentGames);

}
