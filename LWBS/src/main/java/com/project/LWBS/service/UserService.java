package com.project.LWBS.service;

import com.project.LWBS.config.PrincipalDetails;
import com.project.LWBS.domain.Authority;
import com.project.LWBS.domain.DTO.KakaoDTO;
import com.project.LWBS.domain.User;
import java.util.List;

public interface UserService {

    User findByKakaoId(String kakao_id);

    Long registerUser(String kind,String LMSID,String LMSPW,String publisherId,KakaoDTO kakaoDTO);

    KakaoDTO getKakaoDTO();

    void setKakaoDTO(KakaoDTO kakaoDTO);

    List<Authority> findAuthorityById(Long id);

    User findByUsername(String username);

    Authority findById(Long authority_id);

    User findByUserId(Long user_name);

    void updateUserInfo(String studentId, String studentPw, Long user_id, PrincipalDetails principalDetails);

    void updateBookStoreInfo(String franchisee, Long user_id, PrincipalDetails principalDetails);

    void deleteUser(User user);

    String findAuthority(Long user_id);

}

