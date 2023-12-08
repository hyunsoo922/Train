package com.project.LWBS.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticsService {
    long countAll();
    List<Object[]> countByBook();
}
