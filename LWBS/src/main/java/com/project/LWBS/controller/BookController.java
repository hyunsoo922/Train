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
@RequestMapping("/bookStore")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //모든 교재 목록 표시
    @GetMapping("/list")
    public String getAllBooks(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
       //BookService를 사용해서 모든 교재 표시
        List<Book> books = bookService.getAllBooks();
        //모델에 사용자, 책 목록 속성 추가
        model.addAttribute("user",principalDetails.getUser());
        model.addAttribute("books", books);

        return "bookStore/list";
    }
}
