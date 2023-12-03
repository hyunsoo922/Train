package com.project.LWBS.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
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
    private String studentId;

    @Column
    private String studentPw;

    @Column
    private String profileImgUrl;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @ToString.Exclude
    private Authority authority;

    @OneToMany
    private List<Mileage> mileage;
}
