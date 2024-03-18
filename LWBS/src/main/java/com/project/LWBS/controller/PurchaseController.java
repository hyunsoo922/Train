package com.project.LWBS.controller;


import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.DTO.Purchase;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.CartService;
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

    @Autowired
    private CartService cartService;

    @PostMapping("/payment")
    public @ResponseBody Purchase payment(@RequestParam String item, @RequestParam String totalPrice, @RequestParam String totalCnt)
    {

        System.out.println("상품"+item+"총가격"+totalPrice+"상품갯수"+totalCnt);
        Purchase response = purchaseService.paymentKakaoPay(item,totalPrice,totalCnt);


        return response;
    }

    @GetMapping("/success")
    public String success(HttpSession session)
    {
        List<Book> bookList = (List<Book>)session.getAttribute("books");
        User user = (User) session.getAttribute("users");
        String receiveDay =(String)session.getAttribute("receiveDate");
        int useMileage = Integer.parseInt((String)session.getAttribute("mileagePoint"));

        studentService.createReceipt(bookList,user,receiveDay,useMileage);

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
