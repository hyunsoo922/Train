package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.DTO.KakaoDTO;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.KakaoService;
import com.project.LWBS.service.UserService;
import com.project.LWBS.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final KakaoService kakaoService;

    private UserService userService;

    @Autowired
    public UserController(KakaoService kakaoService, UserService userService) {
        this.kakaoService = kakaoService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin(Model model)
    {
        model.addAttribute("kakaoUrl",kakaoService.getKakaoLogin());
        return "user/login";
    }

    @GetMapping("/register")
    public void register(Model model)
    {

    }

    @PostMapping("/register")
    public String registerOk(@RequestParam("kind") String kind,@RequestParam(name = "LMSID", required = false) String LMSID,
                             @RequestParam(name = "LMSPW",required = false) String LMSPW,
                             @RequestParam(name = "publisherId",required = false) String publisherId){

        System.out.println("종류"+kind);
        System.out.println("lmsid"+LMSID);
        System.out.println("lmspw"+LMSPW);
        System.out.println("가맹점아이디"+publisherId);
        KakaoDTO kakaoDTO = userService.getKakaoDTO();
        System.out.println("!!!!카카오아이디!!!!" + kakaoDTO);
        userService.registerUser(kind,LMSID,LMSPW,publisherId,kakaoDTO);


        return  "redirect:login";
    }

    @GetMapping("/delete")
    public void delete(){};

    @PostMapping("/delete")
    @Transactional
    public String deleteSuccess(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request, HttpServletResponse response)
    {
        User user = principalDetails.getUser();
        new SecurityContextLogoutHandler().logout(request,response,null);
        System.out.println(user+"탈퇴할유저");
        userService.deleteUser(user);

        return  "redirect:/home";
    }

}
