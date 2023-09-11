package com.pinkward.bushgg.domain.match.service;

import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.common.RuneList;
import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.common.TimeTranslator;
import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.match.dto.RecentDTO;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class MatchServiceImpl implements MatchService {

    @Override
    public MatchInfoDTO getMatchInfo(Map<String, Object> matchInfo) {
        MatchInfoDTO matchInfoDTO = (MatchInfoDTO) matchInfo.get("matchInfo");

        matchInfoDTO.setChangeGameDuration(TimeTranslator.unixMinAndSec(matchInfoDTO.getGameDuration()));
        matchInfoDTO.setEndGame(TimeTranslator.unixToLocal(matchInfoDTO.getGameEndTimestamp()));

        return matchInfoDTO;
    }

}
