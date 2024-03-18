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
    // 회원가입한 유저의 enrollment가 비어있는지 체크하는 메서드
    public Boolean isEmptyData(User user) {
        List<Enrollment> enrollment = enrollmentRepository.findByUser(user);
        if(enrollment != null)
            // 비어있지 않다면 false
            return false;
        // 비어있다면 true
        return true;
    }
    @Override
    // Enrollment 테이블에 정보를 저장하기 전, 중복 값을 체크하는 메서드
    public Boolean isExistData(String enrollmentName, User user) {
        Enrollment enrollment1 = enrollmentRepository.findByEnrollmentName(enrollmentName);
        List<Enrollment> userEnrollment= enrollmentRepository.findByUser(user);
        for (Enrollment e:userEnrollment) {
            if(e.equals(enrollment1)) {
                return false;
            }
        }
        return true;
    }
    @Override
    // 책 검색 모듈로 수집한 책의 이름과 그 책을 등록한 user의 정보를 Enrollment 테이블에 저장하는 메서드
    public void createEnrollment(String enrollmentName, Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        if(isExistData(enrollmentName, user)){
            Enrollment enrollment = Enrollment.builder()
                    .enrollmentName(enrollmentName)
                    .user(user)
                    .build();
            enrollmentRepository.saveAndFlush(enrollment);
        }
    }
}