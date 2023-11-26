package com.project.LWBS.service;

import com.project.LWBS.domain.DTO.KakaoDTO;

public interface UserService {

    Boolean isExistKakaoIdByUser(String kakao_id);

    void registerUser(String kind,String LMSID,String LMSPW,String publisherId,KakaoDTO kakaoDTO);

    KakaoDTO getKakaoDTO();

    void setKakaoDTO(KakaoDTO kakaoDTO);
}
