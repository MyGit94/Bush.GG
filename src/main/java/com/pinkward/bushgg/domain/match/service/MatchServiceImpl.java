package com.pinkward.bushgg.domain.match.service;

import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.champion.mapper.ChampionMapper;
import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.common.RuneList;
import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.common.TimeTranslator;
import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.match.dto.RecentDTO;
import com.pinkward.bushgg.domain.ranking.mapper.ChallengerMapper;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final ChallengerMapper challengerMapper;
    private final ChampionMapper championMapper;

    @Override
    public MatchInfoDTO matchInfoDTO(Map<String, Object> match) {
        MatchInfoDTO matchInfoDTO = new MatchInfoDTO();

        Map<String, Object> metadata = (Map<String, Object>) match.get("metadata");

        matchInfoDTO.setMatchId((String)metadata.get("matchId"));

        Map<String, Object> info = (Map<String, Object>) match.get("info");

        // DTO 만들었다 넣자..
        matchInfoDTO.setGameDuration((Integer) info.get("gameDuration"));
        matchInfoDTO.setGameEndTimestamp((Long) info.get("gameEndTimestamp"));
        matchInfoDTO.setQueueId((int) info.get("queueId"));

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

        tower = (Map<String, Object>) redTeamObjectives.get("tower");
        matchInfoDTO.setRedTowerKills((int) tower.get("kills"));

        matchInfoDTO.setRedWin((boolean) redTeam.get("win"));

        matchInfoDTO.setChangeGameDuration(TimeTranslator.unixMinAndSec(matchInfoDTO.getGameDuration()));
        matchInfoDTO.setEndGame(TimeTranslator.unixToLocal(matchInfoDTO.getGameEndTimestamp()));

        return matchInfoDTO;
    }

    @Override
    public Map<String, Object> matchInfo(Map<String, Object> match) {

        Map<String, Object> matchInfo = new HashMap<>();

        Map<String, Object> info = (Map<String, Object>) match.get("info");

        List<Map<String, Object>> participants = (List<Map<String, Object>>) info.get("participants");

        for (int i = 0; i < participants.size(); i++) {
            ParticipantsDTO participantsDTO = new ParticipantsDTO();
            Map<String, Object> participant = participants.get(i);

            participantsDTO.setAssists((int) participant.get("assists"));
            participantsDTO.setChampLevel((int) participant.get("champLevel"));
            participantsDTO.setChampionId((int) participant.get("championId"));
            participantsDTO.setChampionName(championMapper.getChampionEnName(participantsDTO.getChampionId()));
            participantsDTO.setDeaths((int) participant.get("deaths"));
            participantsDTO.setSummonerId((String) participant.get("summonerId"));

//            if (challengerMapper.getTierById(participantsDTO.getSummonerId())==null) {
//                SummonerTierDTO summonerTierDTO = summonerService.getTierInfo(apiServiceKo.getTierInfo(participantsDTO.getSummonerId()));
//                log.info("{}",summonerTierDTO);
//                participantsDTO.setTier(summonerTierDTO.getTierName());
//                log.info("{}",participantsDTO.getTier());
//            } else {
//                participantsDTO.setTier(challengerMapper.getTierById(participantsDTO.getSummonerId()));
//            }

            participantsDTO.setGoldEarned((int) participant.get("goldEarned"));
            participantsDTO.setItem0((int) participant.get("item0"));
            participantsDTO.setItem1((int) participant.get("item1"));
            participantsDTO.setItem2((int) participant.get("item2"));
            participantsDTO.setItem3((int) participant.get("item3"));
            participantsDTO.setItem4((int) participant.get("item4"));
            participantsDTO.setItem5((int) participant.get("item5"));
            participantsDTO.setItem6((int) participant.get("item6"));
            participantsDTO.setKills((int) participant.get("kills"));
            participantsDTO.setLargestMultiKill((int) participant.get("largestMultiKill"));

            if(participant.get("placement") != null){
                participantsDTO.setPlacement((int) participant.get("placement"));
                participantsDTO.setPlayerAugment1((int) participant.get("playerAugment1"));
                participantsDTO.setPlayerAugment2((int) participant.get("playerAugment2"));
                participantsDTO.setPlayerAugment3((int) participant.get("playerAugment3"));
                participantsDTO.setPlayerAugment4((int) participant.get("playerAugment4"));
            }

            participantsDTO.setSummoner1Id((int) participant.get("summoner1Id"));
            participantsDTO.setSummoner2Id((int) participant.get("summoner2Id"));

            if(challengerMapper.getNameById(participantsDTO.getSummonerId())==null){
                participantsDTO.setSummonerName((String) participant.get("summonerName"));
            } else {
                participantsDTO.setSummonerName(challengerMapper.getNameById(participantsDTO.getSummonerId()));
            }

            participantsDTO.setTeamId((int) participant.get("teamId"));
            participantsDTO.setTotalDamageDealtToChampions((int) participant.get("totalDamageDealtToChampions"));
            participantsDTO.setTotalDamageTaken((int) participant.get("totalDamageTaken"));
            
            participantsDTO.setTotalMinionsKilled((int) participant.get("totalMinionsKilled")+(int)participant.get("neutralMinionsKilled"));

            participantsDTO.setVisionScore((int) participant.get("visionScore"));
            participantsDTO.setWardsKilled((int) participant.get("wardsKilled"));
            participantsDTO.setWardsPlaced((int) participant.get("wardsPlaced"));
            participantsDTO.setWin((boolean) participant.get("win"));

            double kda = ((double) participantsDTO.getKills() + participantsDTO.getAssists()) / participantsDTO.getDeaths();

            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String formattedKda = decimalFormat.format(kda);

            participantsDTO.setKda(formattedKda);

            Map<String, Object> perks = (Map<String, Object>) participant.get("perks");
            if (perks != null) {
                Map<String, Object> statPerks = (Map<String, Object>) perks.get("statPerks");
                participantsDTO.setDefense((int) statPerks.get("defense"));
                participantsDTO.setFlex((int) statPerks.get("flex"));
                participantsDTO.setOffense((int) statPerks.get("offense"));

                List<Map<String, Object>> styles = (List<Map<String, Object>>) perks.get("styles");
                if (styles != null && styles.size() >= 2) {

                    Map<String, Object> primaryStyle = styles.get(0);
                    Map<String, Object> subStyle = styles.get(1);
                    participantsDTO.setMainRune((int) primaryStyle.get("style"));
                    participantsDTO.setSubRune((int) subStyle.get("style"));

                    List<Map<String, Object>> selections = (List<Map<String, Object>>) primaryStyle.get("selections");
                    for (int j = 0; j < selections.size(); j++) {
                        Map<String, Object> selection = selections.get(j);
                        int perk = (int) selection.get("perk");

                        if (j == 0) {
                            participantsDTO.setMainRune1(perk);
                        } else if (j == 1) {
                            participantsDTO.setMainRune2(perk);
                        } else if (j == 2) {
                            participantsDTO.setMainRune3(perk);
                        } else if (j == 3) {
                            participantsDTO.setMainRune4(perk);
                        }
                    }

                    selections = (List<Map<String, Object>>) subStyle.get("selections");
                    for (int j = 0; j < selections.size(); j++) {
                        Map<String, Object> selection = selections.get(j);
                        int perk = (int) selection.get("perk");

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

    public String matchQueueName(int queueId) {
        switch (queueId) {
            case 420:
                return "솔로 랭크";
            case 430:
                return "일반";
            case 440:
                return "자유 랭크";
            case 450:
                return "무작위 총력전";
            case 900:
                return "우르프 모드";
            case 1700:
                return "아레나";
            case 1900:
                return "우르프 모드";
            default:
                return "알 수 없음";
        }
    }

}
