package com.project.LWBS.repository;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    static void save(Receive receive) {
    }

    List<Receipt> findAllByUser(User user);

    List<Receipt> findAllByBook(Book book);

    List<Receipt> findAllByReceive(Receive receive);


    List<Receipt> findByUserStudentId(String studentId);
}
