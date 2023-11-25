package com.project.LWBS.domain;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false)
    private String kakaoId;

    @Column(length = 100)
    private String franchisee;

    @Column(length = 100, unique = true)
    private String student_id;

    @Column
    private String student_pw;

    @Column
    private String profile_img_url;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @ToString.Exclude
    private Authority authority;



}
