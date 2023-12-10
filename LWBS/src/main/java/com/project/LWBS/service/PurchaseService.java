package com.project.LWBS.service;

import com.project.LWBS.domain.DTO.Purchase;

public interface PurchaseService {
    Purchase paymentKakaoPay(String item, String totalPrice, String totalCnt);
}
