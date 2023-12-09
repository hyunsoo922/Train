//package com.project.LWBS.controller;
//
//import com.project.LWBS.domain.Department;
//import com.project.LWBS.service.*;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//
//import java.util.*;
//
//import com.project.LWBS.service.BookService;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//@Controller
//@RequestMapping()
//public class BookRestController {
//    private static EnrollmentService enrollmentService;
//    private static BookService bookService;
//
//    private static void sleep(int milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    @Autowired
//    public BookRestController(EnrollmentService enrollmentService, BookService bookService) {
//        this.enrollmentService = enrollmentService;
//        this.bookService = bookService;
//    }
//    @GetMapping("/webscraping")
//    public String hello() {
//        System.setProperty("webdriver.chrome.driver", "C:/Users/skrheem/IdeaProjects/Train/chromedriver-win64/chromedriver.exe");
////        ChromeOptions options = new ChromeOptions();
////        options.addArguments("--headless");
////        WebDriver driver = new ChromeDriver(options);
//        WebDriver driver = new ChromeDriver();
//        System.out.println("시작");
//
//        String id = "18102101";
//        String ps = "sk991116!";
//
//        driver.get("https://sso.nsu.ac.kr/login?redirect_url=https%3A%2F%2Fmypage.nsu.ac.kr%2Fmypage%2Fstudent%2F");
//
//        WebElement elem = driver.findElement(By.id("id_input"));
//        elem.sendKeys(id);
//
//        WebElement passwordInput = driver.findElement(By.id("password_input"));
//        passwordInput.sendKeys(ps);
//        passwordInput.sendKeys(Keys.RETURN);
//
//        sleep(1000);
//
//        driver.get("https://mypage.nsu.ac.kr/mypage/student/?m1=A00020%2FHSK511%25");
//
//        sleep(1000);
//
//        int index1 = 2;
//        String a = "//*[@id=\"wrapper\"]/div[1]/div/div/div[6]/div[1]/div/table/tbody/tr[";
//        String b = "]/td[11]";
//
//        int index2 = 1;
//        String c = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr[";
//        String d = "]/td[3]/span";
//
//        List<String> bookNames = new ArrayList<>();
//        List<String> departments = new ArrayList<>();
//        List<String> subjects = new ArrayList<>();
//
//        while (true) {
//            String menuButtonXPath = a + index1 + b;
//
//            try {
//                WebElement menuButton = driver.findElement(By.xpath(menuButtonXPath));
//                menuButton.click();
//                sleep(100);
//                while (true) {
//                    String menuButtonXPath2 = c + index2 + d;
//                    String subjectXPath = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[1]/div/div/table/tbody/tr[1]/td[4]/div/span[1]";
//                    String departmentXPath = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[1]/div/div/table/tbody/tr[3]/td[4]/span";
//                    try {
//                        WebElement subjectElement = driver.findElement(By.xpath(subjectXPath));
//                        String subject = subjectElement.getText();
//                        sleep(100);
//
//                        WebElement departmentElement = driver.findElement(By.xpath(departmentXPath));
//                        String department = departmentElement.getText();
//                        sleep(100);
//
//                        WebElement bookElement = driver.findElement(By.xpath(menuButtonXPath2));
//                        String book = bookElement.getText();
//                        sleep(100);
//
//                        if (book.length() >= 5) {
//                            bookNames.add(book);
//                            System.out.println(book);
//                            departments.add(department);
//                            System.out.println(departments);
//                            subjects.add(subject);
//                            System.out.println(subjects);
//                        }
//                        break;
//                    } catch (org.openqa.selenium.NoSuchElementException e) {
//                        index2++;
//                        continue;
//                    }
//                }
//                index1++;
//                WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"popup_layout_list\"]/div/div[2]/div[1]/img"));
//                closeButton.click();
//                sleep(100);
//                index2 = 1;
//            } catch (org.openqa.selenium.NoSuchElementException e) {
//                System.out.println("종료");
//                break;
//            }
//        }
//        bookNames.remove(2);
//        departments.remove(2);
//        subjects.remove(2);
//
//        // 웹스크래핑으로 가져온 교재명을 Enrollment에 저장
//        for (String E : bookNames) {
//            enrollmentService.createEnrollment(E);
//        }
//
//        sleep(100);
//        driver.quit();
//        System.out.println("교재명 리스트 (길이가 5 이상):");
//        System.out.println("교재명");
//        for (String v : bookNames) {
//            System.out.println(v);
//        }
//        System.out.println();
//        System.out.println("과목명");
//        for (String s : subjects) {
//            System.out.println(s);
//        }
//        System.out.println();
//        System.out.println("학과명");
//        for (String f : departments) {
//            System.out.println(f);
//        }
//        String clientId = "GjB1T5WYEFrpN4KIf6Pb";
//        String clientSecret = "U9jyF2ZCDa";
//        int index = 0;
//        for (String term : bookNames) {
//            index++;
//            try {
//                String encodedQuery = URLEncoder.encode(term, "UTF-8");
//                String apiUrl = "https://openapi.naver.com/v1/search/book.json?query=" + encodedQuery + "&display=1";
//
//                URL url = new URL(apiUrl);
//                HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                con.setRequestMethod("GET");
//                con.setRequestProperty("X-Naver-Client-Id", clientId);
//                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
//
//                int responseCode = con.getResponseCode();
//
//                if (responseCode == 200) {
//                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//
//                    while ((line = br.readLine()) != null) {
//                        response.append(line);
//                    }
//                    br.close();
//
//                    String responseBody = response.toString();
//
//                    // JSON 응답 파싱
//                    JSONObject jsonResponse = new JSONObject(responseBody);
//
//                    JSONArray items = jsonResponse.getJSONArray("items");
//                    int LEN = items.length();
//                    for(int i = 0; i < LEN; i++) {
//                        JSONObject item = items.getJSONObject(i);
//                        String title = item.getString("title");
//                        String author = item.getString("author");
//                        String discount = item.getString("discount");
//                        String publisher = item.getString("publisher");
//                        String imageUrl = item.getString("image");
//                        String isbn = item.getString("isbn");
//                        String description = item.getString("description");
//                        String D = departments.get(index-1);
//                        String S = subjects.get(index-1);
//                        bookService.createBook(title, author, publisher, discount, imageUrl, isbn, description, D, S);
//                    }
//                } else {
//                    System.out.println("에러 코드: " + responseCode);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return "/mypage";
//
//    }
//
//}
package com.project.LWBS.controller;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Department;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor; // 추가
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.*;

import com.project.LWBS.service.BookService;
import org.json.JSONArray;
import org.json.JSONObject;

@Controller
@RequestMapping()
public class BookRestController {
    private static EnrollmentService enrollmentService;
    private static BookService bookService;
    private static UserService userService;

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public BookRestController(EnrollmentService enrollmentService, BookService bookService, UserService userService) {
        this.enrollmentService = enrollmentService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/webscraping/{user_id}")
    public String hello(@PathVariable Long user_id) {
        // headless 옵션 추가
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        System.setProperty("webdriver.chrome.driver", "C:/Users/skrheem/IdeaProjects/Train/chromedriver-win64/chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);

        System.out.println("시작");

        String id = userService.findByUserId(user_id).getStudentId();
        String ps = userService.findByUserId(user_id).getStudentPw();

        driver.get("https://sso.nsu.ac.kr/login?redirect_url=https%3A%2F%2Fmypage.nsu.ac.kr%2Fmypage%2Fstudent%2F");

        WebElement elem = driver.findElement(By.id("id_input"));
        elem.sendKeys(id);

        WebElement passwordInput = driver.findElement(By.id("password_input"));
        passwordInput.sendKeys(ps);
        passwordInput.sendKeys(Keys.RETURN);

        sleep(1000);

        driver.get("https://mypage.nsu.ac.kr/mypage/student/?m1=A00020%2FHSK511%25");

        sleep(1000);

        int index1 = 2;
        String a = "//*[@id=\"wrapper\"]/div[1]/div/div/div[6]/div[1]/div/table/tbody/tr[";
        String b = "]/td[11]";

        int index2 = 1;
        String c = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr[";
        String d = "]/td[3]/span";

        List<String> bookNames = new ArrayList<>();
        List<String> departments = new ArrayList<>();
        List<String> subjects = new ArrayList<>();

        while (true) {
            String menuButtonXPath = a + index1 + b;

            try {
                WebElement menuButton = driver.findElement(By.xpath(menuButtonXPath));
                // JavaScriptExecutor를 사용하여 클릭
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuButton);
                sleep(100);

                while (true) {
                    String menuButtonXPath2 = c + index2 + d;
                    String subjectXPath = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[1]/div/div/table/tbody/tr[1]/td[4]/div/span[1]";
                    String departmentXPath = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[1]/div/div/table/tbody/tr[3]/td[4]/span";
                    try {
                        WebElement subjectElement = driver.findElement(By.xpath(subjectXPath));
                        String subject = subjectElement.getText();
                        sleep(100);

                        WebElement departmentElement = driver.findElement(By.xpath(departmentXPath));
                        String department = departmentElement.getText();
                        sleep(100);

                        WebElement bookElement = driver.findElement(By.xpath(menuButtonXPath2));
                        String book = bookElement.getText();
                        sleep(100);

                        if (book.length() >= 5) {
                            bookNames.add(book);
                            System.out.println(book);
                            departments.add(department);
                            System.out.println(departments);
                            subjects.add(subject);
                            System.out.println(subjects);
                        }
                        break;
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        index2++;
                        continue;
                    }
                }
                index1++;
                WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"popup_layout_list\"]/div/div[2]/div[1]/img"));
                closeButton.click();
                sleep(100);
                index2 = 1;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println("종료");
                break;
            }
        }

        bookNames.remove(2);
        departments.remove(2);
        subjects.remove(2);

        // 웹스크래핑으로 가져온 교재명을 Enrollment에 저장
//        for (String E : bookNames) {
//            enrollmentService.createEnrollment(E, userService.findByUserId(user_id).getId());
//        }

        sleep(100);
        driver.quit();
        System.out.println("교재명 리스트 (길이가 5 이상):");
        System.out.println("교재명");
        for (String v : bookNames) {
            System.out.println(v);
        }
        System.out.println();
        System.out.println("과목명");
        for (String s : subjects) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println("학과명");
        for (String f : departments) {
            System.out.println(f);
        }
        String clientId = "GjB1T5WYEFrpN4KIf6Pb";
        String clientSecret = "U9jyF2ZCDa";
        int index = 0;
        for (String term : bookNames) {
            index++;
            try {
                String encodedQuery = URLEncoder.encode(term, "UTF-8");
                String apiUrl = "https://openapi.naver.com/v1/search/book.json?query=" + encodedQuery + "&display=1";

                URL url = new URL(apiUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                int responseCode = con.getResponseCode();

                if (responseCode == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    br.close();

                    String responseBody = response.toString();

                    // JSON 응답 파싱
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    JSONArray items = jsonResponse.getJSONArray("items");
                    int LEN = items.length();
                    for(int i = 0; i < LEN; i++) {
                        JSONObject item = items.getJSONObject(i);
                        String title = item.getString("title");
                        String author = item.getString("author");
                        String discount = item.getString("discount");
                        String publisher = item.getString("publisher");
                        String imageUrl = item.getString("image");
                        String isbn = item.getString("isbn");
                        String description = item.getString("description");
                        String D = departments.get(index-1);
                        String S = subjects.get(index-1);
                        bookService.createBook(title, author, publisher, discount, imageUrl, isbn, description, D, S);
                        enrollmentService.createEnrollment(title, userService.findByUserId(user_id).getId());
                    }
                } else {
                    System.out.println("에러 코드: " + responseCode);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "/mypage";
    }
}