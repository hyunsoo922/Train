package com.project.LWBS.service;

import com.project.LWBS.domain.Authority;
import com.project.LWBS.domain.DTO.KakaoDTO;
import com.project.LWBS.domain.User;
import com.project.LWBS.repository.AuthorityRepository;
import com.project.LWBS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private KakaoDTO kakaoDTO;



    @Override
    public User findByKakaoId(String kakao_id) {
        return userRepository.findByKakaoId(kakao_id);
    }

    @Override
    public void registerUser(String kind, String LMSID, String LMSPW, String publisherId,KakaoDTO kakaoDTO) {
        Authority student = authorityRepository.findById(1L).orElse(null);
        Authority bookStore = authorityRepository.findById(2L).orElse(null);
        User user = new User();
        if(kind.equals("student"))
        {
            user = User.builder()
                    .authority(student)
                    .studentId(LMSID)
                    .studentPw(LMSPW)
                    .name(kakaoDTO.getNickname())
                    .kakaoId(String.valueOf(kakaoDTO.getId()))
                    .profileImgUrl(kakaoDTO.getProfile_img())
                    .franchisee(null)
                    .build();
        }
        else if(kind.equals("bookStore"))
        {
            user = User.builder()
                    .authority(bookStore)
                    .franchisee(publisherId)
                    .name(kakaoDTO.getNickname())
                    .kakaoId(String.valueOf(kakaoDTO.getId()))
                    .profileImgUrl(kakaoDTO.getProfile_img())
                    .studentPw(null)
                    .studentId(null)
                    .build();
        }

        userRepository.saveAndFlush(user);
    }

    @Override
    public KakaoDTO getKakaoDTO() {
        return kakaoDTO;
    }

    @Override
    public void setKakaoDTO(KakaoDTO kakaoDTO) {
        this.kakaoDTO = kakaoDTO;
    }

    @Override
    public List<Authority> findAuthorityById(Long id) {
        List<Authority> list = new ArrayList<>();
        list.add(userRepository.findById(id).get().getAuthority());
        return list;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public Authority findById(Long authority_id) {
        Authority authority = authorityRepository.findById(authority_id).orElse(null);
        return authority;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}