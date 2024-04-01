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
    // Receipt 테이블에게 동일한 book_id 값을 갖는 레코드의 개수를 COUNT하여 내림차순으로 정렬하여 Map으로 만드는 메서드
    static void save(Receive receive) {
    }

    List<Receipt> findAllByUser(User user);

    List<Receipt> findAllByBook(Book book);

    List<Receipt> findAllByReceive(Receive receive);

    List<Receipt> findByUserStudentId(String studentId);

    Receipt findByBookAndUser(Book book,User user);

    @Query("SELECT r.book.id as book_id, COUNT(r.book.id) as count " +
            "FROM Receipt r " +
            "GROUP BY r.book.id " +
            "ORDER BY count DESC " +
            "LIMIT 5")
    List<Map<String, Object>> findTopBookIds();

    default List<Long> findTopBookIdsList() {
        List<Map<String, Object>> result = findTopBookIds();
        List<Long> topBookIds = new ArrayList<>();
        for (Map<String, Object> row : result) {
            Long bookId = (Long) row.get("book_id");
        }
        return topBookIds;
    }

    @Query("SELECT r.book.id as book_id, COUNT(r.book.id) as count " +
            "FROM Receipt r " +
            "GROUP BY r.book.id " +
            "ORDER BY count DESC ")
    List<Map<String, Object>> findBookIds();

    default List<Long> findBookIdsList() {
        List<Map<String, Object>> result = findTopBookIds();
        List<Long> BookIds = new ArrayList<>();
        for (Map<String, Object> row : result) {
            Long bookId = (Long) row.get("book_id");
        }
        return BookIds;
    }

    List<Receipt> findByUser(User user);

}
