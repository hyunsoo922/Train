package com.project.LWBS.repository;

import com.project.LWBS.domain.Mileage;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MileageRepository extends JpaRepository<Mileage, Long> {

}
