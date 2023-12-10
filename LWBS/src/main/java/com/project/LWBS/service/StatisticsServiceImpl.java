package com.project.LWBS.service;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public List<Receipt> findAll() {
        return receiptRepository.findAll();
    }

    @Override
    public Map<String, Integer> count(List<Receipt> receiptList, List<String> nameList) {
        // 중복된 코드를 방지하기 위해 nameList 초기화를 이동

        Map<String, Integer> countMap = new HashMap<>();

        // 각 서적별 판매량을 계산하여 countMap에 저장합니다.
        for (String name : nameList) {
            int count = 0;
            System.out.println("nameList"+name);
            for (Receipt receipt : receiptList) {
                System.out.println("receipt"+receipt.getBook().getName());
                if (receipt.getBook().getName().equals(name)) {
                    count++;
                }
            }
            countMap.put(name, count);
        }
        System.out.println("countmap"+countMap);

        return countMap;
    }
}
