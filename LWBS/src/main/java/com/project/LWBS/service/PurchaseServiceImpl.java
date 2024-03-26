package com.project.LWBS.service;

import com.project.LWBS.domain.DTO.Purchase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    @Override
    public Purchase paymentKakaoPay(String item,String totalPrice,String totalCnt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","KakaoAK 74d0916631639e978afec5faf0540314");
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> paymentData = new LinkedMultiValueMap<>();

        // 카카오페이에 보내줘야할 정보들
        paymentData.add("cid","TC0ONETIME");
        paymentData.add("partner_order_id","Kakao20230829");
        paymentData.add("partner_user_id","KakaoPay");
        paymentData.add("item_name",item);
        paymentData.add("quantity",totalCnt);
        paymentData.add("total_amount",totalPrice);
        paymentData.add("tax_free_amount","0");
        paymentData.add("approval_url","http://localhost:8093/purchase/success");
        paymentData.add("cancel_url","http://localhost:8093/purchase/cancel");
        paymentData.add("fail_url","http://localhost:8093/purchase/fail");

        // HTTP 통신을 위해서 header 와 body 를 하나로만들기위함
        HttpEntity<Map> request = new HttpEntity<Map>(paymentData,headers);

        // API 호출후 응답을 받기위함
        RestTemplate template = new RestTemplate();

        String url = "https://kapi.kakao.com/v1/payment/ready";

        // post 요청후 결과를 받아 저장
        Purchase purchase = template.postForObject(url,request,Purchase.class);

        return purchase;
    }
}
