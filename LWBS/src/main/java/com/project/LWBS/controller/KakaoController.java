package com.project.LWBS.controller;

import com.project.LWBS.common.MsgEntity;
import com.project.LWBS.domain.DTO.KakaoDTO;
import com.project.LWBS.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;

//    @GetMapping("/callback")
//    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception{
//        KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));
//
//        return ResponseEntity.ok().body(new MsgEntity("Success",kakaoDTO));
//    }


    @GetMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));

        // 다음 페이지로의 URL을 클라이언트에게 전달
        String redirectUrl = "http://localhost:8093/user/registerOk";  // 실제 다음 페이지의 URL로 대체해야 합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        // 302 Found 상태 코드와 함께 리디렉션 URL을 클라이언트에게 전달
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
