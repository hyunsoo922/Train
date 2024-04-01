package com.project.LWBS.controller;


import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Authority;
import com.project.LWBS.domain.Mileage;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.MyPageService;
import com.project.LWBS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping
public class MyPageController {
    private MyPageService mypageService;
    private UserService userService;


    @Autowired
    public void MyPageService(MyPageService mypageService, UserService userService) {
        this.mypageService = mypageService;
        this.userService = userService;
    }

    @GetMapping("/mypage/{user_id}")
    // 마이페이지에 로그인한 User의 ID 값에 해당하는 정보를 view에게 전달하는 메서드
    public String hello(@PathVariable Long user_id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if(userService.findAuthority(user_id).equals("ROLE_STUDENT")) {
            // 마일리지 총합을 계산
            int sumMileage = mypageService.sumMileage(user_id);
            // User의 이름을 전달
            String name = mypageService.getName(user_id);
            // User의 카카오톡 프로필 이미지 URL을 전달
            String profileURL = mypageService.getProfile(user_id);
            // 사이트의 경로로부터 user_id를 추출 후 전달
            Long id = user_id;
            // 현재 로그인 한 User의 로그인 유형(학생/서점)을 전달
            Long authority = mypageService.getAuthority(user_id);



            try{
                // view에게 마이페이지에서 필요한 정보를 전달
                System.out.println("홈에 들어옴 로그인"+principalDetails.getUsername());
                model.addAttribute("user",principalDetails.getUser());
                model.addAttribute("sumpoint",sumMileage);
                model.addAttribute("name", name);
                model.addAttribute("URL", profileURL);
                model.addAttribute("ID", id);
                model.addAttribute("authority", authority);
            } catch (Exception e){
                System.out.println("로그인실패");
                model.addAttribute("logged_id", null);
            }
            return "mypage";
        }
        else if(userService.findAuthority(user_id).equals("ROLE_BOOKSTORE")) {
            // 가맹점 ID를 전달
            String franchisee = mypageService.getFranchisee(user_id);
            // User의 이름을 전달
            String name = mypageService.getName(user_id);
            // User의 카카오톡 프로필 이미지 URL을 전달
            String profileURL = mypageService.getProfile(user_id);
            // 사이트의 경로로부터 user_id를 추출 후 전달
            Long id = user_id;
            // 현재 로그인 한 User의 로그인 유형(학생/서점)을 전달
            Long authority = mypageService.getAuthority(user_id);
            try{
                // view에게 마이페이지에서 필요한 정보를 전달
                System.out.println("홈에 들어옴 로그인"+principalDetails.getUsername());
                model.addAttribute("user",principalDetails.getUser());
                model.addAttribute("franchisee",franchisee);
                model.addAttribute("name", name);
                model.addAttribute("URL", profileURL);
                model.addAttribute("ID", id);
                model.addAttribute("authority", authority);
            } catch (Exception e){
                System.out.println("로그인실패");
                model.addAttribute("logged_id", null);
            }
            return "mypageBookStore";
        }
        return "mypage";
    }
//    @GetMapping("/mypage/mileage/{user_id}")
//    public String mileage(@PathVariable Long user_id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
//        List<Mileage> history = mypageService.history(user_id);
//        int sumMileage = mypageService.sumMileage(user_id);
//        try{
//            // view에게 마일리지 내역 조회 페이지에 필요한 정보를 전달
//            model.addAttribute("user",principalDetails.getUser());
//            model.addAttribute("history", history);
//            model.addAttribute("mileage", sumMileage);
//        } catch (Exception e){
//            System.out.println("마일리지 조회 실패");
//            model.addAttribute("logged_id", null);
//        }
//        return "mypage/mileage";
//    }
@GetMapping("/mypage/mileage/{user_id}")
public String mileage(@PathVariable Long user_id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    List<Mileage> history = mypageService.history(user_id);
    int sumMileage = mypageService.sumMileage(user_id);

    List<Mileage> accumulate = new ArrayList<>();
    List<Mileage> use = new ArrayList<>();

    for (Mileage mileage : history) {
        if (mileage.getId() % 2 != 0) {
            accumulate.add(mileage);
        } else if (mileage.getId() % 2 == 0) {
            if(mileage.getPoint() != 0)
                use.add(mileage);
        }
    }

    history.clear();

    history.addAll(accumulate);
    history.addAll(use);

    // history 리스트를 mileage.getDate()를 기준으로 정렬하는 Comparator를 사용하여 정렬
    Collections.sort(history, new MileageDateComparator());

    try {
        // view에게 마일리지 내역 조회 페이지에 필요한 정보를 전달
        model.addAttribute("user", principalDetails.getUser());
        model.addAttribute("history", history);
        model.addAttribute("mileage", sumMileage);
    } catch (Exception e) {
        System.out.println("마일리지 조회 실패");
        model.addAttribute("logged_id", null);
    }
    return "mypage/mileage";
}
    private static class MileageDateComparator implements Comparator<Mileage> {
        @Override
        public int compare(Mileage m1, Mileage m2) {
            // 내림차순으로 정렬되도록 최신 날짜가 더 큰 것으로 간주합니다.
            return m2.getDay().compareTo(m1.getDay());
        }
    }
}
