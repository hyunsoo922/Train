package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.service.BookService;
import com.project.LWBS.service.ReceiptService;
import com.project.LWBS.service.ReceiptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class HomeBookStoreController {
    private static ReceiptService receiptService;
    private static BookService bookService;
    @Autowired
    public HomeBookStoreController(ReceiptService receiptService, BookService bookService) {
        this.receiptService = receiptService;
        this.bookService = bookService;
    }
    @GetMapping("/home/bookStore")
    public void hello(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Map<String, Object>> rankingList = receiptService.ranking();
        List<Book> bookList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (Map<String, Object> map : rankingList) {
            Long bookId = (Long) map.get("book_id");
            int count = ((Number) map.get("count")).intValue(); // assuming count is stored as a Number in the map
            countList.add(count);

            Book book = bookService.findById(bookId);
            bookList.add(book);
        }

        model.addAttribute("bookList", bookList);
        model.addAttribute("countList", countList);
        model.addAttribute("user",principalDetails.getUser());
    }
}
