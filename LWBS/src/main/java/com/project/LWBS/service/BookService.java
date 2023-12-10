package com.project.LWBS.service;

import com.project.LWBS.domain.Book;

public interface BookService {

    //Book createBook(String title, String author, String publisher, String price, String imageUrl, String isbn, String description);

    void createBook(String title, String author, String publisher, String price, String imageUrl, String isbn, String description, String dname, String sname);

    Boolean isExistIsbn(String isbn);

}