package com.project.LWBS.controller;

import com.project.LWBS.common.MsgEntity;
import com.project.LWBS.domain.DTO.KakaoDTO;
import com.project.LWBS.service.KakaoService;
import com.project.LWBS.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private UserService userService;

    @Autowired
    public KakaoController(KakaoService kakaoService, UserService userService) {
        this.kakaoService = kakaoService;
        this.userService = userService;
    }

    //    @GetMapping("/callback")
//    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception{
//        KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));
//
//
//    }


    @GetMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));
        String kakaoId = String.valueOf(kakaoDTO.getId());
        if(!userService.isExistKakaoIdByUser(kakaoId))
        {
            // 다음 페이지로의 URL을 클라이언트에게 전달
            String redirectUrl = "http://localhost:8093/user/register";  // 실제 다음 페이지의 URL로 대체해야 합니다.
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", redirectUrl);

            // 302 Found 상태 코드와 함께 리디렉션 URL을 클라이언트에게 전달
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

        return ResponseEntity.ok().body(new MsgEntity("Success",kakaoDTO));

    }
}
