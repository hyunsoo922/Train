package com.project.LWBS.service;

import com.google.j2objc.annotations.ObjectiveCName;
import com.project.LWBS.domain.Department;
import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Subject;
import com.project.LWBS.repository.BookRepository;
import com.project.LWBS.repository.DepartmentRepository;
import com.project.LWBS.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public void createBook(String title, String author, String publisher, String price, String imageUrl, String isbn, String description, String dname, String sname) {
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
        // Book 저장
        System.out.println(book);
        System.out.println("-----------------------");
        System.out.println("넘겨받은 " + dname);
        System.out.println("넘겨받은 " + sname);
        System.out.println("-----------------------");
        bookRepository.saveAndFlush(book);
    }

    @Override
    public Boolean isExistIsbn(String isbn) {
        Book bookcheck = bookRepository.findByIsbn(isbn);
        if(bookcheck == null){
            return false;
        }
        return true;
    }
}