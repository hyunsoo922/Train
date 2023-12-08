package com.project.LWBS.repository;

import com.project.LWBS.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsRepository extends JpaRepository <Receipt, Long>{
    long countAll();

    @Query("SELECT r.book.id, COUNT(r) FROM Receipt r GROUP BY r.book.id")//GPT가 알려줬는데 뭐라는지 모르곘음
    List<Object[]> countByBook(); // 각 책에 대한 개수를 세는 메서드
}
