package com.project.LWBS.service;

import com.project.LWBS.domain.Book;

import java.util.List;


public interface BookService {
    List<Book> getAllBooks();


    //Book createBook(String title, String author, String publisher, String price, String imageUrl, String isbn, String description);

    void createBook(String title, String author, String publisher, String price, String imageUrl, String isbn, String description, String dname, String sname);

    Boolean isExistIsbn(String isbn);

}
