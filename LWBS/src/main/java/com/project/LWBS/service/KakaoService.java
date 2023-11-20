package com.project.LWBS.service;

import com.project.LWBS.domain.DTO.KakaoDTO;

public interface KakaoService {
    String getKakaoLogin();

    KakaoDTO getKakaoInfo(String code) throws Exception;

    KakaoDTO getUserInfoWithUserToken(String accessToken) throws Exception;
}
