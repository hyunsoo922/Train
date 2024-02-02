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
    public String getBookSalesCount(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Book> allBooks = bookService.getAllBooks();  // 전체 교재 목록을 가져오기
        List<Map<String, Object>> statisticsList = receiptService.statistics();

        List<Book> bookList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        for (Book book : allBooks) {
            Long bookId = book.getId();
            int count = findCountForBook(bookId, statisticsList);  // 해당 교재에 대한 판매량 찾기
            countList.add(count);
            bookList.add(book);
        }

        model.addAttribute("bookList", bookList);
        model.addAttribute("countList", countList);
        model.addAttribute("user", principalDetails.getUser());

        return "bookStore/Statistics";
    }

    private int findCountForBook(Long bookId, List<Map<String, Object>> statisticsList) {
        for (Map<String, Object> map : statisticsList) {
            Long currentBookId = (Long) map.get("book_id");
            if (bookId.equals(currentBookId)) {
                return ((Number) map.get("count")).intValue();
            }
        }
        return 0;  // 해당 교재에 대한 판매량이 없으면 0으로 처리
    }
}
