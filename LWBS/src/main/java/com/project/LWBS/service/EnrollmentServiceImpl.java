package com.project.LWBS.service;

import com.project.LWBS.domain.Enrollment;
import com.project.LWBS.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Override
    public Enrollment createEnrollment(String enrollmentName) {
        Enrollment enrollment = Enrollment.builder()
                .enrollmentName(enrollmentName)
                .build();

        enrollmentRepository.saveAndFlush(enrollment);

        return enrollment;
    }
}