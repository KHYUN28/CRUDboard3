package com.kkh.board.controller.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkh.board.model.User;
import com.kkh.board.oauth.KakaoProfile;
import com.kkh.board.oauth.OAuthToken;
import com.kkh.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class KakaoOauthController {

    @Value("${key}")
    private String Key;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {

        OAuthToken oauthToken = getKakaoOAuthToken(code);

        KakaoProfile kakaoProfile = getKakaoUserProfile(oauthToken.getAccess_token());

        User kakaoUser = createUserFromKakaoProfile(kakaoProfile);

        User originUser = userService.findUser(kakaoUser.getUsername());

        if (originUser.getUsername() == null) {	userService.registerUser(kakaoUser); }

        authenticateUser(kakaoUser.getUsername(), Key);

        return "redirect:/";
    }

    private OAuthToken getKakaoOAuthToken(String code) {
        RestTemplate rtToken = new RestTemplate();

        HttpHeaders headersToken = new HttpHeaders();
        headersToken.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "059c43b42aa949ccdfaed590a8522969");
        params.add("redirect_uri", "http://localhost:7777/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headersToken);

        ResponseEntity<String> response = rtToken.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

    private KakaoProfile getKakaoUserProfile(String accessToken) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    private User createUserFromKakaoProfile(KakaoProfile kakaoProfile) {
        String username = kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId();

        return User.builder()
                .username(username)
                .password(Key)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();
    }

    private void authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
