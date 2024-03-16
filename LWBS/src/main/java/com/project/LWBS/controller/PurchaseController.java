package com.project.LWBS.controller;


import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.DTO.Approve;
import com.project.LWBS.domain.DTO.CancelDTO;
import com.project.LWBS.domain.DTO.Purchase;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.PurchaseService;
import com.project.LWBS.service.ReceiptService;
import com.project.LWBS.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/payment")
    public @ResponseBody Purchase payment(@RequestParam String item, @RequestParam String totalPrice, @RequestParam String totalCnt,HttpSession session)
    {

        System.out.println("상품"+item+"총가격"+totalPrice+"상품갯수"+totalCnt);
        Purchase response = purchaseService.paymentKakaoPay(item,totalPrice,totalCnt);
        session.setAttribute("purchase",response);

        return response;
    }

    @PostMapping("/refund")
    public ResponseEntity refund(@RequestParam String books, @AuthenticationPrincipal PrincipalDetails principalDetails)
    {
        User user = principalDetails.getUser();
        List<String> bookList = Arrays.asList(books.split(","));
        List<Book> bookLists = studentService.findByIds(bookList);
        int totalPrice = 0;
        for(Book book : bookLists)
        {
            int price = Integer.parseInt(book.getPrice());
            totalPrice += price;
        }

//        System.out.println("bookLists"+bookLists);
//        System.out.println("totalPrice"+totalPrice);
        List<Receipt> receiptList = receiptService.findReceiptsByBookAndUser(bookLists,user);

        System.out.println(receiptList);
        Set<String> tids = new HashSet<>();

        for(Receipt receipt : receiptList)
        {
            tids.add(receipt.getTid());
        }

        System.out.println(tids);
        List<CancelDTO> cancelResponse = new ArrayList<>();
        for (String tid : tids)
        {
            CancelDTO cancel = purchaseService.kakaoCancel(tid,totalPrice);
            cancelResponse.add(cancel);
        }

        for(Receipt receipt : receiptList)
        {

            receiptService.deleteReceipt(receipt);
        }

        return new ResponseEntity<>(cancelResponse, HttpStatus.OK);


    }

    @GetMapping("/success")
    public String success(@RequestParam("pg_token") String pgToken,HttpSession session)
    {
//        System.out.println("토큰"+pgToken);
        List<Book> bookList = (List<Book>)session.getAttribute("books");
        User user = (User) session.getAttribute("users");
        String receiveDay =(String)session.getAttribute("receiveDate");
        int useMileage = Integer.parseInt((String)session.getAttribute("mileagePoint"));
        Purchase purchase = (Purchase) session.getAttribute("purchase");

        Approve approve = purchaseService.approveKakaoPay(pgToken,purchase.getTid());

        if(approve != null)
        {
            System.out.println("승인 요청 성공");
            studentService.createReceipt(bookList,user,receiveDay,useMileage,purchase.getTid());
        }


        return "purchase/success";
    }

    @GetMapping("/cancle")
    public String cancle()
    {
        return "purchase/cancle";
    }

    @GetMapping("/fail")
    public String fail()
    {
        return "purchase/fail";
    }
}
