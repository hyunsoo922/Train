package com.project.LWBS.repository;

import com.project.LWBS.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);

//    Book findBySubject(long subject_id);
//
//    Book findByDepartment(long department_id);

    List<Book> findAll();

    Book findByName(String name);

    @Query("SELECT b.name FROM Book b")
    List<String> findAllBookNames();

    @Query("SELECT b.isbn FROM Book b WHERE b.isbn = ?1 AND b.department.name = ?2 AND b.subject.name = ?3")
    List<String> findBookNamesByIsbnAndDepartmentAndSubject(String isbn, String departmentName, String subjectName);
}
