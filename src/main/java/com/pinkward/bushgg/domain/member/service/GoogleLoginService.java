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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Service
public class GoogleLoginService {

    private static final String CALLBACK_URI = "http://localhost/member/callback";
    private static final Collection<String> SCOPES = Arrays.asList("profile", "email");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;

    public void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {

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

    public GoogleTokenResponse getToken(String code) throws Exception {

        InputStream in = new FileInputStream("src/main/resources/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
                        clientSecrets, SCOPES).build();

        return flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
    }

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