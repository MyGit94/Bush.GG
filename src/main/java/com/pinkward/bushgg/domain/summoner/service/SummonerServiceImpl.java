package com.pinkward.bushgg.domain.summoner.service;

import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SummonerServiceImpl implements SummonerService{

    public SummonerTierDTO getTierInfo(Set<Map<String,Object>> summonerTier) {
        SummonerTierDTO summonerTierDTO = new SummonerTierDTO();
        if(summonerTier == null) {
            summonerTierDTO.setTier(null);
        } else {
            String tier = "CHALLENGER";
            String tier1 = "GRANDMASTER";
            String tier2 ="MASTER";
            Map<String, Object> metadata = (Map<String, Object>) summonerTier.iterator().next();
            if(tier.equals((String)metadata.get("tier")) || tier1.equals((String)metadata.get("tier")) ||tier2.equals((String)metadata.get("tier"))){
                summonerTierDTO.setTier((String) metadata.get("tier"));
                summonerTierDTO.setTierName((String) metadata.get("tier"));
            } else{
                summonerTierDTO.setTier((String) metadata.get("tier"));
                summonerTierDTO.setTierName((String) metadata.get("tier")+' '+changeRank((String) metadata.get("rank")));
            }

            summonerTierDTO.setLeaguePoints((int) metadata.get("leaguePoints"));
            summonerTierDTO.setWins((int) metadata.get("wins"));
            summonerTierDTO.setLosses((int) metadata.get("losses"));
            double winRate = ((double)summonerTierDTO.getWins() / (summonerTierDTO.getWins() + summonerTierDTO.getLosses())) * 100;
            summonerTierDTO.setWinRate((int)Math.round(winRate));
        }




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

    @Override
    public String changeTierName(String tier) {
        if (tier == null) {
            return null;
        }
        switch (tier) {
            case "CHALLENGER" :
                return "C";
            case "GRANDMASTER":
                return "GM";
            case "MASTER":
                return "M";
            case "DIAMOND 1":
                return "D1";
            case "DIAMOND 2":
                return "D2";
            case "DIAMOND 3":
                return "D3";
            case "DIAMOND 4":
                return "D4";
            case "EMERALD 1":
                return "E1";
            case "EMERALD 2":
                return "E2";
            case "EMERALD 3":
                return "E3";
            case "EMERALD 4":
                return "E4";
            case "PLATINUM 1":
                return "P1";
            case "PLATINUM 2":
                return "P2";
            case "PLATINUM 3":
                return "P3";
            case "PLATINUM 4":
                return "P4";
            default:
                return "알 수 없음";
        }
    }
    @Override
    public String changeRank(String rank) {
        if (rank == null) {
            return null;
        }
        switch (rank) {
            case "I":
                return "1";
            case "II":
                return "2";
            case "III":
                return "3";
            case "IV":
                return "4";
            default:
                return "알수없음";
        }
    }
}
