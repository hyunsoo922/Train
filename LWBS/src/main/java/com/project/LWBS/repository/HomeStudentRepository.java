package com.project.LWBS.repository;

import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HomeStudentRepository extends JpaRepository<User, Long> {

}
