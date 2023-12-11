package com.project.LWBS.service;

import com.project.LWBS.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    void createBook(String title, String author, String publisher, String price, String imageUrl, String isbn, String description, String dname, String sname);

    Boolean isExistIsbn(String isbn);

    List<Book> findByBookName(Long user_id);

    Book findById(Long user_id);

}
