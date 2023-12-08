package com.project.LWBS.service;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Enrollment;
import com.project.LWBS.domain.Receive;
import com.project.LWBS.domain.User;
import com.project.LWBS.repository.BookRepository;
import com.project.LWBS.repository.EnrollmentRepository;
import com.project.LWBS.repository.ReceiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReceiveRepository receiveRepository;

    @Override
    public List<Book> findMyClass(User user) {
        List<Enrollment> enrollmentList = enrollmentRepository.findByUser(user);
        List<Book> bookList = new ArrayList<>();
        for(int i = 0; i < enrollmentList.size(); i++)
        {
            bookList.add(bookRepository.findByName(enrollmentList.get(i).getEnrollmentName()));
        }

        return bookList;
    }

    @Override
    public List<Receive> findReceiveCheck() {
        List<Receive> receiveList = receiveRepository.findByReceiveCheck("미수령");
        return receiveList;
    }

    @Override
    public List<Book> findByIds(List<String> bookList) {
        List<Book> books = new ArrayList<>();

        for(int i = 0; i < bookList.size(); i++)
        {
            long id = Long.parseLong(bookList.get(i));
            books.add(bookRepository.findById(id).orElse(null));
        }
        return books;
    }
}
