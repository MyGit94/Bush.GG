package com.pinkward.bushgg.web.summoner.controller;

import com.pinkward.bushgg.domain.api.service.APIServiceAsia;
import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.challenges.dto.PlayerChallengesInfoDTO;
import com.pinkward.bushgg.domain.champion.service.ChampionService;
import com.pinkward.bushgg.domain.match.common.ChampionCount;
import com.pinkward.bushgg.domain.match.common.RuneList;
import com.pinkward.bushgg.domain.match.common.SummonerWithCount;
import com.pinkward.bushgg.domain.match.dto.MatchInfoDTO;
import com.pinkward.bushgg.domain.match.dto.ParticipantsDTO;
import com.pinkward.bushgg.domain.match.dto.RecentDTO;
import com.pinkward.bushgg.domain.match.service.MatchService;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Summoner 관련 요청을 처리하는 세부 컨트롤러 구현 클래스
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;
    private final APIServiceKo apiServiceKo;
    private final APIServiceAsia apiServiceAsia;
    private final MatchService matchService;
    private final ChampionService championService;

    @GetMapping(value = "/summoner")
    public String searchSummonerInfo(@RequestParam("summonerName") String summonerName, Model model){

        if (summonerName.length() == 2) {
            summonerName = summonerName.charAt(0) + " " + summonerName.substring(1);
        }
        String esummonerName = null;
        esummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8);
        summonerName = summonerName.replaceAll(" ","").toLowerCase();
        SummonerDTO summoner = apiServiceKo.getSummonerInfo(esummonerName);
        if(summoner == null) {
            return "/404";
        }

        Set<Map<String,Object>> summonerTier = apiServiceKo.getTierInfo(summoner.getId());
        SummonerTierDTO summonerTierDTO = null;

        if (summonerTier != null) {
             summonerTierDTO = summonerService.getTierInfo(summonerTier);
            String tierImageUrl = "img/tier/" + summonerTierDTO.getTier() + ".png";
            model.addAttribute("tierImageUrl", tierImageUrl);
        } else {
            model.addAttribute("tierImageUrl", "img/tier/Provisional.png");
        }

        model.addAttribute("summonerTier",summonerTierDTO);
        model.addAttribute("summoner",summoner);

        PlayerChallengesInfoDTO playerChallengesInfo = apiServiceKo.getPlayerChallengesInfo(summoner.getPuuid());
        model.addAttribute("playerChallengesInfo" ,playerChallengesInfo);

        List<String> matchIds = apiServiceAsia.getMatchId(summoner.getPuuid());
        List<Map<String,Object>> matchInfoList = new ArrayList<>();

        RecentDTO recentDTO = new RecentDTO();
        List<ChampionCount> championCounts = new ArrayList<>();
        List<SummonerWithCount> summonerWithCounts = new ArrayList<>();

        for (String matchId : matchIds) {

            Map<String, Object> match = apiServiceAsia.getMatch(matchId);
            MatchInfoDTO matchInfoDTO = matchService.matchInfoDTO(match);
            Map<String, Object> matchInfo = matchService.matchInfo(match);
            matchInfoDTO.setQueueName(matchService.matchQueueName(matchInfoDTO.getQueueId()));

            int teamId = 0;
            String name = "";

            Map<String, Object> matchList = new HashMap<>();
            List<ParticipantsDTO> participantsList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                ParticipantsDTO participant = (ParticipantsDTO) matchInfo.get("participants" + i);
                String getSummonerName = participant.getSummonerName().replaceAll(" ", "").toLowerCase();

                if (summonerName.equals(getSummonerName)) {
                    participant.setMinionsKilledMin(Math.round((double) participant.getTotalMinionsKilled() / matchInfoDTO.getGameDuration() * 60 * 10.0) / 10.0);
                    matchList.put("participant", participant);
                    teamId = participant.getTeamId();
                    name = participant.getSummonerName();

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

                    double kda = ((double) recentDTO.getKills() + recentDTO.getAssists()) / recentDTO.getDeaths();
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String formattedKda = decimalFormat.format(kda);

                    recentDTO.setKda(formattedKda);
                    championCounts = championService.getChampionCounts(championCounts,participant);
                }
                participantsList.add(participant);
            }
            summonerWithCounts =  summonerService.getSummonerWith(matchInfo,teamId,name, summonerWithCounts);
            matchInfoDTO =  matchService.getMatchInfoDTO(matchInfoDTO,matchInfo);
            matchList.put("matchInfo",matchInfoDTO);
            matchList.put("participantsList", participantsList);
            matchInfoList.add(matchList);
        }

        model.addAttribute("matchInfoList",matchInfoList);

        List<SummonerWithCount> filteredList = summonerService.sortSummonerWith(summonerWithCounts);
        List<ChampionCount> filteredChampionCounts = championService.sortChampionCounts(championCounts);
        recentDTO.setSummonerWithCounts(filteredList);
        recentDTO.setChampionCounts(filteredChampionCounts);

        model.addAttribute("recentDTO",recentDTO);
        return "record";
    }

}
