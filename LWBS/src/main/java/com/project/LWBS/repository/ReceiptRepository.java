package com.project.LWBS.repository;


import com.project.LWBS.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    @Query("SELECT r.book.id as book_id, COUNT(r.book.id) as count " +
            "FROM Receipt r " +
            "GROUP BY r.book.id " +
            "ORDER BY count DESC " +
            "LIMIT 4")
    List<Map<String, Object>> findTopBookIds();

    // 결과에서 book_id만 추출하는 기본 메서드도 정의할 수 있습니다
    default List<Long> findTopBookIdsList() {
        List<Map<String, Object>> result = findTopBookIds();
        List<Long> topBookIds = new ArrayList<>();
        for (Map<String, Object> row : result) {
            Long bookId = (Long) row.get("book_id");
        }
        return topBookIds;
    }
}
