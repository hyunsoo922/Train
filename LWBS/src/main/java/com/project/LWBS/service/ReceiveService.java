package com.project.LWBS.service;

import com.project.LWBS.domain.Receive;

import java.time.LocalDate;
import java.util.List;

public interface ReceiveService {
        void createReceive(LocalDate start, LocalDate end, long count);

        List<Receive> findDay (String day);



}
