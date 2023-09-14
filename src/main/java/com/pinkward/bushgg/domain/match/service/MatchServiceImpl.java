package com.pinkward.bushgg.domain.match.service;

import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.common.RuneList;
import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.common.TimeTranslator;
import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.match.dto.RecentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    @Override
    public MatchInfoDTO matchInfoDTO(Map<String, Object> match) {
        MatchInfoDTO matchInfoDTO = new MatchInfoDTO();

        Map<String, Object> metadata = (Map<String, Object>) match.get("metadata");

        matchInfoDTO.setMatchId((String)metadata.get("matchId"));

        Map<String, Object> info = (Map<String, Object>) match.get("info");

        // DTO 만들었다 넣자..
        matchInfoDTO.setGameCreation((long)info.get("gameCreation"));
        matchInfoDTO.setGameDuration((Integer) info.get("gameDuration"));
        matchInfoDTO.setGameEndTimestamp((Long) info.get("gameEndTimestamp"));
        matchInfoDTO.setQueueId((int) info.get("queueId"));
        matchInfoDTO.setGameStartTimestamp((Long) info.get("gameStartTimestamp"));
        matchInfoDTO.setGameVersion((String) info.get("gameVersion"));

        List<Map<String, Object>> teams = (List<Map<String, Object>>) info.get("teams");

        Map<String, Object> blueTeam = teams.get(0);
        Map<String, Object> redTeam = teams.get(1);

        // blueTeam
        Map<String, Object> blueTeamObjectives = (Map<String, Object>) blueTeam.get("objectives");

        Map<String, Object> baron = (Map<String, Object>) blueTeamObjectives.get("baron");
        matchInfoDTO.setBlueBaron((int) baron.get("kills"));

        Map<String, Object> champion = (Map<String, Object>) blueTeamObjectives.get("champion");
        matchInfoDTO.setBlueKills((int) champion.get("kills"));

        Map<String, Object> dragon = (Map<String, Object>) blueTeamObjectives.get("dragon");
        matchInfoDTO.setBlueDragon((int) dragon.get("kills"));

        Map<String, Object> riftHerald = (Map<String, Object>) blueTeamObjectives.get("riftHerald");
        matchInfoDTO.setBlueRiftHerald((int) riftHerald.get("kills"));

        Map<String, Object> tower = (Map<String, Object>) blueTeamObjectives.get("tower");
        matchInfoDTO.setBlueTowerKills((int) tower.get("kills"));

        matchInfoDTO.setBlueWin((boolean) blueTeam.get("win"));

        // redTeam
        Map<String, Object> redTeamObjectives = (Map<String, Object>) redTeam.get("objectives");

        baron = (Map<String, Object>) redTeamObjectives.get("baron");
        matchInfoDTO.setRedBaron((int) baron.get("kills"));

        champion = (Map<String, Object>) redTeamObjectives.get("champion");
        matchInfoDTO.setRedKills((int) champion.get("kills"));

        dragon = (Map<String, Object>) redTeamObjectives.get("dragon");
        matchInfoDTO.setRedDragon((int) dragon.get("kills"));

        riftHerald = (Map<String, Object>) redTeamObjectives.get("riftHerald");
        matchInfoDTO.setRedRiftHerald((int) riftHerald.get("kills"));

        tower = (Map<String, Object>) redTeamObjectives.get("tower");
        matchInfoDTO.setRedTowerKills((int) tower.get("kills"));

        matchInfoDTO.setRedWin((boolean) redTeam.get("win"));


        matchInfoDTO.setChangeGameDuration(TimeTranslator.unixMinAndSec(matchInfoDTO.getGameDuration()));
        matchInfoDTO.setEndGame(TimeTranslator.unixToLocal(matchInfoDTO.getGameEndTimestamp()));

        return matchInfoDTO;
    }

    @Override
    public Map<String, Object> matchInfo(Map<String, Object> match) {
        // 필요한 match정보만 저장하기 위한 맵
        Map<String, Object> matchInfo = new HashMap<>();

        Map<String, Object> metadata = (Map<String, Object>) match.get("metadata");
        Map<String, Object> info = (Map<String, Object>) match.get("info");

        // "participants" 맵에 접근
        List<Map<String, Object>> participants = (List<Map<String, Object>>) info.get("participants");

        // 얘 돌려쓰자

        // 각 참가자 정보를 처리하고 맵에 저장
        for (int i = 0; i < participants.size(); i++) {
            ParticipantsDTO participantsDTO = new ParticipantsDTO();
            Map<String, Object> participant = participants.get(i);
            participantsDTO.setAssists((int) participant.get("assists"));
            participantsDTO.setChampLevel((int) participant.get("champLevel"));
            participantsDTO.setChampionId((int) participant.get("championId"));
            participantsDTO.setChampionName((String) participant.get("championName"));
            participantsDTO.setDeaths((int) participant.get("deaths"));

            participantsDTO.setDeaths((int) participant.get("deaths"));
            participantsDTO.setFirstBloodKill((boolean) participant.get("firstBloodKill"));
            participantsDTO.setFirstTowerKill((boolean) participant.get("firstTowerKill"));
            participantsDTO.setGoldEarned((int) participant.get("goldEarned"));

            // 포지션(TOP, JUNGLE, MIDDLE, BOTTOM, UTILITY)
            participantsDTO.setIndividualPosition((String) participant.get("individualPosition"));

            participantsDTO.setItem0((int) participant.get("item0"));
            participantsDTO.setItem1((int) participant.get("item1"));
            participantsDTO.setItem2((int) participant.get("item2"));
            participantsDTO.setItem3((int) participant.get("item3"));
            participantsDTO.setItem4((int) participant.get("item4"));
            participantsDTO.setItem5((int) participant.get("item5"));
            participantsDTO.setItem6((int) participant.get("item6"));
            participantsDTO.setKills((int) participant.get("kills"));
            participantsDTO.setLargestMultiKill((int) participant.get("largestMultiKill"));
            participantsDTO.setPentaKills((int) participant.get("pentaKills"));

            // 아레나
            if(participant.get("placement") != null){
                participantsDTO.setPlacement((int) participant.get("placement"));
                participantsDTO.setPlayerAugment1((int) participant.get("playerAugment1"));
                participantsDTO.setPlayerAugment2((int) participant.get("playerAugment2"));
                participantsDTO.setPlayerAugment3((int) participant.get("playerAugment3"));
                participantsDTO.setPlayerAugment4((int) participant.get("playerAugment4"));
            }


            participantsDTO.setSummoner1Id((int) participant.get("summoner1Id"));
            participantsDTO.setSummoner2Id((int) participant.get("summoner2Id"));

            participantsDTO.setSummonerName((String) participant.get("summonerName"));
            participantsDTO.setTeamId((int) participant.get("teamId"));
            participantsDTO.setTotalDamageDealtToChampions((int) participant.get("totalDamageDealtToChampions"));
            participantsDTO.setTotalDamageTaken((int) participant.get("totalDamageTaken"));
            participantsDTO.setTotalHeal((int) participant.get("totalHeal"));
            // 미니언에 오브젝트까지 더해야함...
            participantsDTO.setTotalMinionsKilled((int) participant.get("totalMinionsKilled")+(int)participant.get("neutralMinionsKilled"));


            participantsDTO.setVisionScore((int) participant.get("visionScore"));
            participantsDTO.setSightWardsBoughtInGame((int) participant.get("sightWardsBoughtInGame"));
            participantsDTO.setVisionWardsBoughtInGame((int) participant.get("visionWardsBoughtInGame"));
            participantsDTO.setWardsKilled((int) participant.get("wardsKilled"));
            participantsDTO.setWardsPlaced((int) participant.get("wardsPlaced"));
            participantsDTO.setWin((boolean) participant.get("win"));

            // KDA 계산
            double kda = ((double) participantsDTO.getKills() + participantsDTO.getAssists()) / participantsDTO.getDeaths();

            // DecimalFormat을 사용하여 소수점 두 자리까지 포맷 지정
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String formattedKda = decimalFormat.format(kda);

            participantsDTO.setKda(formattedKda);

            // 룬 정보 가져오기
            Map<String, Object> perks = (Map<String, Object>) participant.get("perks");
            if (perks != null) {
                Map<String, Object> statPerks = (Map<String, Object>) perks.get("statPerks");
                participantsDTO.setDefense((int) statPerks.get("defense"));
                participantsDTO.setFlex((int) statPerks.get("flex"));
                participantsDTO.setOffense((int) statPerks.get("offense"));

                List<Map<String, Object>> styles = (List<Map<String, Object>>) perks.get("styles");
                if (styles != null && styles.size() >= 2) {
                    // 룬 저장할 list
                    List<Integer> perkList = new ArrayList<>();

                    // 첫 번째 스타일과 두 번째 스타일 가져오기
                    Map<String, Object> primaryStyle = styles.get(0);
                    Map<String, Object> subStyle = styles.get(1);
                    participantsDTO.setMainRune((int) primaryStyle.get("style"));
                    participantsDTO.setSubRune((int) subStyle.get("style"));


                    List<Map<String, Object>> selections = (List<Map<String, Object>>) primaryStyle.get("selections");
                    for (int j = 0; j < selections.size(); j++) {
                        Map<String, Object> selection = selections.get(j);
                        int perk = (int) selection.get("perk");

                        // participantsDTO 객체의 필드 업데이트
                        if (j == 0) {
                            participantsDTO.setMainRune1(perk);
                        } else if (j == 1) {
                            participantsDTO.setMainRune2(perk);
                        } else if (j == 2) {
                            participantsDTO.setMainRune3(perk);
                        } else if (j == 3) {
                            participantsDTO.setMainRune4(perk);
                        }// 필요한 만큼 반복
                    }

                    selections = (List<Map<String, Object>>) subStyle.get("selections");
                    for (int j = 0; j < selections.size(); j++) {
                        Map<String, Object> selection = selections.get(j);
                        int perk = (int) selection.get("perk");

                        // participantsDTO 객체의 필드 업데이트
                        if (j == 0) {
                            participantsDTO.setSubRune1(perk);
                        } else if (j == 1) {
                            participantsDTO.setSubRune2(perk);
                        }
                    }
                } else {
                    log.info("Styles field is null or doesn't have enough styles");
                }
            } else {
                log.info("Perks field is null");
            }

            matchInfo.put("participants"+i,participantsDTO);
            ParticipantsDTO parti = (ParticipantsDTO)matchInfo.get("participants"+i);
        }

        return matchInfo;
    }

    public MatchInfoDTO getMatchInfoDTO(MatchInfoDTO matchInfoDTO, Map<String, Object> matchInfo){

        for (int i = 0; i < 10; i++) {
            ParticipantsDTO participant = (ParticipantsDTO) matchInfo.get("participants" + i);

            // 인게임 정보 matchInfo에 담기
            if(i<5){
                matchInfoDTO.setBlueGold(matchInfoDTO.getBlueGold()+participant.getGoldEarned());
                matchInfoDTO.setBlueTotalDamageDealtToChampions(matchInfoDTO.getBlueTotalDamageDealtToChampions()+participant.getTotalDamageDealtToChampions());
                matchInfoDTO.setBlueWardsPlaced(matchInfoDTO.getBlueWardsPlaced()+participant.getWardsPlaced());
                matchInfoDTO.setBlueTotalDamageTaken(matchInfoDTO.getBlueTotalDamageTaken()+participant.getTotalDamageTaken());
                matchInfoDTO.setBlueTotalMinionsKilled(matchInfoDTO.getBlueTotalMinionsKilled()+participant.getTotalMinionsKilled());

            } else {
                matchInfoDTO.setRedGold(matchInfoDTO.getRedGold()+participant.getGoldEarned());
                matchInfoDTO.setRedTotalDamageDealtToChampions(matchInfoDTO.getRedTotalDamageDealtToChampions()+participant.getTotalDamageDealtToChampions());
                matchInfoDTO.setRedWardsPlaced(matchInfoDTO.getRedWardsPlaced()+participant.getWardsPlaced());
                matchInfoDTO.setRedTotalDamageTaken(matchInfoDTO.getRedTotalDamageTaken()+participant.getTotalDamageTaken());
                matchInfoDTO.setRedTotalMinionsKilled(matchInfoDTO.getRedTotalMinionsKilled()+participant.getTotalMinionsKilled());
            }
        }
        return matchInfoDTO;
    }

}
