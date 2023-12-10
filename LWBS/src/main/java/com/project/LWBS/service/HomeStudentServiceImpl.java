package com.project.LWBS.service;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Enrollment;
import com.project.LWBS.repository.BookRepository;
import com.project.LWBS.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HomeStudentServiceImpl implements HomeStudendService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public List<String> getBookName(Long user_id) {

        return null;
    }

    @Override
    public List<Book> findByBookName(String bookname) {
        return null;
    }
}
