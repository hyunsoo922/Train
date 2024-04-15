package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Department;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.BookService;
import com.project.LWBS.service.MyPageService;
import com.project.LWBS.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
public class BookController {
    private static BookService bookService;
    private static StudentService studentService;
    private MyPageService mypageService;

    @Autowired
    public BookController(BookService bookService, StudentService studentService, MyPageService mypageService) {

        this.bookService = bookService;
        this.studentService = studentService;
        this.mypageService = mypageService;
    }

    //모든 교재 목록 표시
    @GetMapping("/list")
    public String getAllBooks(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

         // 현재 로그인 중인 유저의 id값을 매개변수로 전달하여 유저가 수강신청한 강의의 교재명 검색 후
        // 교재명에 맞는 Book 객체들을 리스트로 전달 받음.
        User user = principalDetails.getUser();
        int sumMileage = mypageService.sumMileage(user.getId());
        List<Receipt> receiptList = studentService.findAllUser(principalDetails.getUser());
        model.addAttribute("user",user);
        model.addAttribute("mileage", sumMileage);
        List<Book> bookList = bookService.getAllBook();
        List<Department> departmentList = bookService.getAllDepartments();

        // Model 객체에 Book 리스트와 User 객체를 담아 View에게 전달
        model.addAttribute("bookList", bookList);
        model.addAttribute("departmentList", departmentList);
        // 첫 번째 영수증의 수령 날짜와 확인 여부를 모델에 추가
        if (!receiptList.isEmpty()) {
            model.addAttribute("receiveDay", receiptList.get(0).getReceive().getDay());
        } else {
            model.addAttribute("receiveDay", "구매하신 교재가 없습니다."); // 또는 다른 처리를 수행할 수 있음
        }
        // 수령 가능한 일자 목록을 모델에 추가
        model.addAttribute("daySelect", studentService.findReceiveCheck());

        //model.addAttribute("user",principalDetails.getUser());
        // /home/student 경로를 반환

        return "bookStore/list";
    }




}
