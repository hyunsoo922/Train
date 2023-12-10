package com.project.LWBS.service;

import com.project.LWBS.domain.Mileage;
import com.project.LWBS.repository.MileageRepository;
import com.project.LWBS.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MileageServiceImpl implements MileageService {
    @Autowired
    private MileageRepository mileageRepository;

    @Override
    public Mileage createMileage() {
        return null;
    }
}