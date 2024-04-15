package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.service.BookService;
import com.project.LWBS.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class    HomeController {
    private static BookService bookService;
    private static ReceiptService receiptService;

    @Autowired
    public HomeController(ReceiptService receiptService, BookService bookService) {
        this.receiptService = receiptService;
        this.bookService = bookService;
    }
    @RequestMapping("/")
    public String home(){
        return "redirect:home";
    }

    @RequestMapping("/home")
    // 학생 로그인 후 /home/student 페이지에서 출력할 교재들을 전달하는 메서드
    public String home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<Map<String, Object>> rankingList = receiptService.ranking();
        List<Book> bookList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (Map<String, Object> map : rankingList) {
            // map에서 판매량이 가장 많은(1위~4위) 책들의 id를 추출
            Long bookId = (Long) map.get("book_id");
            // 판매량을 추출
            int count = ((Number) map.get("count")).intValue();
            countList.add(count);

            // 판매량이 가장 많은 책들의 정보를 찾아
            Book book = bookService.findById(bookId);
            // bookList에 저장
            bookList.add(book);
        }
        // View에게 bookList를 전달
        model.addAttribute("bookList", bookList);
        model.addAttribute("countList", countList);
        try{
            System.out.println("홈에 들어옴 로그인"+principalDetails.getUsername());
            model.addAttribute("user",principalDetails.getUser());
            // 현재 로그인한 유저가 학생 계정이라면
            if(principalDetails.getUser().getAuthority().getName().equals("ROLE_STUDENT")) {
                return "redirect:home/student";
            }
            // 서점 계정이라면
            else if(principalDetails.getUser().getAuthority().getName().equals("ROLE_BOOKSTORE")) {
                return "redirect:home/bookStore";
            }
        } catch (Exception e){
            System.out.println("로그인실패");
            model.addAttribute("user", null);
        }
        return "home";
    }

    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
