package com.project.LWBS.controller;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bookStore")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    //책 정보, 구매자, 구매일자, 수령 체크, 수령날짜
    @GetMapping("/Receipt")
    public String getAllReceipts(Model model) {
        List<Receipt> receipts = receiptService.getAllReceipts();
        model.addAttribute("receipts", receipts);
        return "bookstore/Receipt";
    }
}

