package com.pinkward.bushgg.web.home.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.pinkward.bushgg.domain.champion.service.ChampionService;
import com.pinkward.bushgg.domain.member.dto.MemberDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 홈페이지 요청을 처리하는 세부 컨트롤러 구현 클래스
 *
 * @author  에너자이조 김기정
 * @since   2023. 9. 4.
 * @version 1.0
 */
@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class HomeController {
   private final ChampionService championService;


   @GetMapping("/")
   public String home(@SessionAttribute(name="loginMember", required = false) MemberDTO loginMember, Model model , HttpSession httpSession) {
      List<Integer> championIds = championService.championLotation();
      List<String> championNamesEn = new ArrayList<>();
      List<String> championNamesKo = new ArrayList<>();

      for (Integer championId : championIds) {
         String championNameEn = championService.getChampionNameEn(championId.toString()).replace(" ","").replace("'","");
         String championNameKo = championService.getChampionNameKo(championId.toString());
         championNamesEn.add(championNameEn);
         championNamesKo.add(championNameKo);
      }

		if (loginMember != null) {
			model.addAttribute("loginMember", loginMember);
		}
		
      model.addAttribute("championNamesEn",championNamesEn);
      model.addAttribute("championNamesKo",championNamesKo);
      return "index";
   }
}