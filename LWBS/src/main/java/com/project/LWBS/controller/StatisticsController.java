package com.project.LWBS.controller;

import com.project.LWBS.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookStore")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }

    @GetMapping("/Statistics")
    public String countItems(@RequestParam Long bookId, Model model){
        long itemCount = statisticsService.countBookId(bookId);
        model.addAttribute("itemCount", itemCount);
        return "bookStore/Statistics";
    }
}

