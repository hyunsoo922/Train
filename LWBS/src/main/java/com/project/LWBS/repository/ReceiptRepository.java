package com.project.LWBS.repository;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    static void save(Receive receive) {
    }

    List<Receipt> findAllByUser(User user);

    List<Receipt> findAllByBook(Book book);

    List<Receipt> findAllByReceive(Receive receive);

    List<Receipt> findByUserStudentId(String studentId);

    @Query("SELECT r.book.id as book_id, COUNT(r.book.id) as count " +
            "FROM Receipt r " +
            "GROUP BY r.book.id " +
            "ORDER BY count DESC " +
            "LIMIT 4")
    List<Map<String, Object>> findTopBookIds();

    default List<Long> findTopBookIdsList() {
        List<Map<String, Object>> result = findTopBookIds();
        List<Long> topBookIds = new ArrayList<>();
        for (Map<String, Object> row : result) {
            Long bookId = (Long) row.get("book_id");
        }
        return topBookIds;
    }

    List<Receipt> findByUser(User user);
}
