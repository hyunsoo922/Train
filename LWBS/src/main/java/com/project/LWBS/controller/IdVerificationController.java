package com.project.LWBS.controller;

import com.project.LWBS.service.UserService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
public class IdVerificationController {

    @GetMapping()
    public Boolean verification(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "pw") String pw) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

//        System.setProperty("webdriver.chrome.driver", "C:/Users/skrheem/IdeaProjects/Train/chromedriver-win64/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/Train/LWBS/build/libs/chromedriver-win64/chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);

        System.out.println("시작");

        driver.get("https://sso.nsu.ac.kr/login?redirect_url=https%3A%2F%2Fmypage.nsu.ac.kr%2Fmypage%2Fstudent%2F");

        WebElement elem = driver.findElement(By.id("id_input"));
        elem.sendKeys(id);

        WebElement passwordInput = driver.findElement(By.id("password_input"));
        passwordInput.sendKeys(pw);
        passwordInput.sendKeys(Keys.RETURN);

        sleep(1000);

        try {
            // 로그인 후 학생의 프로필 이미지를 탐색
            driver.findElement(By.xpath("//*[@id='student_info']/div[2]/div[1]/div"));
            driver.quit();
            // 요소가 있다면 true를 반환
            return true;
        } catch (NoSuchElementException e) {
            driver.quit();
            // 요소가 없다면 false를 반환
            return false;
        }
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}