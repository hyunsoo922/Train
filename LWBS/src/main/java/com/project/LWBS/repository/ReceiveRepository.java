package com.project.LWBS.repository;

import com.project.LWBS.domain.Receive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiveRepository extends JpaRepository<Receive, Long> {


    List<Receive> findByDay(String day);
}
