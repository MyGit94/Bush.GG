package com.pinkward.bushgg.web.home.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Person;
import com.pinkward.bushgg.domain.GoogleLoginExample;
import com.pinkward.bushgg.domain.api.service.APIServiceKo;
import com.pinkward.bushgg.domain.member.mapper.MemberMapper;
import com.pinkward.bushgg.domain.summoner.service.SummonerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@Slf4j
public class LoginController {

    private final GoogleLoginExample googleLogin;
    private final MemberMapper memberMapper;
    private final APIServiceKo apiServiceKo;
    private final SummonerService summonerService;

    public LoginController(MemberMapper memberMapper, APIServiceKo apiServiceKo, SummonerService summonerService) {
        this.memberMapper = memberMapper;
        this.apiServiceKo = apiServiceKo;
        this.summonerService = summonerService;
        this.googleLogin = new GoogleLoginExample();
    }

    @GetMapping("/login2")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        googleLogin.login(req, resp);
    }

    @GetMapping("/member/callback")
    public RedirectView handleCallback(@RequestParam("code") String code, HttpSession session) throws Exception {
        // Exchange authorization code for access token
        GoogleTokenResponse tokenResponse = googleLogin.getToken(code);

        String accessToken = tokenResponse.getAccessToken();

        // Use the access token to get user info
        Person profile = googleLogin.getUserInfo(accessToken);

        String userId = profile.getResourceName();
        List<EmailAddress> emailAddresses = profile.getEmailAddresses();
        String userEmail = null;

        // 첫 번째 이메일 주소 가져오기
        if (emailAddresses != null && !emailAddresses.isEmpty()) {
            userEmail = emailAddresses.get(0).getValue();
        }


        RedirectView redirectView = new RedirectView();
        if(memberMapper.getUserById(userId)== null){
            session.setAttribute("userId", userId);
            session.setAttribute("userEmail", userEmail);
            redirectView.setUrl("/member/nickname");

        } else {
            session.setAttribute("Member",memberMapper.getUserById(userId));
            redirectView.setUrl("/member/googleLogin");
        }
        return redirectView;
    }

}