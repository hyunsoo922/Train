package com.project.LWBS.service;

import com.project.LWBS.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookStoreServiceImpl implements BookStoreService {

    @Autowired  // 자동주입 선언 하자마자
    private BookRepository bookRepository;

//    public List<Book> findAllList()
    {
 //       List<>
    }
}
