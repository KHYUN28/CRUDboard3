package com.kkh.board.controller.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkh.board.model.User;
import com.kkh.board.oauth.KakaoProfile;
import com.kkh.board.oauth.OAuthToken;
import com.kkh.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    @Value("${kakao.client.id}")
    private String KakaoKey;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
        OAuthToken oauthToken = getKakaoOAuthToken(code);
        KakaoProfile kakaoProfile = getKakaoUserProfile(oauthToken.getAccess_token());

        String username = kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId();
        User kakaoUser = User.builder()
                .username(username)
                .password(KakaoKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        User originUser = userService.findUser(kakaoUser.getUsername());

        if (originUser.getUsername() == null) {
            userService.registerUser(kakaoUser);
        }
        authenticateUser(kakaoUser.getUsername(), KakaoKey);
        return "redirect:/";
    }

    private OAuthToken getKakaoOAuthToken(String code) {
        HttpHeaders headersToken = new HttpHeaders();
        headersToken.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> token = new LinkedMultiValueMap<>();
        token.add("grant_type", "authorization_code");
        token.add("client_id", "059c43b42aa949ccdfaed590a8522969");
        token.add("redirect_uri", "http://localhost:7777/auth/kakao/callback");
        token.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(token, headersToken);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        return parseResponse(response, OAuthToken.class);
    }

    private KakaoProfile getKakaoUserProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        return parseResponse(response, KakaoProfile.class);
    }

    private <T> T parseResponse(ResponseEntity<String> response, Class<T> responseType) {
        try {
            return objectMapper.readValue(response.getBody(), responseType);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing response", e);
        }
    }

    private void authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
