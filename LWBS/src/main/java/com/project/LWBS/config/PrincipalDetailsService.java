package com.project.LWBS.config;

import com.project.LWBS.domain.User;
import com.project.LWBS.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // login 에서 넘어온 username 으로 DB 조회 후 user 에 대입
        User user = userService.findByKakaoId(username);

        // user 가 DB에 존재한다면 UserDetails 생성해서 리턴
        if(user != null){
            PrincipalDetails userDetails = new PrincipalDetails(user);
            userDetails.setUserService(userService);

            // 사용자를 로그인 상태로 설정
            loadUserDirectly(userDetails);

            return userDetails;
        }

        // user 가 DB에 없다면 예외문 throw
        throw new UsernameNotFoundException(username);
    }

    private void loadUserDirectly(PrincipalDetails principalDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }
}
