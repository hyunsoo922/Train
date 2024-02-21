package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping()
public class HomeStudentController {
    private static BookService bookService;
    @Autowired
    public HomeStudentController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home/student")
    public String hello(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        // 현재 로그인 중인 유저의 id값을 매개변수로 전달하여 유저가 수강신청한 강의의 교재명 검색 후
        // 교재명에 맞는 Book 객체들을 리스트로 전달 받음.
        List<Book> bookList = bookService.findByBookName(principalDetails.getUser().getId());
        // Model 객체에 Book 리스트와 User 객체를 담아 View에게 전달
        model.addAttribute("bookList", bookList);
        model.addAttribute("user",principalDetails.getUser());
        // /home/student 경로를 반환
        return "home/student";
    }
}
