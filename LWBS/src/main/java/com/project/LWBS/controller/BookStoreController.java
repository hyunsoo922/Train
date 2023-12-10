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

    @GetMapping("/DaySelect")
    public void showDaySelect(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("user",principalDetails.getUser());
    }

    @PostMapping("/DaySelect")
    public String submitDaySelect(

            Model model,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        LocalDate defaultStartDate = LocalDate.now().minusDays(30);
        LocalDate defaultEndDate = LocalDate.now();


        LocalDate selectedStartDate = (startDate != null) ? startDate : defaultStartDate;
        LocalDate selectedEndDate = (endDate != null) ? endDate : defaultEndDate;

        long numberOfDays = ChronoUnit.DAYS.between(selectedStartDate, selectedEndDate)+1;

        model.addAttribute("defaultStartDate", defaultStartDate);
        model.addAttribute("defaultEndDate", defaultEndDate);
        model.addAttribute("selectedStartDate", selectedStartDate);
        model.addAttribute("selectedEndDate", selectedEndDate);
        model.addAttribute("numberOfDays", numberOfDays);
        model.addAttribute("user",principalDetails.getUser());

        receiveService.createReceive(startDate, endDate, numberOfDays);


        return "bookStore/DaySelectResult";
    }



}


