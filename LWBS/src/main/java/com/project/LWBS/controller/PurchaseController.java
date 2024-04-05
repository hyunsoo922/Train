package com.project.LWBS.controller;


import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.DTO.Approve;
import com.project.LWBS.domain.DTO.CancelDTO;
import com.project.LWBS.domain.DTO.Purchase;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.CartService;
import com.project.LWBS.service.PurchaseService;
import com.project.LWBS.service.ReceiptService;
import com.project.LWBS.service.StudentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private CartService cartService;

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
    public ResponseEntity refund(@RequestParam String book, @AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request, HttpServletResponse response)
    {
        User user = principalDetails.getUser();
        Long id = Long.parseLong(book);
        Book item = studentService.findById(id);
        int price = Integer.parseInt(item.getPrice());

        Receipt receipt = receiptService.findReceiptByBookAndUser(item,user);

        String tid = receipt.getTid();
        List<Receipt> refundList = receiptService.findByTid(tid);
        List<CancelDTO> cancelResponse = new ArrayList<>();

        Cookie[] cookies = request.getCookies();
        String mileagePoint = null;
        if(cookies != null)
        {
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals("mileagePoint"))
                {
                    mileagePoint = cookie.getValue();
                    break;
                }
            }
        }
        int useMileage = Integer.parseInt(mileagePoint) / refundList.size();
        String cookiePoint = String.valueOf(Integer.parseInt(mileagePoint) - useMileage);
        if(cookies != null)
        {
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals("mileagePoint"))
                {
                    cookie.setValue(cookiePoint);
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        int totalPrice = price - useMileage;
        CancelDTO cancel = purchaseService.kakaoCancel(tid,totalPrice);
        System.out.println(cancel);
        cancelResponse.add(cancel);

        receiptService.deleteReceipt(receipt);


        return new ResponseEntity<>(cancelResponse, HttpStatus.OK);


    }

    @GetMapping("/success")
    public String success(@RequestParam("pg_token") String pgToken,HttpSession session)
    {

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

        cartService.deleteCart(user.getId());

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
