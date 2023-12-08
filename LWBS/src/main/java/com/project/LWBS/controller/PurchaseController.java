package com.project.LWBS.controller;


import com.project.LWBS.domain.DTO.Purchase;
import com.project.LWBS.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/payment")
    public @ResponseBody Purchase payment(@RequestParam String item, @RequestParam String totalPrice, @RequestParam String totalCnt)
    {
        Purchase response = purchaseService.paymentKakaoPay(item,totalPrice,totalCnt);

        System.out.println("결제고유번호: "+ response.getTid());
        System.out.println("결제요청 URL"+response.getNext_redirect_pc_url());

        return response;
    }

    @GetMapping("/success")
    public String success()
    {
        return "/purchase/success";
    }

    @GetMapping("/cancle")
    public String cancle()
    {
        return "/purchase/cancle";
    }

    @GetMapping("/fail")
    public String fail()
    {
        return "/purchase/fail";
    }
}
