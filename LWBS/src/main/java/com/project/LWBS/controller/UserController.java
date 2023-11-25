package com.project.LWBS.controller;

import com.project.LWBS.domain.User;
import com.project.LWBS.service.KakaoService;
import com.project.LWBS.service.UserService;
import com.project.LWBS.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final KakaoService kakaoService;

    @Autowired
    public UserController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }



    @GetMapping("/login")
    public String getLogin(Model model)
    {
        model.addAttribute("kakaoUrl",kakaoService.getKakaoLogin());
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("kakaoUrl",kakaoService.getKakaoLogin());
        return "user/register";
    }

    @GetMapping("/registerOk")
    public String getRegisterOk()
    {

        return "user/registerOk";
    }
}
