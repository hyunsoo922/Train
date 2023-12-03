package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public void home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        try{
//            PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            User user = userDetails.getUser();
//            Long id = user.getId();
            System.out.println("홈에 들어옴 로그인"+principalDetails.getUsername());
            model.addAttribute("user",principalDetails.getUser());
        } catch (Exception e){
            System.out.println("로그인실패");
            model.addAttribute("logged_id", null);
        }
    }

    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}