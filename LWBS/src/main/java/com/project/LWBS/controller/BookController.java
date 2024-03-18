package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.service.BookService;
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

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //모든 교재 목록 표시
    @GetMapping("/list")
    public String getAllBooks(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails,
                              @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Book> books = bookService.getAllBooks(pageable);

        int nowPage = books.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 7, books.getTotalPages());

        model.addAttribute("user", principalDetails.getUser());
        model.addAttribute("books", books);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sortProperty", pageable.getSort().toString());

        return "bookStore/list";
    }

    @GetMapping("/listSearch")
    public String searchByDepartment(@RequestParam("department") String departmentName,
                                     @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
                                     Model model) {

        Page<Book> searchResults = bookService.findByDepartmentName(departmentName, pageable);


        int nowPage = searchResults.getNumber() + 1; // 현재 페이지
        int startPage = Math.max(nowPage - 2, 1); // 시작 페이지
        int endPage = Math.min(nowPage + 3, searchResults.getTotalPages()); // 끝 페이지

        // 모델에 데이터 추가
        model.addAttribute("books", searchResults);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sortProperty", pageable.getSort().toString());

        return "bookStore/listSearch";
    }


}
