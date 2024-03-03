package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.service.ReceiptService;
import com.project.LWBS.service.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String getAllReceipts(Model model,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        // 페이징 객체 생성
        PageRequest pageRequest = PageRequest.of(page, size);

        // Receipt 테이블에서 페이징된 결과를 받아옴
        Page<Receipt> receiptPage = receiptService.getAllReceipts(pageRequest);

        int nowPage = receiptPage.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage -4, 1);
        int endPage = Math.min(nowPage +5, receiptPage.getTotalPages());


        // 현재 페이지의 Receipt 목록
        List<Receipt> receipts = receiptPage.getContent();



        model.addAttribute("receipts", receipts);
        model.addAttribute("user", principalDetails.getUser());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", receiptPage.getTotalPages());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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
    public String searchReceipts(@RequestParam(name = "studentId", required = false) String studentId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (studentId == null) {
            // studentId가 없을 경우, 혹은 required=true로 설정하면 무조건 값이 필요할 경우 예외처리를 할 수 있습니다.
            // 여기서는 기본값으로 현재 사용자의 학생 번호를 사용하도록 했습니다.
            studentId = principalDetails.getUser().getStudentId();
        }

        List<Receipt> filteredReceipts = receiptService.findReceiptsByStudentId(studentId);
        model.addAttribute("receipts", filteredReceipts);
        model.addAttribute("user", principalDetails.getUser());
        return "bookStore/ReceiptSearch";
    }

    @PostMapping("/ReceiptSearch")
    public String updateReceiptSearch(@RequestParam("receiptId") Long receiptId,
                                      @RequestParam("check") String check,
                                      @RequestParam("receiveDay") String receiveDay,
                                      Model model,
                                      @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Receive> receives = receiveService.findDay(receiveDay);
        Receive receive = new Receive();

        for (int i = 0; i < receives.size(); i++) {
            if (receives.get(i).getReceiveCheck().equals(check)) {
                receive = receives.get(i);
                System.out.println("수령 : " + receive);
            }
        }

        receiptService.findById(receiptId, receive);
        return "redirect:/bookStore/ReceiptSearch";
    }

}
