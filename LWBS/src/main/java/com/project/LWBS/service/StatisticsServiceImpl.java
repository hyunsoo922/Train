package com.project.LWBS.service;

import com.project.LWBS.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository){
    }
    @Override
    public long countAll() {
        return 0;
    }

    @Override
    public List<Object[]> countByBook() {
        return null;
    }
}
