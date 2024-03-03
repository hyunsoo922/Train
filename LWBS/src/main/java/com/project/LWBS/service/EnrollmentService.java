package com.project.LWBS.service;

import com.project.LWBS.domain.User;

import java.util.List;

public interface EnrollmentService {
    // 책 검색 모듈로 수집한 책의 이름과 그 책을 등록한 user의 정보를 Enrollment 테이블에 저장하는 메서드
    void createEnrollment(String enrollment, Long user_id);

    // Enrollment 테이블에 정보를 저장하기 전, 중복 값을 체크하는 메서드
    Boolean isExistData(String name, User user);

    Boolean isEmptyData(Long user_id);
}
