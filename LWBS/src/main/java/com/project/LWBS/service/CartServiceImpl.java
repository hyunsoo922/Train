package com.project.LWBS.service;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Cart;
import com.project.LWBS.domain.User;
import com.project.LWBS.repository.BookRepository;
import com.project.LWBS.repository.CartRepository;
import com.project.LWBS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createCart(Long user_id, Long book_id) {
        User user = userRepository.findById(user_id).orElse(null);
        Book book = bookRepository.findById(book_id).orElse(null);
        Cart cart = Cart.builder()
                .user(user)
                .book(book)
                .build();
        cartRepository.saveAndFlush(cart);
    }

    @Override
    public List<Book> findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        List<Book> bookList = new ArrayList<>();
        List<Cart> cartList = cartRepository.findByUser(user);
        for (Cart c: cartList) {
            bookList.add(c.getBook());
        }
        return bookList;
    }

    @Override
    public List<Cart> findByUserId(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        List<Cart> cartList = cartRepository.findByUser(user);
        return cartList;
    }

    @Override
    @Transactional
    public void deleteByCart(Long cart_id) {
        Cart cart = cartRepository.findById(cart_id).orElse(null);


            cartRepository.deleteById(cart_id);

    }


//    @Override
//    public void delete(Long id) {
//        cartRepository.deleteById(id);
//    }

    @Override
    public void deleteCart(Long user_id) {
        cartRepository.deleteAllByUserId(user_id);
    }
}