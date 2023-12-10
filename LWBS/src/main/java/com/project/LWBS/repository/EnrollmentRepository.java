package com.project.LWBS.repository;

import com.project.LWBS.domain.Enrollment;
import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUser(User user);

    Enrollment findByEnrollmentName(String enrollmentName);

    @Query("SELECT e.enrollmentName FROM Enrollment e WHERE e.user.id = :user_id")
    List<String> findEnrollmentNamesByUserId(@Param("user_id") Long user_id);
}
