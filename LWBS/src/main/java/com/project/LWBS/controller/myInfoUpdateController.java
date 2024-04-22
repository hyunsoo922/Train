package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.service.UserService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage/myInfoUpdate")
public class myInfoUpdateController {

    private final UserService userService;

    @Autowired
    public myInfoUpdateController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/student")
    public String showChangeStudentInfoForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("user", principalDetails.getUser());
        return "mypage/myInfoUpdate/student"; // changeInfo.html과 매핑되는 뷰 이름
    }

    @GetMapping("/bookStore")
    public String showChangeBookStoreInfoForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("user", principalDetails.getUser());
        return "mypage/myInfoUpdate/bookStore";
    }

    @PostMapping("/student")
    public String handleSubmit(@RequestParam("studentId") String studentId,
                               @RequestParam("studentPw") String studentPw,
                               Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.updateUserInfo(studentId, studentPw, principalDetails.getUser().getId());
        model.addAttribute("user", principalDetails.getUser());
        return "/mypage/myInfoUpdate/student";
    }

    @PostMapping("/bookStore")
    public String handleSubmit(@RequestParam("franchisee") String franchisee,
                               Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.updateBookStoreInfo(franchisee, principalDetails.getUser().getId());
        model.addAttribute("user", principalDetails.getUser());
        return "mypage/myInfoUpdate/bookStore";
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
