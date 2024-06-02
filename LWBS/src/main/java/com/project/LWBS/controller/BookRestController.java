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

    @Autowired
    public BookRestController(EnrollmentService enrollmentService, BookService bookService, UserService userService) {
        this.enrollmentService = enrollmentService;
        this.bookService = bookService;
        this.userService = userService;
    }

    // 브라우저 조작을 위한 sleep 함수
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/webscraping/{user_id}")
    public String webscraping(@PathVariable Long user_id) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        //System.setProperty("webdriver.chrome.driver", "C:/Users/skrheem/IdeaProjects/Train/chromedriver-win64/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/python/chromedriver");
        WebDriver driver = new ChromeDriver(options);

        System.out.println("시작");

        String id = userService.findByUserId(user_id).getStudentId();
        String pw = userService.findByUserId(user_id).getStudentPw();

        driver.get("https://sso.nsu.ac.kr/login?redirect_url=https%3A%2F%2Fmypage.nsu.ac.kr%2Fmypage%2Fstudent%2F");
        System.out.println("로그인 단계");
        WebElement elem = driver.findElement(By.id("id_input"));
        elem.sendKeys(id);

        WebElement passwordInput = driver.findElement(By.id("password_input"));
        passwordInput.sendKeys(pw);
        passwordInput.sendKeys(Keys.RETURN);
        sleep(1000);
        driver.get("https://mypage.nsu.ac.kr/mypage/student/?m1=A00020%2FHSK511%25");
        System.out.println("수강신청확인 페이지 접속");
        sleep(1000);
        System.out.println("XPath 조합 시작");
        int professorButtonIndex = 2;
        String professorButton1 = "//*[@id=\"wrapper\"]/div[1]/div/div/div[6]/div[1]/div/table/tbody/tr[";
        String professorButton2 = "]/td[11]";
        int BookInfoIndex = 1;
        String BookInfo1 = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr[";
        String BookInfo2 = "]/td[3]/span";
        List<String> bookInfo = new ArrayList<>();
        System.out.println("XPath 조합 완료");
        sleep(1000);
        while (true) {
            String menuButtonXPath = professorButton1 + professorButtonIndex + professorButton2;// 메뉴 버튼 XPath 조합
            try {
                WebElement menuButton = driver.findElement(By.xpath(menuButtonXPath));// 메뉴 버튼 XPath에 해당하는 웹 요소를 탐색
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuButton);// 메뉴 버튼을 클릭
                sleep(1000);
                System.out.println("메뉴 버튼 클릭");
                while (true) {
                    String menuButtonXPath2 = BookInfo1 + BookInfoIndex + BookInfo2;// 주교재명 XPath 조합
                    String subjectXPath = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[1]/div/div/table/tbody/tr[1]/td[4]/div/span[1]";// 과목 요소 XPath
                    String departmentXPath = "//*[@id=\"popup_layout_list\"]/div/div[2]/div[2]/div/div[1]/div/div/table/tbody/tr[3]/td[4]/span";// 학과 요소 XPath
                    System.out.println("주교재, 과목, 학과 XPath 확인");
                    try {
                        System.out.println("주교재명 XPath 탐색 시작");
                        WebElement bookElement = driver.findElement(By.xpath(menuButtonXPath2));// 주교재명 XPath에 해당하는 웹 요소를 탐색
                        System.out.println("웹 요소 저장");// 수집한 웹 요소를 저장
                        String book = bookElement.getText();
                        sleep(1000);
                        if (book.length() <= 5) {
                            System.out.println("수집한 교재명의 길이가 5글자보다 짧습니다.");
                            System.out.println("수집한 교재명 길이: " + book.length());
                            System.out.println("다음 수강계획서 탐색");
                            break;
                        } else {
                            System.out.println("과목 XPath 탐색 시작");
                            WebElement subjectElement = driver.findElement(By.xpath(subjectXPath));// 과목 요소 XPath에 해당하는 웹 요소를 탐색
                            System.out.println("웹 요소 저장");// 수집한 웹 요소를 저장
                            String subject = subjectElement.getText();
                            sleep(1000);// 1초 대기
                            System.out.println("학과 XPath 탐색 시작");
                            WebElement departmentElement = driver.findElement(By.xpath(departmentXPath));// 학과 요소 XPath에 해당하는 웹 요소를 탐색
                            System.out.println("웹 요소 저장");// 수집한 웹 요소를 저장
                            String department = departmentElement.getText();
                            sleep(1000);
                            bookInfo.add(department);
                            bookInfo.add(book);
                            bookInfo.add(subject);
                            System.out.println("웹스크래핑 종료");
                            break;
                        }
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        BookInfoIndex++;// 교재명이 없는 경우 다음 교재명을 탐색
                        continue;
                    }
                }
                // 다음 강의의 담당교수 버튼으로 이동하기 위해 XPATH 수정
                professorButtonIndex++;

                // x 버튼을 찾고 클릭
                WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"popup_layout_list\"]/div/div[2]/div[1]/img"));
                closeButton.click();
                sleep(1000);

                // 다음 강의 계획서에서 첫 번째 주교재 항목을 찾도록 초기화
                BookInfoIndex = 1;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // 수강신청 리스트 페이지에서 수강신청한 강의를 찾지 못할 시 종료
                System.out.println("종료");
                break;
            }
        }

        sleep(1000);
        driver.quit();


        //String filePath = "C:\\Users\\skrheem\\Desktop\\failList.txt";
        String filePath = "/home/ubuntu/failList";
        System.out.println("네이버 API 시작");
        String clientId = "GjB1T5WYEFrpN4KIf6Pb";
        String clientSecret = "RAXseD1BDm";
        List<String> failList = new ArrayList<>(); // 실패한 책 제목을 저장할 리스트 생성

        for (int index = 0; index < bookInfo.size(); index += 3) {
            int departmentIndex = index;
            int bookNameIndex = index + 1;
            int subjectIndex = index + 2;
            try {
                String encodedQuery = URLEncoder.encode(bookInfo.get(bookNameIndex), "UTF-8");// 도서명을 UTF-8로 인코딩
                String apiUrl = "https://openapi.naver.com/v1/search/book.json?query=" + encodedQuery + "&display=1";// Naver 도서 검색 API의 엔드포인트 URL 생성
                URL url = new URL(apiUrl);// URL 객체 생성
                HttpURLConnection con = (HttpURLConnection) url.openConnection();// HTTP 연결 설정
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                System.out.println("departmentIndex : " + departmentIndex);
                System.out.println("bookNameIndex : " + bookNameIndex);
                System.out.println("subjectIndex : " + subjectIndex);
                System.out.println("응답 코드 가져오는중");
                int responseCode = con.getResponseCode();// HTTP 응답 코드 가져오기
                if (responseCode == 200) { // 성공적인 응답인 경우
                    System.out.println("응답 코드 가져오기 성공");
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));// 응답 내용을 읽기 위한 BufferedReader 생성
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {// 응답 내용을 문자열로 변환
                        response.append(line);
                    }
                    br.close();
                    String responseBody = response.toString();// 응답 내용을 JSON 객체로 파싱
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    JSONArray items = jsonResponse.getJSONArray("items");// 교재 정보가 담긴 배열 추출
                    int LEN = items.length();
                    System.out.println("교재 정보 개수 : " + LEN);
                    if (LEN == 0) {
                        System.out.println(bookInfo.get(bookNameIndex));
                        failList.add(bookInfo.get(departmentIndex));
                        failList.add(bookInfo.get(bookNameIndex));
                        failList.add(bookInfo.get(subjectIndex));
                        System.out.println(failList);
                        continue;
                    }
                    for (int i = 0; i < LEN; i++) {
                        JSONObject item = items.getJSONObject(i);
                        String title = item.getString("title");// 교재 정보 추출
                        System.out.println("교재 이름 : " + title);
                        String author = item.getString("author");
                        String discount = item.getString("discount");
                        String publisher = item.getString("publisher");
                        String imageUrl = item.getString("image");
                        String isbn = item.getString("isbn");
                        String description = item.getString("description");
                        String D = bookInfo.get(departmentIndex);// 해당 교재의 학과 및 과목 정보 가져오기
                        String S = bookInfo.get(subjectIndex);
                        bookService.createDepartment(D);
                        bookService.createSubject(S);
                        bookService.createBook(title, author, publisher, discount, imageUrl, isbn, description, D, S);// Book 객체에 교재 정보를 담아 Service에게 전달
                        enrollmentService.createEnrollment(title, userService.findByUserId(user_id).getId());// Enrollment 객체에 교재명을 담아 Service에게 전달
                        System.out.println("데이터베이스에 값 전달 완료");
                    }

                }
            } catch (IOException e) {
                // 예외 발생 시 스택 트레이스 출력
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("학과명 - 교재명 - 과목명");
            bufferedWriter.newLine();
            for (int failIndex = 0; failIndex < failList.size(); failIndex++) {
                System.out.println(failList.get(failIndex));
                bufferedWriter.write(failList.get(failIndex));
                if (failIndex % 3 == 2) {
                    bufferedWriter.newLine();
                } else if (failIndex % 3 == 0 || failIndex % 3 == 1) {
                    bufferedWriter.write(" - ");
                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/mypage/" + user_id;
    }
}
