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
    public void shopping(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam("bookID") String bookid) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(bookid);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Long user_id = principalDetails.getUser().getId();
        Long book_id = Long.valueOf(bookid);
        System.out.println(principalDetails.getUser().getName());
        System.out.println(book_id);
        cartService.createCart(user_id, book_id);
    }
    @GetMapping("/student/purchase/delete")
    public String delete(@RequestParam("cartId") String cartId) {

        Long cart_id = Long.valueOf(cartId);

        cartService.deleteByCart(cart_id);

        return "redirect:/student/purchase/book";
    }
}