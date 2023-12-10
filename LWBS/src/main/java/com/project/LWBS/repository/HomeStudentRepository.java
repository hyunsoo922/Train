package com.project.LWBS.repository;

import com.project.LWBS.domain.Enrollment;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeStudentRepository extends JpaRepository<User, Long> {

}
