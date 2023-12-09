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

        // 여기서 아래 코드는 모호한 부분이 있어서 주석 처리했습니다.
        // model, AbstractMutableCollection 등의 부분은 주어진 코드에서 충분한 정보가 없어서 적절한 처리를 할 수 없습니다.
        // 이 부분에 대한 수정이 필요하다면 해당 부분의 역할과 사용 목적을 자세히 알려주시면 도움을 드릴 수 있습니다.

        // List<Integer> count = new ArrayList<>();
        // count.add(countMap.get(nameList.get(i)));
        // model.add("bookList");
        // model.add("countList");

        return countMap;
    }
}
