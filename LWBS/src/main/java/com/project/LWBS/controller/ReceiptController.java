/*
package com.project.LWBS.controller;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.service.ReceiptService;
import org.apache.tomcat.util.modeler.BaseAttributeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bookStore")
public class ReceiptController {

    private ReceiptService receiptService;
    private BaseAttributeFilter model;

    @Autowired
    public ReceiptController(ReceiptService receiptService){this.receiptService = receiptService;}



    @GetMapping("Receipt")
    public String getAllReceipt(Module module){
        List<Receipt> Receipt = ReceiptService.getAllReceipts();
        model.addAttribute("receipts");
        return "Receipt";
    }
}
*/
