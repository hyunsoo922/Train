package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/purchase/books")
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
    public String postBook(@RequestParam String receiveDay, @RequestParam String books,@RequestParam String mileagePoint,@AuthenticationPrincipal PrincipalDetails principalDetails,HttpSession session) {
        // 문자열을 다시 배열로 변환
        session.setAttribute("user",principalDetails.getUser());
        List<String> bookList = Arrays.asList(books.split(","));

        List<Book> bookLists = studentService.findByIds(bookList);

        session.setAttribute("bookLists",bookLists);
        session.setAttribute("receiveDay",receiveDay);
        session.setAttribute("mileagePoint",mileagePoint);

        return "redirect:student/purchase/bookPay";

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
        int point = Integer.parseInt((String)session.getAttribute("mileagePoint"));
        totalPrice-=point;
        String receiveDay = (String)session.getAttribute("receiveDay");

        String item = bookList.get(0).getName() + "외" + (bookList.size()-1) + "권";

        model.addAttribute("user",principalDetails.getUser());
        model.addAttribute("bookList",bookList);
        model.addAttribute("receiveDay",receiveDay);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("totalCnt",totalCnt);
        model.addAttribute("item",item);
        model.addAttribute("point",point);
        session.setAttribute("books",bookList);
        session.setAttribute("receiveDate",receiveDay);
        session.setAttribute("users",principalDetails.getUser());

        return "student/purchase/bookPay";
    }

    @GetMapping("/purchase/receipt")
    public void receipt(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model)
    {
        model.addAttribute("user",principalDetails.getUser());
        List<Receipt> receiptList = studentService.findAllUser(principalDetails.getUser());
        List<Book> bookList = new ArrayList<>();

        for (int i = 0; i < receiptList.size(); i++)
        {
            bookList.add(receiptList.get(i).getBook());
        }

        model.addAttribute("bookList",bookList);
        model.addAttribute("receiveDay",receiptList.get(0).getReceive().getDay());
        model.addAttribute("receiveCheck",receiptList.get(0).getReceive().getReceiveCheck());
        model.addAttribute("daySelect",studentService.findReceiveCheck());

    }

    @PostMapping("/purchase/receipt")
    public String postReceipt(@AuthenticationPrincipal PrincipalDetails principalDetails,Model model,@RequestParam String day)
    {
        model.addAttribute("user",principalDetails.getUser());

        studentService.updateDay(principalDetails.getUser(),day);

        return "redirect:student/purchase/receipt";

    }

}
