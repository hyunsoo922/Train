package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.service.BookStoreService;
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

@Controller
@RequestMapping("/bookStore")
public class BookStoreController {

    private BookStoreService Book;
    private ReceiveService receiveService;
    @Autowired
    public BookStoreController(BookStoreService book, ReceiveService receiveService) {
        Book = book;
        this.receiveService = receiveService;
    }
    //날짜 선택 화면을 표시
    @GetMapping("/DaySelect")
    public void showDaySelect(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("user",principalDetails.getUser());
    }

    //날짜 선택 결과를 제출
    @PostMapping("/DaySelect")
    public String submitDaySelect(

            Model model,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        //입력값이 없을 경우 범위를 10일로 설정
        LocalDate defaultStartDate = LocalDate.now().minusDays(10);
        LocalDate defaultEndDate = LocalDate.now();

        //입력값이 없을경우 기본값 설정한 10일을 사용
        LocalDate selectedStartDate = (startDate != null) ? startDate : defaultStartDate;
        LocalDate selectedEndDate = (endDate != null) ? endDate : defaultEndDate;
        //선택한 날짜 사이 일수 계산
        long numberOfDays = ChronoUnit.DAYS.between(selectedStartDate, selectedEndDate)+1;
        //모델 속성 추가
        model.addAttribute("defaultStartDate", defaultStartDate);
        model.addAttribute("defaultEndDate", defaultEndDate);
        model.addAttribute("selectedStartDate", selectedStartDate);
        model.addAttribute("selectedEndDate", selectedEndDate);
        model.addAttribute("numberOfDays", numberOfDays);
        model.addAttribute("user",principalDetails.getUser());
        //ReceiveService를 사용하여 날짜 범위에 해당하는 데이터 생성
        receiveService.createReceive(startDate, endDate, numberOfDays);

        return "bookStore/DaySelectResult";
    }



}


