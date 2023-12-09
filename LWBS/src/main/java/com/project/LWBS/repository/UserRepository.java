package com.project.LWBS.repository;

import com.project.LWBS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByKakaoId(String kakaoId);

    User findByName(String name);

    User findByFranchisee(String franchisee);
}
