package com.project.LWBS.controller;


import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.DTO.Purchase;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.PurchaseService;
import com.project.LWBS.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/payment")
    public @ResponseBody Purchase payment(@RequestParam String item, @RequestParam String totalPrice, @RequestParam String totalCnt)
    {
        Purchase response = purchaseService.paymentKakaoPay(item,totalPrice,totalCnt);

        System.out.println("결제고유번호: "+ response.getTid());
        System.out.println("결제요청 URL"+response.getNext_redirect_pc_url());

        return response;
    }

    @GetMapping("/success")
    public String success(HttpSession session)
    {
        List<Book> bookList = (List<Book>)session.getAttribute("books");
        User user = (User) session.getAttribute("users");
        String receiveDay =(String)session.getAttribute("receiveDate");

        System.out.println("구매한 책"+bookList);
        System.out.println("구매자"+user);
        System.out.println("수령일"+receiveDay);

        studentService.createReceipt(bookList,user,receiveDay);


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
