package com.project.LWBS.controller;

import com.project.LWBS.domain.User;
import com.project.LWBS.service.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.*;
import com.project.LWBS.service.BookService;
import org.json.JSONArray;
import org.json.JSONObject;
@Controller
@RequestMapping
public class GatherInfoController {
    private static BookService bookService;
    private static EnrollmentService enrollmentService;
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
    public GatherInfoController(BookService bookService, EnrollmentService enrollmentService, UserService userService) {
        this.bookService = bookService;
        this.enrollmentService = enrollmentService;
        this.userService = userService;
    }

    @GetMapping("/gatherInfo/{user_id}")
    public String gather(@PathVariable Long user_id) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        //System.setProperty("webdriver.chrome.driver", "C:/Users/skrheem/IdeaProjects/Train/chromedriver-win64/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/python/chromedriver");
        WebDriver driver = new ChromeDriver(options);

        String LMSid = "18102101";
        String LMSpw = "sk991116!";
        String clientId = "GjB1T5WYEFrpN4KIf6Pb";
        String clientSecret = "U9jyF2ZCDa";
        //String filePath = "C:\\Users\\skrheem\\Desktop\\failList.txt";
        String filePath = "/home/ubuntu/Train/failList.txt";

        System.out.println("시작");
        System.out.println("로그인 단계");


        driver.get("https://sugang.nsu.ac.kr/");

        driver.switchTo().frame("Main");

        WebElement elem = driver.findElement(By.xpath("/html/body/div[2]/main/div/form/input[1]"));
        elem.sendKeys(LMSid);

        WebElement passwordInput = driver.findElement(By.xpath("/html/body/div[2]/main/div/form/input[2]"));
        passwordInput.sendKeys(LMSpw);

        passwordInput.sendKeys(Keys.RETURN);
        sleep(1000);
        System.out.println("수강신청 페이지 접속 완료");
        
        // 관심과목 버튼
        WebElement interest = driver.findElement(By.xpath("//*[@id=\"2\"]"));
        interest.click();

        sleep(1000);
        // 전공 과목의 XPath
        // 대괄호 뒤에 정수를 입력 후 대괄호를 닫아 XPath를 완성.
        // 정수의 범위는 2~45
        String department = "//*[@id=\"pComboHakgwa\"]/option[";
        int SdepartmentIndex = 2;

        // 학년의 XPath
        // 대괄호 뒤에 정수를 입력 후 대괄호를 닫아 XPath를 완성.
        // 정수의 범위는 2~6 (6은 건축학과전용)
        String grade = "//*[@id=\"pComboGrade\"]/option[";
        int gradeIndex = 2;

        // 수업계획서의 XPath
        String syllabus1 = "//*[@id=";
        int syllabusIndex = 1;
        String syllabus2 = "/td[10]";

        List<String> bookList = new ArrayList<>();
        List<String> failBookList = new ArrayList<>();

        // 전공을 순회하는 반복문
        while(true) {
            try {
                // 2번 전공부터 클릭
                driver.findElement(By.xpath(department + SdepartmentIndex + "]")).click();
                // 학과명을 수집
                String departmentName = driver.findElement(By.xpath(department + SdepartmentIndex + "]")).getText();
                System.out.println("학과명 : " + departmentName);
                bookService.createDepartment(departmentName);
                sleep(500);
                // 학년을 순회하는 반복문
                while(true) {
                    if(gradeIndex == 6) {
                        // 5학년까지 순회를 마쳤다면 학년의 값을 2로 초기화 후 반복문 종료
                        System.out.println("더이상 학년 정보가 없음!");
                        gradeIndex = 2;
                        // 한 개 전공의 1~5학년 강의계획서를 탐색했다면 다음 전공 탐색을 위해 값을 1 증가
                        SdepartmentIndex++;
                        break;
                    }
                    System.out.println("현재 학년 : " + (gradeIndex - 1));
                    // 1학년부터 클릭
                    driver.findElement(By.xpath(grade + gradeIndex + "]")).click();
                    System.out.println("학년 설정 완료");
                    System.out.println("검색 버튼 클릭 전");
                    // 조회 버튼 클릭
                    driver.findElement(By.xpath("//*[@id=\"btnSearch\"]")).click();
                    System.out.println("검색 버튼 클릭 후");
                    sleep(1000);
                    while (true) {
                        try {
                            // 강의 계획서 클릭
                            driver.findElement(By.xpath(syllabus1 + "\"" + syllabusIndex + "\"" + "]" +  syllabus2)).click();
                            sleep(500);
                            // 과목명 수집
                            String subjectName = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div/div/div/div/div[3]/div/div/div/table[1]/tbody/tr[1]/td[2]")).getText();
                            sleep(500);
                            bookService.createSubject(subjectName);
                            // 교재명 수집
                            String bookName = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div/div/div/div/div[3]/div/div/div/table[3]/tbody/tr[1]/td")).getText();
                            sleep(500);

                            if (!bookList.contains(subjectName) && !bookList.contains(bookName)) {
                                // bookList에 동일한 강의명이 존재하지 않으며 동일한 교재명이 존재하지 않을 시 리스트에 저장
                                // 0:학과명/1:강의명/2:교재명 ...
                                System.out.println("배열 저장 시작");
                                bookList.add(departmentName);
                                System.out.println("학과명 : " + departmentName);
                                bookList.add(subjectName);
                                System.out.println("과목명 : " + subjectName);
                                bookList.add(bookName);
                                System.out.println("교재명 : " + bookName);
                                System.out.println("배열 저장 종료");
                            }
                            driver.findElement(By.cssSelector("body > div.jconfirm.jconfirm-dialog.jconfirm-open > div.jconfirm-scrollpane > div > div > div > div > div > div > div > div.jconfirm-closeIcon")).click();
                            sleep(100);
                            // 다음 강의 계획서 탐색
                            syllabusIndex++;
                        } catch (NoSuchElementException e) {
                            // 더이상 강의 계획서가 존재하지 않는다면 강의 계획서의 값을 1로 초기화 후 반복문 종료
                            System.out.println("더이상 강의 계획서가 없음!");
                            syllabusIndex = 1;
                            break;
                        }
                    }
                    // 네이버 검색 API를 호출 후 다음 학년을 순회
                    System.out.println(gradeIndex - 1 + "학년 수집 완료");
                    System.out.println("네이버 검색 API 호출");
                    for(int index = 0; index < bookList.size(); index += 3) {
                        int departmentIndex = index;
                        int subjectIndex = index + 1;
                        int bookNameIndex = index + 2;
                        try {
                            // 도서명을 UTF-8로 인코딩
                            String encodedQuery = URLEncoder.encode(bookList.get(bookNameIndex), "UTF-8");

                            // Naver 도서 검색 API의 엔드포인트 URL 생성
                            String apiUrl = "https://openapi.naver.com/v1/search/book.json?query=" + encodedQuery + "&display=1";

                            // URL 객체 생성
                            URL url = new URL(apiUrl);

                            // HTTP 연결 설정
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("GET");
                            con.setRequestProperty("X-Naver-Client-Id", clientId);
                            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                            System.out.println("departmentIndex : "+departmentIndex);
                            System.out.println("bookNameIndex : "+bookNameIndex);
                            System.out.println("subjectIndex : " + subjectIndex);
                            //System.out.println(bookNames.get(bookNameIndex) + "의 응답 코드 가져오는중");
                            // HTTP 응답 코드 가져오기
                            int responseCode = con.getResponseCode();

                            if (responseCode == 200) { // 성공적인 응답인 경우
                                System.out.println("응답 코드 가져오기 성공");
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
                                System.out.println("교재 정보 개수 : " + LEN);
                                if(LEN == 0) {
                                    System.out.println(bookList.get(bookNameIndex));
                                    failBookList.add(bookList.get(departmentIndex));
                                    failBookList.add(bookList.get(bookNameIndex));
                                    failBookList.add(bookList.get(subjectIndex));
                                    continue;
                                }
                                for(int i = 0; i < LEN; i++) {
                                    JSONObject item = items.getJSONObject(i);
                                    // 교재 정보 추출
                                    String title = item.getString("title");
                                    System.out.println("교재 이름 : " + title);
                                    String author = item.getString("author");
                                    String discount = item.getString("discount");
                                    String publisher = item.getString("publisher");
                                    String imageUrl = item.getString("image");
                                    String isbn = item.getString("isbn");
                                    String description = item.getString("description");
                                    // 해당 교재의 학과 및 과목 정보 가져오기
                                    String D = bookList.get(departmentIndex);
                                    String S = bookList.get(subjectIndex);

                                    // Book 객체에 교재 정보를 담아 Service에게 전달
                                    bookService.createBook(title, author, publisher, discount, imageUrl, isbn, description, D, S);
                                    System.out.println("데이터베이스에 값 전달 완료");
                                }
                                FileWriter fileWriter = new FileWriter(filePath);
                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                for (String fail : failBookList) {
                                    bufferedWriter.write(fail);
                                    bufferedWriter.newLine();
                                }
                                bufferedWriter.flush();
                                bufferedWriter.close();
                            }
                        } catch (IOException e) {
                            // 예외 발생 시 스택 트레이스 출력
                            e.printStackTrace();
                        }
                    }
                    gradeIndex++;
                }
            } catch (NoSuchElementException e) {
                // 모든 전공을 탐색했다면 반복문 종료
                System.out.println("더이상 학과 정보가 없음!");
                break;
            }
        }
        for (int i = 0; i < bookList.size(); i++) {
            System.out.print(bookList.get(i)); // 요소 출력
            if ((i + 1) % 3 == 0) { // 3의 배수인 경우 개행 추가
                System.out.println();
            } else {
                System.out.print(" "); // 공백 추가
            }
        }
        return "mypage";
    }
}
