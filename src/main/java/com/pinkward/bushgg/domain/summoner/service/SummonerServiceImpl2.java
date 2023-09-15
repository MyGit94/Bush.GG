package com.pinkward.bushgg.domain.summoner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.common.TimeTranslator;
import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@Slf4j
public class SummonerServiceImpl2 implements SummonerService{

    public SummonerTierDTO getTierInfo(Set<Map<String,Object>> summonerTier) {
        SummonerTierDTO summonerTierDTO = new SummonerTierDTO();

            Map<String, Object> metadata = (Map<String, Object>) summonerTier.iterator().next();
            summonerTierDTO.setTier((String) metadata.get("tier"));
            summonerTierDTO.setLeaguePoints((int) metadata.get("leaguePoints"));
            summonerTierDTO.setWins((int) metadata.get("wins"));
            summonerTierDTO.setLosses((int) metadata.get("losses"));
            double winRate = ((double)summonerTierDTO.getWins() / (summonerTierDTO.getWins() + summonerTierDTO.getLosses())) * 100;
            summonerTierDTO.setWinRate((int)Math.round(winRate));

        return summonerTierDTO;
    }
    public List<SummonerWithCount> getSummonerWith(Map<String, Object> matchInfo, int teamId, String name, List<SummonerWithCount> summonerWithCounts){

        for (int i = 0; i < 10; i++) {
            ParticipantsDTO participant = (ParticipantsDTO) matchInfo.get("participants" + i);

            boolean found = false;

            if(teamId==participant.getTeamId() && !name.equals(participant.getSummonerName())) {

                for (SummonerWithCount summonerWithCount : summonerWithCounts) {
                    if (summonerWithCount.getSummonerName().equals(participant.getSummonerName())) {
                        // 존재하면 count와 win을 증가시킴
                        summonerWithCount.setCount(summonerWithCount.getCount() + 1);
                        if(participant.isWin()){
                            summonerWithCount.setWin(summonerWithCount.getWin() + 1);
                        } else {
                            summonerWithCount.setLose(summonerWithCount.getLose() + 1);
                        }
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // 소환사 이름이 리스트에 없으면 새 객체 생성하여 리스트에 추가
                    SummonerWithCount newSummonerWithCount = new SummonerWithCount();
                    newSummonerWithCount.setSummonerName(participant.getSummonerName());
                    newSummonerWithCount.setCount(1);
                    if (participant.isWin()) {
                        newSummonerWithCount.setWin(1);
                        newSummonerWithCount.setLose(0);
                    } else {
                        newSummonerWithCount.setWin(0); // 이긴 횟수를 초기화
                        newSummonerWithCount.setLose(1); // 진 횟수를 초기화
                    }
                    summonerWithCounts.add(newSummonerWithCount);
                }
            }
        }
      return summonerWithCounts;
    }

    public List<SummonerWithCount> sortSummonerWith(List<SummonerWithCount> summonerWithCounts) {

        Iterator<SummonerWithCount> iterator1 = summonerWithCounts.iterator();
        while (iterator1.hasNext()) {
            SummonerWithCount summonerWithCount = iterator1.next();
            if (summonerWithCount.getCount() == 1) {
                iterator1.remove();
            }
        }

        Collections.sort(summonerWithCounts, Comparator.comparingInt(SummonerWithCount::getCount).reversed());
        List<SummonerWithCount> filteredList = summonerWithCounts.subList(0, Math.min(5, summonerWithCounts.size()));

        for (SummonerWithCount summonerWithCount: filteredList) {
            double winRate = ((double)summonerWithCount.getWin() / summonerWithCount.getCount()) * 100;
            summonerWithCount.setWinRate((int)Math.round(winRate));
        }
        return filteredList;
    }
}