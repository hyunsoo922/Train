package com.project.LWBS.service;
import com.project.LWBS.domain.Cart;
import com.project.LWBS.domain.Department;
import com.project.LWBS.domain.Subject;

import com.project.LWBS.domain.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartService {
    void createCart(Long user_id, Long book_id);
    List<Book> findById(Long id);

    List<Cart> findByUserId(Long user_id);

//    void delete(Long id);

    void deleteByCart(Long cart_id);

    @Transactional
    void deleteCart(Long user_id);
}
