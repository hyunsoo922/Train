package com.project.LWBS.service;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.repository.BookRepository;
import com.project.LWBS.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    @Override
    public void saveReceipts(List<Receipt> receipts) {

    }

    @Override
    public List<Receipt> findReceiptsByStudentId(String studentId) {
        return receiptRepository.findByUserStudentId(studentId);
    }

    @Override
    public void findById(long Id, Receive receive) {
        Receipt receipt = receiptRepository.findById(Id).orElse(null);
        receipt.setReceive(receive);
        receiptRepository.flush();
    }

    @Override
    // Receipt 테이블에서 가장 많이 팔린 책의 id 값과 개수를 Map 리스트로 만드는 메서드
    public List<Map<String, Object>> ranking() {
        List<Map<String, Object>> rankingList = receiptRepository.findTopBookIds();
        return rankingList;
    }
    @Override
    public List<Map<String, Object>> statistics() {
        List<Map<String, Object>> statistics = receiptRepository.findTopBookIds();
        return statistics;
    }
}
