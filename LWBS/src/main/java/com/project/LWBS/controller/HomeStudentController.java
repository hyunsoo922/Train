package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.service.BookService;
import com.project.LWBS.service.EnrollmentService;
import com.project.LWBS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping()
public class HomeStudentController {
    //private static EnrollmentService enrollmentService;
    private static BookService bookService;
    //private static UserService userService;
    @Autowired
    public HomeStudentController(BookService bookService) {
        //this.enrollmentService = enrollmentService;
        this.bookService = bookService;
        //this.userService = userService;
    }

    @GetMapping("/home/student/{user_id}")
    public String hello(@PathVariable Long user_id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<Book> bookList = bookService.findByBookName(user_id);
        model.addAttribute("bookList", bookList);
        model.addAttribute("user",principalDetails.getUser());
        return "/home/student";
    }
}
