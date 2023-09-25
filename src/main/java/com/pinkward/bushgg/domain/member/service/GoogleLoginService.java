package com.pinkward.bushgg.domain.member.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

/**
 * 구글 로그인 API로 구글 로그인 구현 클래스
 */
@Slf4j
public class GoogleLoginService {

    private static final String CALLBACK_URI = "http://bushgg.r-e.kr/member/callback";
    private static final Collection<String> SCOPES = Arrays.asList("profile", "email");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;

    /**
     * 구글 계정으로 로그인할 수 있는 페이지가 표시되는 메소드
     */
    public void login( HttpServletResponse resp) throws Exception {

        if (httpTransport == null) {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        }

        InputStream in = new FileInputStream("src/main/resources/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
                        clientSecrets, SCOPES).build();

        GenericUrl url =
                flow.newAuthorizationUrl().setRedirectUri(CALLBACK_URI).setState("state-token");

        resp.sendRedirect(url.build());
    }

    /**
     * 인증 코드(code)를 교환하여 액세스 토큰을 얻는 메소드
     */
    public GoogleTokenResponse getToken(String code) throws Exception {
        InputStream in = new FileInputStream("src/main/resources/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
                        clientSecrets, SCOPES).build();

        return flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
    }

    /**
     * 액세스 토큰을 기반으로 사용자 정보를 가져오는 메소드
     */
    public Person getUserInfo(String accessToken) throws IOException {
        Credential credential = new GoogleCredential().setAccessToken(accessToken);
        PeopleService peopleService =
                new PeopleService.Builder(httpTransport, JacksonFactory.getDefaultInstance(), credential)
                        .setApplicationName("bushgg")
                        .build();

        Person profile = peopleService.people().get("people/me")
                .setPersonFields("names,emailAddresses,phoneNumbers")
                .execute();

        return profile;
    }
}