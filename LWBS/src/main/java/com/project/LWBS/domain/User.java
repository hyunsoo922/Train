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

    @Column(length = 100)
    private String studentId;

    @Column
    private String studentPw;

    @Column
    private String profileImgUrl;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @ToString.Exclude
    private Authority authority;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Mileage> mileage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Receipt> receipts;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER )
    @ToString.Exclude
    private List<Cart> carts;

}
