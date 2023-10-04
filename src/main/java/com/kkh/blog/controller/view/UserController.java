package com.kkh.blog.controller.view;

import com.kkh.blog.config.auth.PrincipalDetail;
import com.kkh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.kkh.blog.oauth.KakaoProfile;
import com.kkh.blog.oauth.OAuthToken;
import com.kkh.blog.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	
	@Value("${key}")
	private String Key;
	
	@Autowired
	private AuthenticationManager authenticationManager; 

	@Autowired
	private UserService userService;


	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
		return "user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수

		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "059c43b42aa949ccdfaed590a8522969");
		params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);
		
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);
		
		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("===================================================");
		System.out.println("카카오 액세스 토큰 : " + oauthToken.getAccess_token());
		System.out.println("===================================================");

		//=================================================================================

		RestTemplate rt2 = new RestTemplate();

		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);

		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);
		System.out.println(response2.getBody());
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("===================================================");
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그서버 패스워드 : " + Key);
		System.out.println("===================================================");

		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+ kakaoProfile.getId())
				.password(Key)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();

		User originUser =  userService.findUser(kakaoUser.getUsername());
		
		if (originUser.getUsername() == null) {
			System.out.println("===================================================");
			System.out.println("기존 회원이 아닙니다.");
			System.out.println("===================================================");
			userService.registerUser(kakaoUser);
		}

		System.out.println("===================================================");
		System.out.println("카카오 로그인을 진행합니다.");
		System.out.println("===================================================");

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), Key));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
}
