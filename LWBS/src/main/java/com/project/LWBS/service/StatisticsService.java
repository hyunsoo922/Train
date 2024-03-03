package com.project.LWBS.service;

import com.project.LWBS.domain.Department;
import com.project.LWBS.domain.Receipt;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    List <Receipt> findAll();

    Map<String, Integer> count(List<Receipt> receiptList, List<String> nameList);

}
