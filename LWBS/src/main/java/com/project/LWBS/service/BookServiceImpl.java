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
    // Book 테이블에 중복되는 값이 있는지 검사하는 메서드
    public Boolean isExistIsbn(String isbn) {
        // 매개변수로 전달받은 isbn에 해당하는 Book 객체를 Book 테이블에서 가져옴
        Book bookcheck = bookRepository.findByIsbn(isbn);
        // isbn에 해당하는 Book 객체가 없다면
        if(bookcheck == null){
            // false를 반환하여 Book 테이블에 책 정보를 추가
            return false;
        }
        // true를 반환하여 Book 테이블에 책 정보를 추가하지 않음
        return true;
    }
    @Override
    // Controller에서 책 정보를 담은 Book 객체를 생성하는 메서드
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
    // 현재 로그인 중인 User가 등록한 강의의 교재명을 리스트로 만드는 메서드
    public List<Book> findByBookName(Long user_id) {
        // 매개변수로 전달 받은 user_id(현재 로그인 중인 User 정보)로 Enrollment 테이블에서 검색 후
        // user_id 값을 가지고 있는 레코드가 있다면 그 레코드의 name 컬럼의 값을 리스트로 생성
        List<String> EnrollBookNameList = enrollmentRepository.findEnrollmentNamesByUserId(user_id);
        List<Book> bookList = new ArrayList<>();
        for (String s:EnrollBookNameList) {
            bookList.add(bookRepository.findByName(s));
        }
        return bookList;
    }

    @Override
    public Book findById(Long book_id) {
        Book book = bookRepository.findById(book_id).orElse(null);
        return book;
    }
}