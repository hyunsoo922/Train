package com.project.LWBS.service;

import com.project.LWBS.domain.User;
import com.project.LWBS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser() {
        User user = User.builder()
                .name("임세규")
                .franchisee(null)
                .profileImgUrl("/images/nameko.jpg")
                .studentId("18102101")
                .studentPw("sk991116!")
                .kakaoid("1234")
                .build();
        userRepository.saveAndFlush(user);
        return user;
    }



}
