package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage/myInfoUpdate")
public class MyInfoUpdateController {

    private final UserService userService;

    @Autowired
    public MyInfoUpdateController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/student")
    public String showChangeStudentInfoForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 이 메소드에서 필요한 초기화 작업을 수행할 수 있습니다.
        // 예: 현재 사용자 정보를 가져와서 폼에 미리 채우는 등의 작업
        model.addAttribute("user", principalDetails.getUser());
        return "/mypage/myInfoUpdate/student"; // changeInfo.html과 매핑되는 뷰 이름
    }
    @GetMapping("/bookStore")
    public String showChangeBookStoreInfoForm() {
        return "/mypage/myInfoUpdate/bookStore";
    }
    @PostMapping("/student")
    public String handleSubmit(@RequestParam("studentId") String studentId,
                               @RequestParam("studentPw") String studentPw,
                               Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.updateUserInfo(studentId, studentPw, principalDetails.getUser().getId());
        model.addAttribute("user", principalDetails.getUser());
        return "redirect:/mypage/" + principalDetails.getUser().getId();
    }
    @PostMapping("/bookStore")
    public String handleSubmit(@RequestParam("franchisee") String franchisee,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.updateBookStoreInfo(franchisee, principalDetails.getUser().getId());
        return "redirect:/mypage/" + principalDetails.getUser().getId();
    }
}