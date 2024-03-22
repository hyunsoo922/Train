package com.project.LWBS.service;

import com.project.LWBS.domain.*;
import com.project.LWBS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReceiveRepository receiveRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private MileageRepository mileageRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Book> findMyClass(User user) {
        List<Enrollment> enrollmentList = enrollmentRepository.findByUser(user);
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < enrollmentList.size(); i++) {
            bookList.add(bookRepository.findByName(enrollmentList.get(i).getEnrollmentName()));
        }

        return bookList;
    }

    @Override
    public List<Receive> findReceiveCheck() {
        List<Receive> receiveList = receiveRepository.findByReceiveCheck("미수령");
        return receiveList;
    }

    @Override
    public List<Book> findByIds(List<String> bookList) {
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < bookList.size(); i++) {
            long id = Long.parseLong(bookList.get(i));
            books.add(bookRepository.findById(id).orElse(null));
        }
        return books;
    }

    @Override
    public void createReceipt(List<Book> bookList, User user, String receiveDay,int useMileage, String tid) {

        List<Receive> receiveList = receiveRepository.findByDay(receiveDay);
        Receive receive = new Receive();
        int point = 0;
        for (int x = 0; x < receiveList.size(); x++) {
            if (receiveList.get(x).getReceiveCheck().equals("미수령")) {
                receive = receiveList.get(x);
            }
        }

        for (int i = 0; i < bookList.size(); i++) {
            point += Integer.parseInt(bookList.get(i).getPrice());

            Receipt receipt = Receipt.builder()
                    .book(bookList.get(i))
                    .receive(receive)
                    .user(user)
                    .tid(tid)
                    .build();

            receiptRepository.saveAndFlush(receipt);

        }

        point -= useMileage;
        int mileagePoint = point / 10;
        Mileage mileage = Mileage.builder()
                .user(user)
                .point(mileagePoint)
                .day(LocalDateTime.now())
                .build();

        mileageRepository.saveAndFlush(mileage);

        useMileage = -useMileage;

        Mileage useMileagePoint = Mileage.builder()
                .user(user)
                .point(useMileage)
                .day(LocalDateTime.now())
                .build();

        mileageRepository.saveAndFlush(useMileagePoint);
    }

    @Override
    public List<Receipt> findAllUser(User user) {
        List<Receipt> receiptList = receiptRepository.findByUser(user);
        return receiptList;
    }

    @Override
    public void updateDay(User user, String day) {
        List<Receipt> receiptList = receiptRepository.findByUser(user);
        List<Receive> receiveList = receiveRepository.findByDay(day);
        Receive receive = new Receive();
        for (int x = 0; x < receiveList.size(); x++) {
            if (receiveList.get(x).getReceiveCheck().equals("미수령")) {
                receive = receiveList.get(x);
            }
        }

        for (int i = 0; i < receiptList.size(); i++) {
            receiptList.get(i).setReceive(receive);
            receiptRepository.flush();
        }
    }

    @Override
    public Department findDepartmentByName(String departmentName) {
        System.out.println(departmentName);
        return departmentRepository.findByName(departmentName);
    }
    @Override
    public List<Book> findByDepartment(Department department) {
        return bookRepository.findByDepartment(department);
    }

}
