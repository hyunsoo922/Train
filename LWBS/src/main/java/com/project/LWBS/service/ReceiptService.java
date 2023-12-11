package com.project.LWBS.service;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import java.util.Map;
import java.util.List;

public interface ReceiptService {

    List<Receipt> getAllReceipts();
    void saveReceipts(List<Receipt> receipts);
    List<Receipt> findReceiptsByStudentId(String studentId);

    void findById(long Id, Receive receive);

    // Receipt 테이블에서 가장 많이 팔린 책의 id 값과 개수를 Map 리스트로 만드는 메서드
    List<Map<String, Object>> ranking();

    List<Map<String, Object>> statistics();
}
