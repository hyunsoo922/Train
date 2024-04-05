package com.project.LWBS.service;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;
import com.project.LWBS.repository.BookRepository;
import com.project.LWBS.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@Service
public  class ReceiptServiceImpl implements  ReceiptService{


    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Page<Receipt> getAllReceipts(Pageable pageable) {
        return receiptRepository.findAll(pageable);
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

    }

    @Override
    public void updateReceipt(long Id, Receive receive) {
        Receipt receipt = receiptRepository.findById(Id).orElse(null);

        if (receipt != null) {
            // Receive 객체의 필드들을 동적으로 순회
            for (Field field : Receive.class.getDeclaredFields()) {
                field.setAccessible(true); // private 필드에 접근 가능하도록 설정

                try {
                    // Receive 객체에서 각 필드의 값을 가져와서 Receipt 객체에 설정
                    Object value = field.get(receive);
                    field.set(receipt, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace(); // 필드에 접근할 수 없는 경우 예외 처리
                }
            }

            // 업데이트된 Receipt 객체 저장
            receiptRepository.save(receipt);
        }
    }

    @Override
    // Receipt 테이블에서 가장 많이 팔린 책의 id 값과 개수를 Map 리스트로 만드는 메서드
    public List<Map<String, Object>> ranking() {
        List<Map<String, Object>> rankingList = receiptRepository.findTopBookIds();
        return rankingList;
    }

    @Override
    public List<Map<String, Object>> statistics() {
        List<Map<String, Object>> statisticsList = receiptRepository.findBookIds();
        return statisticsList;
    }

    @Override
    public Receipt findReceiptByBookAndUser(Book book, User user) {

        return receiptRepository.findByBookAndUser(book,user);
    }

    @Override
    @Transactional
    public void deleteReceipt(Receipt receipt) {
        receiptRepository.delete(receipt);

        receiptRepository.flush();
    }

    @Override
    public List<Receipt> findByTid(String tid) {

        return receiptRepository.findByTid(tid);
    }

}
