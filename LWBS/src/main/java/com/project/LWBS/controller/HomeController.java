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
    public String home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        try{
            model.addAttribute("user",principalDetails.getUser());
            if(principalDetails.getUser().getAuthority().getName().equals("ROLE_STUDENT")) {
                return "redirect:/home/student";
            }
            else if(principalDetails.getUser().getAuthority().getName().equals("ROLE_BOOKSTORE")) {
                return "redirect:/home/bookStore";
            }
        } catch (Exception e){
            System.out.println("로그인실패");
            model.addAttribute("user", null);
        }
        return "home";
    }

    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
