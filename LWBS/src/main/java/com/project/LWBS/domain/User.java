//package com.project.LWBS.domain;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Builder                //테이블 컬럼 생성
//@Entity
//@NoArgsConstructor      //기본 생성자 없으면 생성
////@Table(name = "User")   //테이블 명
//@Data                   //@Getter @Setter
//@AllArgsConstructor     // 생성자 생성
//public class User {
//    // 테이블 추가될 때마다 id 값이 자동으로 올라감
//    // @Column(name = "User_ID")
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // @ManyToOne // Authority와 User는 1ㄷ다 관계다.
//    // Authority 엔티티 만들고 주석 풀기
//    @Column//(name = "Authority_INDEX")
//    private int authorityIndex;
//
//    @Column//(name = "USER_NAME")
//    private String name;
//
//    @Column//(name = "USER_KAKAO_ID")
//    private String kakaoID;
//
//    @Column//(name = "STUDENT_ID")
//    private String studentID;
//
//    @Column//(name = "STUDENT_PW")
//    private String studentPW;
//
//    @Column//(name = "FRANCHISEE")
//    private String franchisee;
//
//    @Column//(name = "PROFILE_IMG_URL")
//    private String imgURL;
//
//}
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

    @Column()
    private String kakaoid;

    @Column(length = 100)
    private String franchisee;

    @Column(length = 100, unique = true)
    private String studentId;

    @Column(length = 100)
    private String studentPw;

    @Column(length = 1000)
    private String profileImgUrl;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @ToString.Exclude
    private Authority authority;

//    @OneToMany
//    private List<Mileage> user_id;
    @OneToMany
    private List<Mileage> mileage;
}
