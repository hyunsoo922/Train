package com.project.LWBS.service;

import com.project.LWBS.domain.Enrollment;

import java.util.List;

public interface EnrollmentService {
    void createEnrollment(String enrollment, Long user_id);

    List<String> makeEnrollBookList(Long user_id);

    Boolean isExistData(String name, Long user_id);
}
