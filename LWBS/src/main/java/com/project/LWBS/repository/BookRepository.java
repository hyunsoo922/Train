package com.project.LWBS.repository;

import com.project.LWBS.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.LWBS.domain.Department;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);


    List<Book> findAll();

    Book findByName(String name);

    List<Book> findByDepartment(Department department);

    @Query("SELECT b.name FROM Book b")
    List<String> findAllBookNames();

    @Query("SELECT b.isbn FROM Book b WHERE b.isbn = ?1 AND b.department.name = ?2 AND b.subject.name = ?3")
    List<String> findBookNamesByIsbnAndDepartmentAndSubject(String isbn, String departmentName, String subjectName);

    Page<Book> findByDepartmentName(String departmentName, Pageable pageable);
}
