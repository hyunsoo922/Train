package com.project.LWBS.service;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


        Map<String, Integer> countMap = new HashMap<>();

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


        return countMap;
    }
}
