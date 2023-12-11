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
        User user = userService.findByKakaoId(username);

        if(user != null){
            PrincipalDetails userDetails = new PrincipalDetails(user);
            userDetails.setUserService(userService);

            loadUserDirectly(userDetails);

            return userDetails;
        }

        throw new UsernameNotFoundException(username);
    }

    private void loadUserDirectly(PrincipalDetails principalDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }
}
