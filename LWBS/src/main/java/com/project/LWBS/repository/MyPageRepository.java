package com.project.LWBS.repository;

import com.project.LWBS.domain.Mileage;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyPageRepository extends JpaRepository<User, Long> {

}
