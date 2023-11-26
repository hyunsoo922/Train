package com.project.LWBS.service;

import com.project.LWBS.domain.DTO.KakaoDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class KakaoServiceImpl implements KakaoService{

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";

    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    @Override
    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize" + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL + "&response_type=code";
    }

    @Override
    public KakaoDTO getKakaoInfo(String code) throws Exception {
        if(code == null) throw  new Exception("Failed get Autorization code");

        String accessToken = "";
        String refreshToken = "";

        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type","application/x-www-form-urlencoded");

            MultiValueMap<String,String> parameters = new LinkedMultiValueMap<>();
            parameters.add("grant_type","authorization_code");
            parameters.add("client_id",KAKAO_CLIENT_ID);
            parameters.add("client_secret",KAKAO_CLIENT_SECRET);
            parameters.add("code",code);
            parameters.add("redirect_uri",KAKAO_REDIRECT_URL);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(parameters,headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject =(JSONObject) jsonParser.parse(response.getBody());

            accessToken = (String)jsonObject.get("access_token");
            refreshToken = (String) jsonObject.get("refresh_token");
        }catch (Exception e)
        {
            throw new Exception("API call failed");
        }


        return getUserInfoWithUserToken(accessToken);
    }

    @Override
    public KakaoDTO getUserInfoWithUserToken(String accessToken) throws Exception{

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response =restTemplate.exchange(
                KAKAO_API_URI + "/v2/user/me",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
        );

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObject.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

        long id = (long)jsonObject.get("id");
        String nickname = String.valueOf(profile.get("nickname"));
        String profile_img = String.valueOf(profile.get("profile_image_url"));
        System.out.println("카카오ID"+id);
        return KakaoDTO.builder()
                .id(id)
                .profile_img(profile_img)
                .nickname(nickname)
                .build();
    }


}
