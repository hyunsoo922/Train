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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

//        System.setProperty("webdriver.chrome.driver", "C:/Users/skrheem/IdeaProjects/Train/chromedriver-win64/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/python/chromedriver");
        WebDriver driver = new ChromeDriver(options);

        System.out.println("시작");

        driver.get("https://sso.nsu.ac.kr/login?redirect_url=https%3A%2F%2Fmypage.nsu.ac.kr%2Fmypage%2Fstudent%2F");

        WebElement elem = driver.findElement(By.id("id_input"));
        elem.sendKeys(studentId);

        WebElement passwordInput = driver.findElement(By.id("password_input"));
        passwordInput.sendKeys(studentPw);
        passwordInput.sendKeys(Keys.RETURN);

        sleep(1000);

        try {
            // 로그인 후 학생의 프로필 이미지를 탐색
            driver.findElement(By.xpath("//*[@id='student_info']/div[2]/div[1]/div"));
            driver.quit();
            userService.updateUserInfo(studentId, studentPw, principalDetails.getUser().getId());
            model.addAttribute("user", principalDetails.getUser());
        } catch (NoSuchElementException e) {
            driver.quit();
        }
        return "redirect:mypage/" + principalDetails.getUser().getId();
    }

    @PostMapping("/bookStore")
    public String handleSubmit(@RequestParam("franchisee") String franchisee,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.updateBookStoreInfo(franchisee, principalDetails.getUser().getId());
        return "redirect:mypage/" + principalDetails.getUser().getId();
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
