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

    List<Map<String, Object>> ranking();
}
