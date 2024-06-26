package com.project.LWBS.service;

import com.project.LWBS.domain.*;

import java.util.List;

public interface StudentService {

    List<Book> findMyClass(User user);

    List<Receive> findReceiveCheck();

    List<Book> findByIds(List<String> bookList);

    void createReceipt(List<Book> bookList,User user,String receiveDay,int useMileage,String tid);

    List<Receipt> findAllUser(User user);

    void updateDay(User user,String day);

    Department findDepartmentByName(String departmentName);

    List<Book> findByDepartment(Department department);

    Book findById(Long id);
}
