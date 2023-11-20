package com.project.LWBS.controller;

import com.project.LWBS.common.MsgEntity;
import com.project.LWBS.domain.DTO.KakaoDTO;
import com.project.LWBS.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception{
        KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));

        return ResponseEntity.ok().body(new MsgEntity("Success",kakaoDTO));
    }
}
