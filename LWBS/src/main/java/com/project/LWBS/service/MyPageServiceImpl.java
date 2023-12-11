package com.project.LWBS.service;

import com.project.LWBS.domain.Mileage;
import com.project.LWBS.domain.User;
import com.project.LWBS.repository.MileageRepository;
import com.project.LWBS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyPageServiceImpl implements MyPageService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MileageRepository mileageRepository;

    @Override
    // 현재 로그인 중인 User의 마일리지를 계산하는 메서드
    public int sumMileage(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        List<Mileage> mileageList = mileageRepository.findByUser(user);
        int sum = 0;
        for (Mileage mileage : mileageList) {
            sum = sum + mileage.getPoint();
        }
        return sum;
    }
    @Override
    // 마이페이지에 출력할 User 이름을 User 테이블로부터 가져오는 메서드
    public String getName(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        String name = user.getName();
        return name;
    }

    @Override
    // 마이페이지에 출력할 User의 프로필 이미지 URL을 User 테이블로부터 가져오는 메서드
    public String getProfile(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        String URL = user.getProfileImgUrl();
        return URL;
    }

    @Override
    // 현재 로그인 중인 User가 학생 계정인지 서점 계정인지 판별할 user_id 값을 User 테이블로부터 가져오는 메서드
    public Long getAuthority(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        Long Authority = user.getAuthority().getId();
        return Authority;
    }

    @Override
    public String getFranchisee(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        String franchisee = user.getFranchisee();
        return franchisee;
    }
}
