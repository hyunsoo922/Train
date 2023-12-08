package com.project.LWBS.repository;

import com.project.LWBS.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);

    Book findByName(String name);
}
