package com.project.LWBS.service;

import com.project.LWBS.domain.Mileage;
import com.project.LWBS.domain.User;
import com.project.LWBS.repository.MyPageRepository;
import com.project.LWBS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageServiceImpl implements MyPageService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyPageRepository myPageRepository;

    @Override
    public int sumMileage(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        List<Mileage> mileageList = myPageRepository.findByUser(user);
        int sum = 0;
        for (Mileage mileage : mileageList) {
            sum = sum + mileage.getPoint();
        }
        return sum;
    }
    @Override
    public String getName(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        String name = user.getName();
        return name;
    }

    @Override
    public String getProfile(Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        String URL = user.getProfileImgUrl();
        return URL;
    }
}