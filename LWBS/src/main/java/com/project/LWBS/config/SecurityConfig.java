package com.project.LWBS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login").anonymous()
                        // 학생 계정으로 로그인시 접속가능
                        .requestMatchers("/home/student","/student/purchase/book","/student/purchase/bookPay","/student/purchase/receipt",
                                "/mypage","/mypage/myInfoUpdate/student","/mypage/mileage").hasAnyRole("STUDENT")
                        // 서점 계정으로 로그인시 접속가능
                        .requestMatchers("/home/bookStore","/bookStore/DaySelect","/bookStore/DaySelectResult","/bookStore/list","/bookStore/Receipt"
                                        ,"/bookStore/ReceiptSearch","/bookStore/Statistics","/mypageBookStore","/mypage/myInfoUpdate/bookStore").hasAnyRole("BOOKSTORE")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/user/login")       // 개별 로그인 페이지 지정
                        .loginProcessingUrl("/user/login")
                        .successHandler(new CustomLoginSuccessHandler("/home"))
                )
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")      // 로그아웃 수행 url
                        .invalidateHttpSession(true)    // session invalidate (디폴트 true)
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )
                .build();
    }
}
