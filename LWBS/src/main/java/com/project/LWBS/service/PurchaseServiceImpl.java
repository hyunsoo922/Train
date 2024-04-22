package com.project.LWBS.service;

import com.project.LWBS.domain.DTO.Approve;
import com.project.LWBS.domain.DTO.CancelDTO;
import com.project.LWBS.domain.DTO.Purchase;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    @Override
    public Purchase paymentKakaoPay(String item,String totalPrice,String totalCnt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + "DEVA5BB01EBFA7F8A1129BF32EC7072B07638F9E");
        headers.set("Content-Type", "application/json");


        MultiValueMap<String,String> paymentData = new LinkedMultiValueMap<>();

        // 13.53.50.7:8093
        String requestJson = "{\"cid\":\"TC0ONETIME\",\"partner_order_id\":\"Kakao20230829\",\"partner_user_id\":\"KakaoPay\",\"item_name\":\"" + item + "\",\"quantity\":\"" + totalCnt + "\",\"total_amount\":\"" + totalPrice + "\",\"tax_free_amount\":\"0\",\"approval_url\":\"http://13.53.50.7:8093/purchase/success\",\"cancel_url\":\"http://13.53.50.7:8093/purchase/cancel\",\"fail_url\":\"http://13.53.50.7:8093/purchase/fail\"}";

        // localhost:8093
//        String requestJson = "{\"cid\":\"TC0ONETIME\",\"partner_order_id\":\"Kakao20230829\",\"partner_user_id\":\"KakaoPay\",\"item_name\":\"" + item + "\",\"quantity\":\"" + totalCnt + "\",\"total_amount\":\"" + totalPrice + "\",\"tax_free_amount\":\"0\",\"approval_url\":\"http://localhost:8093/purchase/success\",\"cancel_url\":\"http://localhost:8093/purchase/cancel\",\"fail_url\":\"http://localhost:8093/purchase/fail\"}";

        // HTTP 통신을 위해서 header 와 body 를 하나로만들기위함

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
        // API 호출후 응답을 받기위함
        RestTemplate template = new RestTemplate();

        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        // post 요청후 결과를 받아 저장
        Purchase purchase = template.postForObject(url,request,Purchase.class);

        return purchase;
    }

    @Override
    public CancelDTO kakaoCancel(String tid, int totalPrice) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + "DEVA5BB01EBFA7F8A1129BF32EC7072B07638F9E");
        headers.set("Content-Type", "application/json");
        System.out.println("접속");
        // 요청 본문을 JSON 형식으로 구성
        String requestJson = "{\"cid\": \"TC0ONETIME\", \"tid\": \"" + tid + "\", \"cancel_amount\": " + totalPrice + ", \"cancel_tax_free_amount\": 0, \"payload\": \"환불합니다.\"}";
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/cancel";

        CancelDTO cancelResponse = restTemplate.postForObject(url, request, CancelDTO.class);
        return cancelResponse;
    }

    @Override
    public Approve approveKakaoPay(String pgToken, String tid) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + "DEVA5BB01EBFA7F8A1129BF32EC7072B07638F9E");
        headers.set("Content-Type", "application/json");

        String requestJson = "{\"cid\":\"TC0ONETIME\",\"partner_order_id\":\"Kakao20230829\"," +
                "\"partner_user_id\":\"KakaoPay\", \"tid\": \"" + tid + "\", \"pg_token\": \"" + pgToken + "\"}";


        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";

        Approve approveResponse = restTemplate.postForObject(url, request, Approve.class);
        System.out.println("Approve"+approveResponse);
        return approveResponse;
    }


//    public CancelDTO kakaoCancel(String tid, int totalPrice) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization","KakaoAK 74d0916631639e978afec5faf0540314");
//        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
//
//        // 요청 본문을 JSON 형식으로 구성
//        MultiValueMap<String,String> paymentData = new LinkedMultiValueMap<>();
//        paymentData.add("cid", "TC0ONETIME");
//        paymentData.add("tid", tid);
//        paymentData.add("cancel_amount", String.valueOf(totalPrice));
//        paymentData.add("cancel_tax_free_amount", "500");
//        paymentData.add("cancel_available_amount", String.valueOf(totalPrice));
//        paymentData.add("payload", "환불합니다.");
//
//        HttpEntity<Map> request = new HttpEntity<>(paymentData, headers);
//        System.out.println(request);
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://kapi.kakao.com/v1/payment/cancel";
//        CancelDTO cancelResponse = restTemplate.postForObject(url,request,CancelDTO.class);
//
//
//        return cancelResponse;
//    }


}
