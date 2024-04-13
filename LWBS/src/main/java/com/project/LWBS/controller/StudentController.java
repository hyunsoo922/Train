package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.*;
import com.project.LWBS.service.CartService;
import com.project.LWBS.service.MyPageService;
import com.project.LWBS.service.StudentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;
    private MyPageService myPageService;
    private CartService cartService;

    @Autowired
    public StudentController(StudentService studentService, MyPageService myPageService, CartService cartService) {
        this.studentService = studentService;
        this.myPageService = myPageService;
        this.cartService = cartService;
    }

    @GetMapping("/purchase/book")
    public void getBook(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails)
    {
            User user = principalDetails.getUser();
            model.addAttribute("user",user);
            int sum = myPageService.sumMileage(user.getId());

            //List<Book> bookList = studentService.findMyClass(user);
            List<Book> bookList = cartService.findById(user.getId());
            System.out.println(bookList);

            List<Cart> cartList = cartService.findByUserId(user.getId());
//            System.out.println(bookList);

            List<Receive> receiveList = studentService.findReceiveCheck();

            model.addAttribute("bookList", bookList);
            model.addAttribute("cartList", cartList);
            model.addAttribute("receiveDays",receiveList);
            model.addAttribute("sumpoint", sum);

    }

    @PostMapping("/purchase/book")
    public String postBook(@RequestParam String receiveDay, @RequestParam String books, @RequestParam String mileagePoint, @AuthenticationPrincipal PrincipalDetails principalDetails, HttpSession session, HttpServletResponse response) {
        // 문자열을 다시 배열로 변환
        System.out.println("책들 : " + books);
        session.setAttribute("user",principalDetails.getUser());
        List<String> bookList = Arrays.asList(books.split(","));

        List<Book> bookLists = studentService.findByIds(bookList);


        session.setAttribute("bookLists",bookLists);
        session.setAttribute("receiveDay",receiveDay);
        session.setAttribute("mileagePoint",mileagePoint);
        Cookie cookie = new Cookie("mileagePoint", mileagePoint);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/student/purchase/bookPay";

    }
    @GetMapping("/purchase/bookPay")
    public String bookPay(Model model, HttpSession session, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Book> bookList = (List<Book>) session.getAttribute("bookLists");
        int totalPrice = 0;
        int totalCnt = bookList.size();

        Map<Book, Integer> bookCountMap = new HashMap<>();

        for (Book book : bookList) {
            int price = Integer.parseInt(book.getPrice());
            totalPrice += price;

            if (bookCountMap.containsKey(book)) {
                int count = bookCountMap.get(book);
                bookCountMap.put(book, count + 1);
            } else {
                bookCountMap.put(book, 1);
            }
        }

        int point = Integer.parseInt((String) session.getAttribute("mileagePoint"));
        totalPrice -= point;
        String receiveDay = (String) session.getAttribute("receiveDay");

        String item = bookList.get(0).getName() + "외" + (bookList.size() - 1) + "권";

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(bookCountMap);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        model.addAttribute("user", principalDetails.getUser());
        model.addAttribute("bookCountMap", bookCountMap);
        model.addAttribute("receiveDay", receiveDay);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCnt", totalCnt);
        model.addAttribute("item", item);
        model.addAttribute("point", point);
        session.setAttribute("books", bookList);
        session.setAttribute("receiveDate", receiveDay);
        session.setAttribute("users", principalDetails.getUser());

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
    public void postReceipt(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @RequestParam String day) {
        // 현재 사용자 정보를 모델에 추가
        model.addAttribute("user", principalDetails.getUser());

        // 사용자의 수령 날짜를 업데이트
        studentService.updateDay(principalDetails.getUser(), day);

        // 날짜를 업데이트한 후 구매 내역이 없다면 메시지를 모델에 추가하고 해당 페이지 반환
        List<Receipt> updatedReceiptList = studentService.findAllUser(principalDetails.getUser());
        if (updatedReceiptList.isEmpty()) {
            model.addAttribute("noReceipt", "구매 내역이 없습니다.");
        }
    }
}