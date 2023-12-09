package com.project.LWBS.service;

import com.project.LWBS.domain.Book;

import java.util.List;

public interface HomeStudendService {
    List<String> getBookName(Long user_id);

    List<Book> findByBookName(String bookname);
}
