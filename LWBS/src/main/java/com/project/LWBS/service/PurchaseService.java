package com.project.LWBS.service;

import com.project.LWBS.domain.DTO.CancelDTO;
import com.project.LWBS.domain.DTO.Purchase;

public interface PurchaseService {
    Purchase paymentKakaoPay(String item, String totalPrice, String totalCnt);

    CancelDTO kakaoCancel(String tid, int totalPrice);

}
