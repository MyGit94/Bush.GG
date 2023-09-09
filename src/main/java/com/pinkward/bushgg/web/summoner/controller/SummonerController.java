package com.pinkward.bushgg.web.summoner.controller;

import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.match.common.TimeTranslator;
import com.pinkward.bushgg.domain.match.dto.*;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerServiceImpl summonerService;

    @GetMapping("/")
    public String goIndex(){
        return "index";
    }


    @GetMapping(value = "/summoner")
    public String searchSummonerInfo(@RequestParam("summonerName") String summonerName, Model model){
        // 두 글자인 경우 글자 사이에 띄어쓰기 추가
        if (summonerName.length() == 2) {
            summonerName = summonerName.substring(0, 1) + " " + summonerName.substring(1);
        }
        String esummonerName;
        try {
            esummonerName = URLEncoder.encode(summonerName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        summonerName = summonerName.replaceAll(" ","").toLowerCase();

        // 이름으로 소환사 정보 가져옴
        SummonerDTO summoner = summonerService.summonerInfo(esummonerName);
        SummonerTierDTO summonerTier = summonerService.getTierInfo(summoner.getId());

        if (summonerTier != null) {
            String tierImageUrl = "img/tier/" + summonerTier.getTier() + ".png";
            model.addAttribute("tierImageUrl", tierImageUrl);
        } else {
            // summonerTier가 null인 경우 디폴트 이미지 URL을 설정
            model.addAttribute("tierImageUrl", "img/tier/Provisional.png");
        }
        model.addAttribute("summonerTier",summonerTier);
        model.addAttribute("summoner",summoner);

        List<String> matchIds = summonerService.getMatchId(summoner.getPuuid());
        List<Map<String,Object>> matchInfoList = new ArrayList<>();

        RecentDTO recentDTO = new RecentDTO();
        List<ChampionCount> championCounts = new ArrayList<>();
        // puuid로 challenges를 가져옴
        PlayerChallengesInfoDTO playerChallengesInfo = summonerService.getPlayerChallengesInfo(summoner.getPuuid());

        // MatchIdList를 for문 돌리는중
        for (String matchId : matchIds) {
            // 하나의 matchId로 matchInfo Map을 가져옴
            log.info("{}",matchId);
            Map<String, Object> match = summonerService.getMatch(matchId);
            Map<String, Object> matchInfo = summonerService.matchInfo(match);

            // Map에서 matchInfo 키를 가진 값을 MatchInfoDTO에 저장
            MatchInfoDTO matchInfoDTO = (MatchInfoDTO) matchInfo.get("matchInfo");

            // 참가자들 넣을 List를 만듦
            List<ParticipantsDTO> participantsList = new ArrayList<>();
            matchInfoDTO.setChangeGameDuration(TimeTranslator.unixMinAndSec(matchInfoDTO.getGameDuration()));
            matchInfoDTO.setEndGame(TimeTranslator.unixToLocal(matchInfoDTO.getGameEndTimestamp()));

            // matchInfoDTO를 모델에 담고있는데 얘를 Map에 담아야대
            Map<String, Object> matchList = new HashMap<>();

            for (int i = 0; i < 10; i++) {
                ParticipantsDTO participant = (ParticipantsDTO) matchInfo.get("participants" + i);

                String getSummonerName = participant.getSummonerName().replaceAll(" ", "").toLowerCase();

                if (summonerName.equals(getSummonerName)) {
                    participant.setMinionsKilledMin(Math.round((double) participant.getTotalMinionsKilled() / matchInfoDTO.getGameDuration() * 60 * 10.0) / 10.0);
                    matchList.put("participant", participant);

                    int[] selectedRune1 = RuneList.getRuneListByValue(participant.getMainRune());
                    int[] selectedRune2 = RuneList.getSubRuneArray(RuneList.getRuneListByValue(participant.getSubRune()));
                    int[] offense = RuneList.getStatPerksOffense();
                    int[] flex = RuneList.getStatPerksFlex();
                    int[] defense = RuneList.getStatPerksDefense();

                    matchList.put("mainRune",selectedRune1);
                    matchList.put("subRune",selectedRune2);
                    matchList.put("offense",offense);
                    matchList.put("flex",flex);
                    matchList.put("defense",defense);


                    recentDTO.setKills(recentDTO.getKills()+participant.getKills());
                    recentDTO.setAssists(recentDTO.getAssists()+participant.getAssists());
                    recentDTO.setDeaths(recentDTO.getDeaths()+participant.getDeaths());
                    if(participant.isWin()){
                        recentDTO.setWin(recentDTO.getWin()+1);
                    } else {
                        recentDTO.setLose(recentDTO.getLose()+1);
                    }

                    double winRate = ((double)recentDTO.getWin() / (recentDTO.getWin() + recentDTO.getLose())) * 100;
                    recentDTO.setWinRate((int)Math.round(winRate));

                    // KDA 계산
                    double kda = ((double) recentDTO.getKills() + recentDTO.getAssists()) / recentDTO.getDeaths();

                    // DecimalFormat을 사용하여 소수점 두 자리까지 포맷 지정
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String formattedKda = decimalFormat.format(kda);

                    recentDTO.setKda(formattedKda);

                    // 챔피언 최근 승률

                    boolean found = false;
                    for (ChampionCount championCount : championCounts) {
                        if (participant.getChampionName().equals(championCount.getChampionName())) {
                            // 존재하면 count와 win을 증가시킴
                            championCount.setCount(championCount.getCount() + 1);
                            if(participant.isWin()){
                                championCount.setWin(championCount.getWin() + 1);
                            }
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        // 챔피언 이름이 리스트에 없으면 새 객체 생성하여 리스트에 추가
                        ChampionCount newChampionCount = new ChampionCount();
                        newChampionCount.setChampionName(participant.getChampionName());
                        newChampionCount.setCount(1);
                        if (participant.isWin()) {
                            newChampionCount.setWin(1);
                        } else {
                            newChampionCount.setWin(0); // 이긴 횟수를 초기화
                        }
                        championCounts.add(newChampionCount);
                    }



                }

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

                participantsList.add(participant);

            }
            log.info("{}",championCounts);
            matchList.put("matchInfo",matchInfoDTO);
            matchList.put("participantsList", participantsList);

            matchInfoList.add(matchList);

        }
        recentDTO.setChampionCounts(championCounts);
        log.info("{}",recentDTO.getChampionCounts());
        Collections.sort(recentDTO.getChampionCounts(), Comparator.comparing(ChampionCount::getCount).reversed());
        model.addAttribute("recentDTO",recentDTO);
        model.addAttribute("matchInfoList",matchInfoList);
        model.addAttribute("playerChallengesInfo" ,playerChallengesInfo);

        return "record";
    }

}
