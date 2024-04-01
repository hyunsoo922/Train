package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.service.BookService;
import com.project.LWBS.service.BookStoreService;
import com.project.LWBS.service.ReceiptService;
import com.project.LWBS.service.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class HomeBookStoreController {
    private static ReceiptService receiptService;
    private static BookService bookService;
    private BookStoreService bookStoreService;
    private ReceiveService receiveService;
    @Autowired
    public HomeBookStoreController(ReceiptService receiptService, BookService bookService, BookStoreService bookStoreService, ReceiveService receiveService) {
        this.receiptService = receiptService;
        this.bookService = bookService;
        this.bookStoreService = bookStoreService;
        this.receiveService = receiveService;
    }
    @GetMapping("/home/bookStore")
    public void hello(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 가장 많이 팔린 책 리스트
        List<Map<String, Object>> rankingList = receiptService.ranking();
        // 책의 id로 검색한 Book 객체를 담을 리스트
        List<Book> bookList = new ArrayList<>();
        // 순위 정보를 담을 리스트
        List<Integer> countList = new ArrayList<>();
        for (Map<String, Object> map : rankingList) {
            // Map 타입 객체 map으로 rangkingList를 순회하며
            // 책의 id 값을 추출
            Long bookId = (Long) map.get("book_id");
            // 추출한 id 값에 해당하는 Book 객체를 검색
            Book book = bookService.findById(bookId);
            // 검색한 Book 객체를 bookList에 저장
            bookList.add(book);
            // 순위 정보를 추출
            int count = ((Number) map.get("count")).intValue();
            // 순위 정보를 countList에 추가
            countList.add(count);
        }
        // 책 정보와 순위 정보, 현재 로그인 중인 user의 정보를 view에게 전달
        model.addAttribute("bookList", bookList);
        model.addAttribute("countList", countList);
        model.addAttribute("user",principalDetails.getUser());
    }

    @PostMapping("/home/bookStore")
    public String submitDaySelect(
            Model model,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        LocalDate defaultStartDate = LocalDate.now().minusDays(10);
        LocalDate defaultEndDate = LocalDate.now();

        LocalDate selectedStartDate = (startDate != null) ? startDate : defaultStartDate;
        LocalDate selectedEndDate = (endDate != null) ? endDate : defaultEndDate;

        long numberOfDays = ChronoUnit.DAYS.between(selectedStartDate, selectedEndDate) + 1;

        model.addAttribute("defaultStartDate", defaultStartDate);
        model.addAttribute("defaultEndDate", defaultEndDate);
        model.addAttribute("selectedStartDate", selectedStartDate);
        model.addAttribute("selectedEndDate", selectedEndDate);
        model.addAttribute("numberOfDays", numberOfDays);
        model.addAttribute("user", principalDetails.getUser());

        receiveService.createReceive(startDate, endDate, numberOfDays);

        // 가장 많이 팔린 책 리스트
        List<Map<String, Object>> rankingList = receiptService.ranking();
        // 책의 id로 검색한 Book 객체를 담을 리스트
        List<Book> bookList = new ArrayList<>();
        // 순위 정보를 담을 리스트
        List<Integer> countList = new ArrayList<>();
        for (Map<String, Object> map : rankingList) {
            // Map 타입 객체 map으로 rangkingList를 순회하며
            // 책의 id 값을 추출
            Long bookId = (Long) map.get("book_id");
            // 추출한 id 값에 해당하는 Book 객체를 검색
            Book book = bookService.findById(bookId);
            // 검색한 Book 객체를 bookList에 저장
            bookList.add(book);
            // 순위 정보를 추출
            int count = ((Number) map.get("count")).intValue();
            // 순위 정보를 countList에 추가
            countList.add(count);
        }
        // 책 정보와 순위 정보, 현재 로그인 중인 user의 정보를 view에게 전달
        model.addAttribute("bookList", bookList);
        model.addAttribute("countList", countList);
        model.addAttribute("user",principalDetails.getUser());

        return "/home/bookStore";
    }
}
