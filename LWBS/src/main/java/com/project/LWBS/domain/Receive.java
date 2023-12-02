package com.project.LWBS.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor//기본생성자 생성
@AllArgsConstructor//모든생성자 생성
@Data//getter, setter
@Entity
@ToString(callSuper = true)
@Builder //값을 생성후 DB에 저장시 필요
public class Receive {
    @Id//pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//테이블 요소 추가될때마다 1씩 증가
    private long id;

    @Column(nullable = false)
    private String day;//수령 날짜

    @Column(nullable = false)
    private String receiveCheck;//수령여부

}
