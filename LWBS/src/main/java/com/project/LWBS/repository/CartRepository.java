package com.project.LWBS.repository;
import com.project.LWBS.domain.Cart;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    void deleteAllByUserId(Long user_id);
}
