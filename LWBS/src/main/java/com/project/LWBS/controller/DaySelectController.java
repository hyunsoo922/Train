package com.project.LWBS.controller;

import com.project.LWBS.service.ReceiveService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class DaySelectController {
    private ReceiveService receiveService;
    @GetMapping("/DaySelect")
    public String showDaySelect() {
        return "dateRangePicker";
    }

    @PostMapping("/DaySelect")
    public String submitDaySelect(

            Model model,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        LocalDate defaultStartDate = LocalDate.now().minusDays(30);
        LocalDate defaultEndDate = LocalDate.now();


        LocalDate selectedStartDate = (startDate != null) ? startDate : defaultStartDate;
        LocalDate selectedEndDate = (endDate != null) ? endDate : defaultEndDate;

        long numberOfDays = ChronoUnit.DAYS.between(selectedStartDate, selectedEndDate);

        model.addAttribute("defaultStartDate", defaultStartDate);
        model.addAttribute("defaultEndDate", defaultEndDate);
        model.addAttribute("selectedStartDate", selectedStartDate);
        model.addAttribute("selectedEndDate", selectedEndDate);
        model.addAttribute("numberOfDays", numberOfDays);



        return "result";
    }
}
