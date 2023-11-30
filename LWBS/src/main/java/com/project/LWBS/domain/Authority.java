package com.project.LWBS.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int point;

}