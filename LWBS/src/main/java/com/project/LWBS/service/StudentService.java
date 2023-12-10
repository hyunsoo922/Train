package com.project.LWBS.service;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Receipt;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;

import java.util.List;

public interface StudentService {

    List<Book> findMyClass(User user);

    List<Receive> findReceiveCheck();

    List<Book> findByIds(List<String> bookList);

    void createReceipt(List<Book> bookList,User user,String receiveDay);

    List<Receipt> findAllUser(User user);

    void updateDay(User user,String day);
}

