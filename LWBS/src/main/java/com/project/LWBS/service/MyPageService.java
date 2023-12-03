package com.project.LWBS.service;

import com.project.LWBS.domain.Mileage;

public interface MyPageService {
    int sumMileage(Long user_id);
    String getName(Long user_id);
    String getProfile(Long user_id);
}
