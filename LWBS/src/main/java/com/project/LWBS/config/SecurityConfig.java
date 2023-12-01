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
//                        .requestMatchers("/").authenticated() // 이후 hasAnyRole 로 권한부여 예정
                        .requestMatchers("/user/login").anonymous()
                                .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .successHandler(new CustomLoginSuccessHandler("/home"))
                )
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")        // 로그아웃 수행 url
                        .invalidateHttpSession(false)    // session invalidate (디폴트 true)
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )
                .build();
    }
}
