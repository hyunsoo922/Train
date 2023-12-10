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
    public Boolean isExistData(String enrollmentName, Long user_id) {
        Enrollment enrollment1 = enrollmentRepository.findByEnrollmentName(enrollmentName);
        Enrollment enrollment2 = enrollmentRepository.findById(user_id).orElse(null);
        if(enrollment1 != null && enrollment2 != null) {
            return false;
        }
        return true;
    }
    @Override
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

    // user_id로 Enrollment 테이블의 교재명을 List로 가져옴
    @Override
    public List<String> makeEnrollBookList(Long user_id) {
        try {
            List<String> enrollBookNameList = enrollmentRepository.findEnrollmentNamesByUserId(user_id);
            return enrollBookNameList;
        } catch (NoResultException ex) {
            // 예외 발생 시 "책 없음"을 포함한 빈 리스트 반환
            return Collections.singletonList("책 없음");
        }
    }


}