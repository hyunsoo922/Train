package com.project.LWBS.repository;

import com.project.LWBS.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);

    Book findByName(String name);

    @Query("SELECT b.name FROM Book b")
    List<String> findAllBookNames();
}
