package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.service.BookService;
import com.project.LWBS.service.ReceiptService;
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
@RequestMapping("/bookStore")
public class StatisticsController {

    private final ReceiptService receiptService;
    private final BookService bookService;

    @Autowired
    public StatisticsController(ReceiptService receiptService, BookService bookService) {
        this.receiptService = receiptService;
        this.bookService = bookService;
    }

    @GetMapping("/Statistics")
    public void statistics(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Map<String, Object>> statisticsList = receiptService.statistics();
        List<Book> bookList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (Map<String, Object> map : statisticsList) {
            Long bookId = (Long) map.get("book_id");
            Book book = bookService.findById(bookId);
            bookList.add(book);
            int count = ((Number) map.get("count")).intValue();
             countList.add(count);
        }
        model.addAttribute("bookList", bookList);
        model.addAttribute("countList", countList);
        model.addAttribute("user",principalDetails.getUser());

    }
}
