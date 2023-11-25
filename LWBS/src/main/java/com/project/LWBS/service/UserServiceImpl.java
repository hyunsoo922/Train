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
    public Boolean isExistKakaoIdByUser(String kakao_id) {
        User user = userRepository.findByKakaoId(kakao_id);
        if(user != null)
        {
            return true;
        }
        return false;
    }
}
