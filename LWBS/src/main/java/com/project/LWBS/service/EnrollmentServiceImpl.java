package com.project.LWBS.service;

import com.project.LWBS.domain.Enrollment;
import com.project.LWBS.domain.User;
import com.project.LWBS.repository.EnrollmentRepository;
import com.project.LWBS.repository.UserRepository;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    // Enrollment 테이블에 정보를 저장하기 전, 중복 값을 체크하는 메섣,
    public Boolean isExistData(String enrollmentName, Long user_id) {
        Enrollment enrollment1 = enrollmentRepository.findByEnrollmentName(enrollmentName);
        Enrollment enrollment2 = enrollmentRepository.findById(user_id).orElse(null);
        if(enrollment1 != null && enrollment2 != null) {
            return false;
        }
        return true;
    }
    @Override
    // 책 검색 모듈로 수집한 책의 이름과 그 책을 등록한 user의 정보를 Enrollment 테이블에 저장하는 메서드
    public void createEnrollment(String enrollmentName, Long user_id) {
        if(isExistData(enrollmentName, user_id)){
            User user = userRepository.findById(user_id).orElse(null);
            Enrollment enrollment = Enrollment.builder()
                    .enrollmentName(enrollmentName)
                    .user(user)
                    .build();
            enrollmentRepository.saveAndFlush(enrollment);
        }
    }
}