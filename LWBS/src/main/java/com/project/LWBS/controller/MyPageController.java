package com.project.LWBS.controller;


import com.project.LWBS.service.MyPageService;
import com.project.LWBS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping
public class MyPageController {
    private MyPageService mypageService;

    @GetMapping("/mypage/{user_id}")
    public String hello(@PathVariable Long user_id, Model model) {
        int a = mypageService.sumMileage(user_id);
        String b = mypageService.getName(user_id);
        String c = mypageService.getProfile(user_id);
        model.addAttribute("sumpoint",a);
        model.addAttribute("name", b);
        model.addAttribute("URL", c);
        return "/mypage";
    }
    private UserService userService;
    @Autowired
    public void MyPageService(MyPageService mypageService) {
        this.mypageService = mypageService;
    }
    // 로그인된 사용자의 id값을 받아와서 /mypage/{user_id}로 넘긴다.
}
