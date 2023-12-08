package com.project.LWBS.repository;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

    List<Receipt> findByUser(User user);
}
