package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Department;
import com.project.LWBS.domain.Subject;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.BookService;
import com.project.LWBS.service.CartService;
import com.project.LWBS.service.CartServiceImpl;
import com.project.LWBS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping()
public class CartController {
    private BookService bookService;
    private CartService cartService;
    private UserService userService;

    @Autowired
    public CartController(BookService bookService, CartService cartService, UserService userService) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.userService = userService;
    }
    @GetMapping("/cart")
    public String shopping(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam("bookID") String bookid) {
        Long user_id = principalDetails.getUser().getId();
        Long book_id = Long.valueOf(bookid);

        cartService.createCart(user_id, book_id);

        return "redirect:/home/student";
    }
    @GetMapping("/student/purchase/delete")
    public String delete(@RequestParam("cartId") String cartId) {

        Long cart_id = Long.valueOf(cartId);

        cartService.delete(cart_id);

        return "redirect:/student/purchase/book";
    }
}
