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

    //모든 구매내역을 조회
    @GetMapping("/Receipt")
    public String getAllReceipts(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Receipt> receipts = receiptService.getAllReceipts();
        model.addAttribute("receipts", receipts);
        model.addAttribute("user",principalDetails.getUser());
        return "bookStore/Receipt";
    }
    //학생 번호을 입력하면 입력한 학생의 구매내역을 조회

    //구매 수령 여부 변경
    @PostMapping("/Receipt")
    public String updateReceipt(@RequestParam("receiptId") String receiptId,
                                @RequestParam("check") String check,
                                @RequestParam("receiveDay") String receiveDay,Model model,
                                @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Receive> receives = receiveService.findDay(receiveDay);
        Receive receive = new Receive();
        model.addAttribute("user",principalDetails.getUser());
        for(int i = 0;i < receives.size();i++)
        {
            if (receives.get(i).getReceiveCheck().equals(check))
            {
                receive = receives.get(i);
                System.out.println("수령 : "+receive);
            }

        }
        long Id = Long.parseLong(receiptId);
        receiptService.findById(Id, receive);
        return "redirect:/bookStore/Receipt";
    }

    @GetMapping("/ReceiptSearch")
    public String searchReceipts(@RequestParam(name = "studentId") String studentId, Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Receipt> filteredReceipts = receiptService.findReceiptsByStudentId(studentId);
        model.addAttribute("receipts", filteredReceipts);
        model.addAttribute("user",principalDetails.getUser());
        return "bookStore/ReceiptSearch";
    }
}
