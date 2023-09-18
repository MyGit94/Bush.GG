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
import com.pinkward.bushgg.domain.ranking.service.RankingAPIServiceImpl;
import com.pinkward.bushgg.domain.ranking.mapper.ChallengerMapper;
import com.pinkward.bushgg.domain.summoner.dto.SummonerDTO;
import com.pinkward.bushgg.domain.summoner.dto.SummonerTierDTO;
import com.pinkward.bushgg.domain.summoner.mapper.SummonerMapper;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
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

    private final SummonerService summonerService;
    private final APIServiceKo apiServiceKo;
    private final APIServiceAsia apiServiceAsia;
    private final MatchService matchService;
    private final ChampionService championService;
    private final ChallengerMapper challengerMapper;
    private final SummonerMapper summonerMapper;
    private final RankingAPIServiceImpl rankingService;


    @GetMapping(value = "/summoner")
    public String searchSummonerInfo(@RequestParam("summonerName") String summonerName, Model model){
        // 두 글자인 경우 글자 사이에 띄어쓰기 추가
        if (summonerName.length() == 2) {
            summonerName = summonerName.substring(0, 1) + " " + summonerName.substring(1);
        }
        String esummonerName = null;
        try {
            esummonerName = URLEncoder.encode(summonerName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

//        summonerMapper.deleteTier();
//        rankingService.challengerRanking(0,999);
//        rankingService.grandMasterRanking(0,999);
//        rankingService.masterRanking(0,9999);
//        rankingService.diamond1Ranking(0,999);
//        rankingService.diamond2Ranking(0,999);
//        rankingService.diamond3Ranking(0,999);
//        rankingService.diamond4Ranking(0,999);

        summonerName = summonerName.replaceAll(" ","").toLowerCase();

        // 이름으로 소환사 정보 가져옴
        SummonerDTO summoner = apiServiceKo.getSummonerInfo(esummonerName);


        Set<Map<String,Object>> summonerTier = apiServiceKo.getTierInfo(summoner.getId());
        SummonerTierDTO summonerTierDTO = null;

        if (summonerTier != null) {
             summonerTierDTO = summonerService.getTierInfo(summonerTier);
            String tierImageUrl = "img/tier/" + summonerTierDTO.getTier() + ".png";
            model.addAttribute("tierImageUrl", tierImageUrl);
        } else {
            // summonerTier가 null인 경우 디폴트 이미지 URL을 설정
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

                    // KDA 계산
                    double kda = ((double) recentDTO.getKills() + recentDTO.getAssists()) / recentDTO.getDeaths();

                    // DecimalFormat을 사용하여 소수점 두 자리까지 포맷 지정
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String formattedKda = decimalFormat.format(kda);

                    recentDTO.setKda(formattedKda);
                    championCounts = championService.getChampionCounts(championCounts,participant);


                }
                matchInfoDTO =matchService.getMatchInfoDTO(matchInfoDTO, matchInfo);

                participantsList.add(participant);

            }
            summonerWithCounts =  summonerService.getSummonerWith(matchInfo,teamId,name, summonerWithCounts);
            matchInfoDTO =  matchService.getMatchInfoDTO(matchInfoDTO,matchInfo);
            log.info("{}",matchInfoDTO);
            log.info("{}",participantsList);
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
