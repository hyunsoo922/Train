package com.project.LWBS.controller;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }


    @GetMapping("/Statistics")
    public String getBookSalesCount(Model model) {
        List<Receipt> receiptList = statisticsService.findAll();
        List<String> nameList = new ArrayList<>();  // 이 부분은 필요에 따라 초기화 방식을 변경할 수 있습니다.
        for (Receipt receipt : receiptList) {
            String bookName = receipt.getBook().getName();
            if (!nameList.contains(bookName)) {
                nameList.add(bookName);
            }
        }
        Map<String, Integer> bookSales = statisticsService.count(receiptList, nameList);
        List<Integer> countList = new ArrayList<>();
        for(int i = 0 ; i < nameList.size(); i++)
        {
            countList.add(bookSales.get(nameList.get(i)));
        }

        System.out.println("nameList"+nameList);
        System.out.println("countList"+countList);
        model.addAttribute("nameList",  nameList);
        model.addAttribute("countList", countList );
        return "bookStore/Statistics";
    }
}
