package com.project.LWBS.service;

public interface MyPageService {
    // 현재 로그인 중인 User의 마일리지를 계산하는 메서드
    int sumMileage(Long user_id);
    // 마이페이지에 출력할 User 이름을 User 테이블로부터 가져오는 메서드
    String getName(Long user_id);
    // 마이페이지에 출력할 User의 프로필 이미지 URL을 User 테이블로부터 가져오는 메서드
    String getProfile(Long user_id);
    // 현재 로그인 중인 User가 학생 계정인지 서점 계정인지 판별할 user_id 값을 User 테이블로부터 가져오는 메서드
    Long getAuthority(Long user_id);
    // 현재 로그인 중인 서점 계정의 가맹점 id를 가져오는 메서드
    String getFranchisee(Long user_id);
}
