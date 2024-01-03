package com.project.LWBS.controller;

import com.project.LWBS.service.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping
public class BookRestController {
    private static EnrollmentService enrollmentService;
    private static BookService bookService;
    private static UserService userService;

    // 브라우저 조작을 위한 sleep 함수
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

        int professorButtonIndex = 2;
        String professorButton1 = "//*[@id=\"wrapper\"]/div[1]/div/div/div[6]/div[1]/div/table/tbody/tr[";
        String professorButton2 = "]/td[11]";

        int BookInfoIndex = 1;
        String BookInfo1 = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr[";
        String BookInfo2 = "]/td[3]/span";

        List<String> bookNames = new ArrayList<>();
        List<String> departments = new ArrayList<>();
        List<String> subjects = new ArrayList<>();

        while (true) {
            // 메뉴 버튼 XPath 조합
            String menuButtonXPath = professorButton1 + professorButtonIndex + professorButton2;
            try {
                // 메뉴 버튼 XPath에 해당하는 웹 요소를 탐색
                WebElement menuButton = driver.findElement(By.xpath(menuButtonXPath));
                // 메뉴 버튼을 클릭
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuButton);
                sleep(100);
                while (true) {
                    // 주교재명 XPath 조합
                    String menuButtonXPath2 = BookInfo1 + BookInfoIndex + BookInfo2;
                    // 과목 요소 XPath
                    String subjectXPath = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[1]/div/div/table/tbody/tr[1]/td[4]/div/span[1]";
                    // 학과 요소 XPath
                    String departmentXPath = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[1]/div/div/table/tbody/tr[3]/td[4]/span";
                    try {
                        // 과목 요소 XPath에 해당하는 웹 요소를 탐색
                        WebElement subjectElement = driver.findElement(By.xpath(subjectXPath));
                        // 수집한 웹 요소를 저장
                        String subject = subjectElement.getText();
                        // 0.1초 대기
                        sleep(100);

                        // 학과 요소 XPath에 해당하는 웹 요소를 탐색
                        WebElement departmentElement = driver.findElement(By.xpath(departmentXPath));
                        // 수집한 웹 요소를 저장
                        String department = departmentElement.getText();
                        sleep(100);

                        // 주교재명 XPath에 해당하는 웹 요소를 탐색
                        WebElement bookElement = driver.findElement(By.xpath(menuButtonXPath2));
                        // 수집한 웹 요소를 저장
                        String book = bookElement.getText();
                        sleep(100);

                        // 주교재명의 길이가 5 이상인 이름만 리스트에 저장
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
                        // 교재명이 없는 경우 다음 교재명을 탐색
                        BookInfoIndex++;
                        continue;
                    }
                }
                // 다음 강의의 담당교수 버튼으로 이동하기 위해 XPATH 수정
                professorButtonIndex++;

                // x 버튼을 찾고 클릭
                WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"popup_layout_list\"]/div/div[2]/div[1]/img"));
                closeButton.click();
                sleep(100);

                // 다음 강의 계획서에서 첫 번째 주교재 항목을 찾도록 초기화
                BookInfoIndex = 1;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // 수강신청 리스트 페이지에서 수강신청한 강의를 찾지 못할 시 종료
                System.out.println("종료");
                break;
            }
        }

        // 자바 교재 때문에 넣음.
        bookNames.remove(2);
        departments.remove(2);
        subjects.remove(2);

        sleep(100);
        driver.quit();

        String clientId = "GjB1T5WYEFrpN4KIf6Pb";
        String clientSecret = "U9jyF2ZCDa";
        int index = 0;
        for (String term : bookNames) {
            index++;
            try {
                // 도서명을 UTF-8로 인코딩
                String encodedQuery = URLEncoder.encode(term, "UTF-8");

                // Naver 도서 검색 API의 엔드포인트 URL 생성
                String apiUrl = "https://openapi.naver.com/v1/search/book.json?query=" + encodedQuery + "&display=1";

                // URL 객체 생성
                URL url = new URL(apiUrl);

                // HTTP 연결 설정
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                // HTTP 응답 코드 가져오기
                int responseCode = con.getResponseCode();

                if (responseCode == 200) { // 성공적인 응답인 경우
                    // 응답 내용을 읽기 위한 BufferedReader 생성
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    // 응답 내용을 문자열로 변환
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    br.close();

                    // 응답 내용을 JSON 객체로 파싱
                    String responseBody = response.toString();
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    // 교재 정보가 담긴 배열 추출
                    JSONArray items = jsonResponse.getJSONArray("items");
                    int LEN = items.length();

                    for(int i = 0; i < LEN; i++) {
                        JSONObject item = items.getJSONObject(i);

                        // 교재 정보 추출
                        String title = item.getString("title");
                        String author = item.getString("author");
                        String discount = item.getString("discount");
                        String publisher = item.getString("publisher");
                        String imageUrl = item.getString("image");
                        String isbn = item.getString("isbn");
                        String description = item.getString("description");

                        // 해당 교재의 학과 및 과목 정보 가져오기
                        String D = departments.get(index-1);
                        String S = subjects.get(index-1);

                        // Book 객체에 교재 정보를 담아 Service에게 전달
                        bookService.createBook(title, author, publisher, discount, imageUrl, isbn, description, D, S);
                        // Enrollment 객체에 교재명을 담아 Service에게 전달
                        enrollmentService.createEnrollment(title, userService.findByUserId(user_id).getId());
                    }
                } else {
                    // 에러 코드 출력
                    System.out.println("에러 코드: " + responseCode);
                }

            } catch (IOException e) {
                // 예외 발생 시 스택 트레이스 출력
                e.printStackTrace();
            }
        }
        return "/mypage";
    }
}