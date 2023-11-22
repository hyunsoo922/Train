package com.project.LWBS.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request,response,authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response,
                          Authentication authentication) throws IOException, ServletException
    {
        String target =determineTargetUrl(authentication);
        if(response.isCommitted())
        {
            return;
        }
        getRedirectStrategy().sendRedirect(request,response,target);
    }

    protected String determineTargetUrl(Authentication authentication)
    {
        // 와일드 카드 사용
        Collection<? extends GrantedAuthority> authorities =authentication.getAuthorities();

        if(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_STUDENT")))
        {
            return "http://localhost:8093/home/student";
        }else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_BOOKSTORE")))
        {
            return "http://localhost:8093/home/bookStore";
        }else
        {
            return "/dashboard";
        }
    }
}
