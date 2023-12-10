package com.project.LWBS.repository;

import com.project.LWBS.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);
}