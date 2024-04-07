package com.project.LWBS.controller;

import com.project.LWBS.common.MsgEntity;
import com.project.LWBS.config.PrincipalDetailsService;
import com.project.LWBS.domain.DTO.KakaoDTO;
import com.project.LWBS.domain.User;
import com.project.LWBS.service.EnrollmentService;
import com.project.LWBS.service.KakaoService;
import com.project.LWBS.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    private UserService userService;

    private final PrincipalDetailsService principalDetailsService;

    private EnrollmentService enrollmentService;

    @Autowired
    public KakaoController(KakaoService kakaoService, UserService userService, PrincipalDetailsService principalDetailsService, EnrollmentService enrollmentService) {
        this.kakaoService = kakaoService;
        this.userService = userService;
        this.principalDetailsService = principalDetailsService;
        this.enrollmentService = enrollmentService;
    }
    @GetMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));
        String kakaoId = String.valueOf(kakaoDTO.getId());
        User user = userService.findByKakaoId(kakaoId);
        String redirectUrl = "";
        if(user == null)
        {
            userService.setKakaoDTO(kakaoDTO);
            // 다음 페이지로의 URL을 클라이언트에게 전달
//            redirectUrl = "http://13.53.50.7:8093/user/register";
            redirectUrl = "http://localhost:8093/user/register";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", redirectUrl);

            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

        // 카카오 로그인 성공 시, PrincipalDetails를 사용하여 인증 정보를 설정
        UserDetails userDetails = principalDetailsService.loadUserByUsername(kakaoId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(user.getAuthority().getName().equals("ROLE_STUDENT"))
        {
            if(!enrollmentService.isEmptyData(user))
            {
//                redirectUrl = "http://13.53.50.7:8093/home/student";
                redirectUrl = "http://localhost:8093/home/student";
            }
            else
            {
                redirectUrl = "http://localhost:8093/mypage/" + user.getId();
            }
        }
        else if(user.getAuthority().getName().equals("ROLE_BOOKSTORE"))
        {
            redirectUrl = "http://localhost:8093/home/bookStore";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }
}
