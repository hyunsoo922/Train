package com.project.LWBS.service;

import com.project.LWBS.domain.Book;
import com.project.LWBS.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface BookService {
    Page<Book> getAllBooks();


    // 웹스크래핑, 검색 API 로직을 수행하여 수집한 교재 정보들을 Book 객체로 만들어 Repository에게 전달하는 메서드
    void createBook(String title, String author, String publisher, String price, String imageUrl, String isbn, String description, String dname, String sname);

    Page<Book> getAllBooks(Pageable pageable);

    List<Book> findByBookName(Long user_id);

    Book findById(Long user_id);

    void createSubject(String sname);

    void createDepartment(String dname);

    Page<Book> findByDepartmentName(String departmentName, Pageable pageable);

    List<Book> getAllBook();

    List<Department> getAllDepartments();
}
