package com.project.LWBS.service;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.List;

public interface ReceiptService {



    Page<Receipt> getAllReceipts(Pageable pageable);

    void saveReceipts(List<Receipt> receipts);
    List<Receipt> findReceiptsByStudentId(String studentId);

    void findById(long Id, Receive receive);

    void updateReceipt(long Id, Receive receive);

    // Receipt 테이블에서 가장 많이 팔린 책의 id 값과 개수를 Map 리스트로 만드는 메서드
    List<Map<String, Object>> ranking();

    List<Map<String, Object>> statistics();

    List<Receipt> findReceiptByBookAndUser(Book book, User user);

    void deleteReceipt(Receipt receipt);

    List<Receipt> findByTid(String tid);
}
