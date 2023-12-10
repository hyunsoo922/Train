package com.project.LWBS.service;

import com.project.LWBS.domain.Receive;
import com.project.LWBS.repository.ReceiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


//서비스 임플과 리퍼지터리랑 연결
@Service
public class ReceiveServiceImpl implements ReceiveService{
    @Autowired
    private ReceiveRepository receiveRepository;
    @Override
    public void createReceive(LocalDate start, LocalDate end, long count) {
        String endDay = end.toString();

        for(int i = 0;i < count;i++)
        {
            LocalDate currentDate = start.plusDays(i);
            if(currentDate.equals(end))
            {
                Receive receive1 = Receive.builder()
                        .day(endDay)
                        .receiveCheck("Check")
                        .build();
                receiveRepository.saveAndFlush(receive1);

                Receive receive2 = Receive.builder()
                        .day(endDay)
                        .receiveCheck("NonCheck")
                        .build();
                receiveRepository.saveAndFlush(receive2);
                System.out.println(endDay+"종료날짜");
            }
            else
            {
                String current = currentDate.toString();
                Receive receive1 = Receive.builder()
                        .day(current)
                        .receiveCheck("Check")
                        .build();
                receiveRepository.saveAndFlush(receive1);

                Receive receive2 = Receive.builder()
                        .day(current)
                        .receiveCheck("NonCheck")
                        .build();
                receiveRepository.saveAndFlush(receive2);
                System.out.println(current+"현재날짜");
            }

        }

    }
}
