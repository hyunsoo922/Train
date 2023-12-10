package com.project.LWBS.domain;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 교재명
    @Column(nullable = false, length = 100)
    private String name;

    // 저자명
    @Column(nullable = false, length = 100)
    private String author;

    // 출판사명
    @Column(nullable = false, length = 100)
    private String publisher;

    // 가격
    @Column(nullable = false, length = 100)
    private String price;

    // 이미지 주소
    @Column(length = 254)
    private String imageUrl;

    // 책 내용
    @Column(length = 1000)
    private String description;

    // 국제표준도서번호
    @Column(nullable = false,length = 20)
    private String isbn;

    // 강의명
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // 학과명
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}