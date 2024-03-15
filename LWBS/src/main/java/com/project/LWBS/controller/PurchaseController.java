package com.project.LWBS.controller;


import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.DTO.Purchase;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.PurchaseService;
import com.project.LWBS.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/payment")
    public @ResponseBody Purchase payment(@RequestParam String item, @RequestParam String totalPrice, @RequestParam String totalCnt,HttpSession session)
    {

        System.out.println("상품"+item+"총가격"+totalPrice+"상품갯수"+totalCnt);
        Purchase response = purchaseService.paymentKakaoPay(item,totalPrice,totalCnt);
        session.setAttribute("purchase",response);

        return response;
    }

    @PostMapping("/refund")
    public void refund(@RequestParam String books, @AuthenticationPrincipal PrincipalDetails principalDetails)
    {
        User user = principalDetails.getUser();
        List<String> bookList = Arrays.asList(books.split(","));
        List<Book> bookLists = studentService.findByIds(bookList);
        List<Long> bookIds = new ArrayList<>();
        int totalPrice = 0;
        for(Book book : bookLists)
        {
            bookIds.add(book.getId());
            int price = Integer.parseInt(book.getPrice());
            totalPrice += price;
        }
        System.out.println("bookLists"+bookLists);
        System.out.println("totalPrice"+totalPrice);


    }

    @GetMapping("/success")
    public String success(HttpSession session)
    {
        List<Book> bookList = (List<Book>)session.getAttribute("books");
        User user = (User) session.getAttribute("users");
        String receiveDay =(String)session.getAttribute("receiveDate");
        int useMileage = Integer.parseInt((String)session.getAttribute("mileagePoint"));
        Purchase purchase = (Purchase) session.getAttribute("purchase");

        studentService.createReceipt(bookList,user,receiveDay,useMileage,purchase.getTid());


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
