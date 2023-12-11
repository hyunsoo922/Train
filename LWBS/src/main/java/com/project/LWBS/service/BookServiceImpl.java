package com.project.LWBS.service;

import com.project.LWBS.domain.Book;
import com.project.LWBS.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.project.LWBS.domain.Department;
import com.project.LWBS.domain.Subject;
import com.project.LWBS.repository.DepartmentRepository;
import com.project.LWBS.repository.EnrollmentRepository;
import com.project.LWBS.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public Boolean isExistIsbn(String isbn) {
        Book bookcheck = bookRepository.findByIsbn(isbn);
        if(bookcheck == null){
            return false;
        }
        return true;
    }
    @Override
    public void createBook(String title, String author, String publisher, String price, String imageUrl, String isbn, String description, String dname, String sname) {
        if(!isExistIsbn(isbn)){
            Department department = departmentRepository.findByName(dname);
            Subject subject = subjectRepository.findByName(sname);
            Book book = Book.builder()
                    .name(title)
                    .author(author)
                    .publisher(publisher)
                    .price(price)
                    .imageUrl(imageUrl)
                    .isbn(isbn)
                    .description(description)
                    .department(department)
                    .subject(subject)
                    .build();
            bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<Book> findByBookName(Long user_id) {
        List<String> EnrollBookNameList = enrollmentRepository.findEnrollmentNamesByUserId(user_id);
        List<Book> bookList = new ArrayList<>();
        for (String s:EnrollBookNameList) {
            bookList.add(bookRepository.findByName(s));
        }
        return bookList;
    }

    @Override
    public Book findById(Long user_id) {
        Book book = bookRepository.findById(user_id).orElse(null);
        return book;
    }
}