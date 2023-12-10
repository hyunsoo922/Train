package com.project.LWBS.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public CustomLoginSuccessHandler(String defaultTargetUrl) {
        // 로그인 성공 후 redirect 할 url 이 없는 경우 기본적으로 redirect 시킬 url 설정
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        System.out.println("로그인 성공: onAuthenticationSuccess() 호출");

        if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT")))
        {
            response.sendRedirect("http://localhost:8093/home/student");
        }
        else if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_BOOKSTORE")))
        {
            response.sendRedirect("http://localhost:8093/home/bookStore");
        }
        else{
            // 만약 특정페이지에서 로그인 시도 후 성공시 로그인 직전 url 로 redirect
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
