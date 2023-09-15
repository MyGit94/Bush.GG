package com.pinkward.bushgg.domain.challenges.controller;

import com.pinkward.bushgg.domain.challenges.dto.ChallengesDTO;
import com.pinkward.bushgg.domain.challenges.dto.LocalizedNameDTO;
import com.pinkward.bushgg.domain.challenges.service.ChallengesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class ChallengesController {
//
//    private final ChallengesService challengesService;
//
//    @GetMapping("/challenges")
//    public String cheallenges(){
//        List<ChallengesDTO> info = challengesService.allChallengesInfo();
//        for (ChallengesDTO result : info) {
//            LocalizedNameDTO koLocalizedNames = result.getLocalizedNames().get("ko_KR");
//            if (koLocalizedNames != null) {
//                Map<String, LocalizedNameDTO> newLocalizedNames = new HashMap<>();
//                newLocalizedNames.put("ko_KR", koLocalizedNames);
//                result.setLocalizedNames(newLocalizedNames);
//                log.info("Updated info: {}", result);
//            }
//        }
//        return "index";
//    }
//}
