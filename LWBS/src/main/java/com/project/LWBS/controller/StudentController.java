package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/purchase/book")
    public void getBook(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails)
    {
            User user = principalDetails.getUser();
            model.addAttribute("user",user);

            List<Book> bookList = studentService.findMyClass(user);

            System.out.println(bookList);

            List<Receive> receiveList = studentService.findReceiveCheck();

            model.addAttribute("bookList", bookList);
            model.addAttribute("receiveDays",receiveList);

    }

    @PostMapping("/purchase/book")
    public String postBook(@RequestParam String receiveDay, @RequestParam String books,@AuthenticationPrincipal PrincipalDetails principalDetails,HttpSession session) {
        // 문자열을 다시 배열로 변환
        session.setAttribute("user",principalDetails.getUser());
        List<String> bookList = Arrays.asList(books.split(","));

        List<Book> bookLists = studentService.findByIds(bookList);

        System.out.println("수령일: " + receiveDay);
        System.out.println("교재: " + bookLists);
        session.setAttribute("bookLists",bookLists);
        session.setAttribute("receiveDay",receiveDay);
//        model.addAttribute("bookLists",bookLists);
//        model.addAttribute("receiveDay",receiveDay);

        return "redirect:/student/purchase/bookPay";

    }

    @GetMapping("/purchase/bookPay")
    public String bookPay(Model model, HttpSession session, @AuthenticationPrincipal PrincipalDetails principalDetails)
    {
        List<Book> bookList = (List<Book>)session.getAttribute("bookLists");
        int totalPrice = 0;
        int totalCnt = bookList.size();

        for(int i = 0; i < bookList.size(); i++)
        {
            int price = Integer.parseInt(bookList.get(i).getPrice());
            totalPrice+=price;
        }

        String receiveDay = (String)session.getAttribute("receiveDay");

        String item = bookList.get(0).getName() + "외" + (bookList.size()-1) + "권";

        System.out.println("교재"+bookList);
        System.out.println("수령일"+receiveDay);
        model.addAttribute("user",principalDetails.getUser());
        model.addAttribute("bookList",bookList);
        model.addAttribute("receiveDay",receiveDay);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("totalCnt",totalCnt);
        model.addAttribute("item",item);
        session.setAttribute("books",bookList);
        session.setAttribute("receiveDate",receiveDay);
        session.setAttribute("users",principalDetails.getUser());

        return "student/purchase/bookPay";
    }

    @GetMapping("/purchase/receipt")
    public void complete(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model)
    {
        model.addAttribute("user",principalDetails.getUser());

    }



}
