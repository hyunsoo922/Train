package com.project.LWBS.service;
import com.project.LWBS.repository.BookRepository;
import com.project.LWBS.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Override
    public List<Map<String, Object>> ranking() {
        List<Map<String, Object>> rankingList = receiptRepository.findTopBookIds();
        return rankingList;
    }
}
