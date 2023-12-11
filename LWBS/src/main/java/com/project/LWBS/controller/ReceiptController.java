package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.service.ReceiptService;
import com.project.LWBS.service.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/bookStore")
public class ReceiptController {

    private final ReceiptService receiptService;

   private  final ReceiveService receiveService;
    @Autowired
    public ReceiptController(ReceiptService receiptService, ReceiveService receiveService) {
        this.receiptService = receiptService;
        this.receiveService = receiveService;
    }

    @GetMapping("/Receipt")
    public String getAllReceipts(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Receipt> receipts = receiptService.getAllReceipts();
        model.addAttribute("receipts", receipts);
        model.addAttribute("user",principalDetails.getUser());
        return "bookStore/Receipt";
    }

    @GetMapping("/ReceiptSearch")
    public String searchReceipts(@RequestParam(name = "studentId") String studentId, Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Receipt> filteredReceipts = receiptService.findReceiptsByStudentId(studentId);
        model.addAttribute("receipts", filteredReceipts);
        model.addAttribute("user",principalDetails.getUser());
        return "bookStore/ReceiptSearch";
    }

    @PostMapping("/Receipt")
    public String updateReceipt(@RequestParam("receiptId") String receiptId, @RequestParam("check") String check, @RequestParam("receiveDay") String receiveDay,Model model ,@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Receive> receives = receiveService.findDay(receiveDay);
        Receive receive = new Receive();
        model.addAttribute("user",principalDetails.getUser());
        for(int i = 0;i < receives.size();i++)
        {
            if (receives.get(i).getReceiveCheck().equals(check))
            {
                receive = receives.get(i);
                System.out.println("check : "+receive);
            }

        }
        long Id = Long.parseLong(receiptId);
        receiptService.findById(Id, receive);
        return "redirect:/bookStore/Receipt";
    }
}
