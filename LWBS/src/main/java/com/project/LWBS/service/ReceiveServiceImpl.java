package com.project.LWBS.service;

import com.project.LWBS.domain.Receive;
import com.project.LWBS.repository.ReceiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
                        .receiveCheck("수령")
                        .build();
                receiveRepository.saveAndFlush(receive1);

                Receive receive2 = Receive.builder()
                        .day(endDay)
                        .receiveCheck("미수령")
                        .build();
                receiveRepository.saveAndFlush(receive2);
                System.out.println(endDay+"종료날짜");
            }
            else
            {
                String current = currentDate.toString();
                Receive receive1 = Receive.builder()
                        .day(current)
                        .receiveCheck("수령")
                        .build();
                receiveRepository.saveAndFlush(receive1);

                Receive receive2 = Receive.builder()
                        .day(current)
                        .receiveCheck("미수령")
                        .build();
                receiveRepository.saveAndFlush(receive2);
                System.out.println(current+"현재날짜");
            }

        }

    }

    @Override
    public List<Receive> findDay(String day) {

        List<Receive> receiveList = receiveRepository.findByDay(day);

        return receiveList;
    }

    @Override
    public List<LocalDate> startAndEnd() {
        List<Receive> receiveList = receiveRepository.findAll();
        LocalDate start = LocalDate.now();
        LocalDate end  = LocalDate.now();
        for(Receive receive : receiveList)
        {
            LocalDate day = LocalDate.parse(receive.getDay());
            if(day.isBefore(start))
            {
                start = day;
            }

            if(day.isAfter(end))
            {
                end = day;
            }
        }
        List<LocalDate> date = new ArrayList<>();
        date.add(start);
        date.add(end);
        return date;
    }
}
