package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.MyPageService;
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
    private MyPageService myPageService;

    @Autowired
    public StudentController(StudentService studentService, MyPageService myPageService) {
        this.studentService = studentService;
        this.myPageService = myPageService;
    }

    @GetMapping("/purchase/book")
    public void getBook(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails)
    {
            User user = principalDetails.getUser();
            model.addAttribute("user",user);
            int sum = myPageService.sumMileage(user.getId());

            List<Book> bookList = studentService.findMyClass(user);

//            System.out.println(bookList);

            List<Receive> receiveList = studentService.findReceiveCheck();

            model.addAttribute("bookList", bookList);
            model.addAttribute("receiveDays",receiveList);
            model.addAttribute("sumpoint", sum);

    }

    @PostMapping("/purchase/book")
    public String postBook(@RequestParam String receiveDay, @RequestParam String books,@RequestParam String mileagePoint,@AuthenticationPrincipal PrincipalDetails principalDetails,HttpSession session) {
        // 문자열을 다시 배열로 변환
        session.setAttribute("user",principalDetails.getUser());
        List<String> bookList = Arrays.asList(books.split(","));

        List<Book> bookLists = studentService.findByIds(bookList);

//        System.out.println("수령일: " + receiveDay);
//        System.out.println("교재: " + bookLists);
        session.setAttribute("bookLists",bookLists);
        session.setAttribute("receiveDay",receiveDay);
        session.setAttribute("mileagePoint",mileagePoint);
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
    public String receipt(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        // 현재 사용자 정보를 모델에 추가
        model.addAttribute("user", principalDetails.getUser());

        // 사용자의 구매 내역을 조회
        List<Receipt> receiptList = studentService.findAllUser(principalDetails.getUser());

        // 만약 구매 내역이 없다면 메시지를 모델에 추가하고 해당 페이지 반환
        if (receiptList.isEmpty()) {
            model.addAttribute("noReceipt", "구매 내역이 없습니다.");
            return "student/purchase/receipt";
        }

        // 구매 내역이 있다면 책 목록을 생성하여 모델에 추가
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < receiptList.size(); i++) {
            bookList.add(receiptList.get(i).getBook());
        }
        model.addAttribute("bookList", bookList);

        // 첫 번째 영수증의 수령 날짜와 확인 여부를 모델에 추가
        model.addAttribute("receiveDay", receiptList.get(0).getReceive().getDay());
        model.addAttribute("receiveCheck", receiptList.get(0).getReceive().getReceiveCheck());

        // 수령 가능한 일자 목록을 모델에 추가
        model.addAttribute("daySelect", studentService.findReceiveCheck());

        // 구매 내역이 있는 경우 해당 페이지 반환
        return "student/purchase/receipt";
    }

    @PostMapping("/purchase/receipt")
    public String postReceipt(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @RequestParam String day) {
        // 현재 사용자 정보를 모델에 추가
        model.addAttribute("user", principalDetails.getUser());

        // 사용자의 수령 날짜를 업데이트
        studentService.updateDay(principalDetails.getUser(), day);

        // 날짜를 업데이트한 후 구매 내역이 없다면 메시지를 모델에 추가하고 해당 페이지 반환
        List<Receipt> updatedReceiptList = studentService.findAllUser(principalDetails.getUser());
        if (updatedReceiptList.isEmpty()) {
            model.addAttribute("noReceipt", "구매 내역이 없습니다.");
            return "student/purchase/receipt";
        }

        // 구매 내역이 있는 경우 해당 페이지로 리다이렉트
        return "redirect:/student/purchase/receipt";
    }
}