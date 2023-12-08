package com.project.LWBS.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@Entity
public class Statistics {

    @Id
    @GeneratedValue()
}
