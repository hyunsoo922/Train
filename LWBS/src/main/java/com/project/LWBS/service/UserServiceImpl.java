package com.project.LWBS.service;

import com.project.LWBS.domain.User;
import com.project.LWBS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

}
