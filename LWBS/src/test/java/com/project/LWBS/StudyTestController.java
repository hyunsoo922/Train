package com.project.LWBS;

public class StudyTestController {



//import com.project.LWBS.config.PrincipalDetails;
//import com.project.LWBS.domain.User;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//    @Controller
//    @RequestMapping("/")
//    public class isgHomeController {
//        @RequestMapping("/")
//        public String home(){
//            return "redirect:/home";
//        }
//
//        @RequestMapping("/home")
//        public void home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
//            try{
////            PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////            User user = userDetails.getUser();
////            Long id = user.getId();
//                System.out.println("홈에 들어옴 로그인"+principalDetails.getUsername());
//                model.addAttribute("user",principalDetails.getUser());
//            } catch (Exception e){
//                System.out.println("로그인실패");
//                model.addAttribute("logged_id", null);
//            }
//        }
//
//        @RequestMapping("/auth")
//        @ResponseBody
//        public Authentication auth(){
//            return SecurityContextHolder.getContext().getAuthentication();
//        }
//    }



//import com.project.LWBS.common.MsgEntity;
//import com.project.LWBS.config.PrincipalDetailsService;
//import com.project.LWBS.domain.DTO.KakaoDTO;
//import com.project.LWBS.domain.User;
//import com.project.LWBS.service.KakaoService;
//import com.project.LWBS.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//    @RestController
//    @RequiredArgsConstructor
//    @RequestMapping("kakao")
//    public class isgKakaoController {
//
//        private final KakaoService kakaoService;
//
//        private UserService userService;
//
//        private final PrincipalDetailsService principalDetailsService;
//
//        @Autowired
//        public KakaoController(KakaoService kakaoService, UserService userService, PrincipalDetailsService principalDetailsService) {
//            this.kakaoService = kakaoService;
//            this.userService = userService;
//            this.principalDetailsService = principalDetailsService;
//        }
//
//
//
//    @GetMapping("/callback")
//    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception{
//        KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));
//
//
//    }
//
//
//        @GetMapping("/callback")
//        public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
//            KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));
//            String kakaoId = String.valueOf(kakaoDTO.getId());
//            User user = userService.findByKakaoId(kakaoId);
//            String redirectUrl = "";
//            if(user == null)
//            {
//                userService.setKakaoDTO(kakaoDTO);
//                // 다음 페이지로의 URL을 클라이언트에게 전달
//                redirectUrl = "http://localhost:8093/user/register";  // 실제 다음 페이지의 URL로 대체해야 합니다.
//                HttpHeaders headers = new HttpHeaders();
//                headers.add("Location", redirectUrl);
//
//                // 302 Found 상태 코드와 함께 리디렉션 URL을 클라이언트에게 전달
//                return new ResponseEntity<>(headers, HttpStatus.FOUND);
//            }
//
//            // 카카오 로그인 성공 시, PrincipalDetails를 사용하여 인증 정보를 설정
//            UserDetails userDetails = principalDetailsService.loadUserByUsername(kakaoId);
//            System.out.println(userDetails+"유저디테일");
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println(SecurityContextHolder.getContext().getAuthentication()+"권한");
//
//            if(user.getAuthority().getName().equals("ROLE_STUDENT"))
//            {
//                redirectUrl = "http://localhost:8093/home/student";
//            }
//            else if(user.getAuthority().getName().equals("ROLE_BOOKSTORE"))
//            {
//                redirectUrl = "http://localhost:8093/home/bookStore";
//            }
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Location", redirectUrl);
//
//            return new ResponseEntity<>(headers, HttpStatus.FOUND);
//
//        }
//    }

//import com.project.LWBS.config.PrincipalDetails;
//import com.project.LWBS.service.MyPageService;
//import com.project.LWBS.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Map;
//
//    @Controller
//    @RequestMapping
//    public class isgMyPageController {
//        private MyPageService mypageService;
//
//        @GetMapping("/mypage/{user_id}")
//        public String hello(@PathVariable Long user_id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//            int a = mypageService.sumMileage(user_id);
//            String b = mypageService.getName(user_id);
//            String c = mypageService.getProfile(user_id);
//            try{
//  //           PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//  //           User user = userDetails.getUser();
//  //           Long id = user.getId();
//                System.out.println("홈에 들어옴 로그인"+principalDetails.getUsername());
//                model.addAttribute("user",principalDetails.getUser());
//                model.addAttribute("sumpoint",a);
//                model.addAttribute("name", b);
//                model.addAttribute("URL", c);
//            } catch (Exception e){
//                System.out.println("로그인실패");
//                model.addAttribute("logged_id", null);
//            }
//            return "/mypage";
//        }
//        private UserService userService;
//        @Autowired
//        public void MyPageService(MyPageService mypageService) {
//            this.mypageService = mypageService;
//        }
//        // 로그인된 사용자의 id값을 받아와서 /mypage/{user_id}로 넘긴다.
//    }


//import com.project.LWBS.domain.DTO.KakaoDTO;
//import com.project.LWBS.domain.User;
//import com.project.LWBS.service.KakaoService;
//import com.project.LWBS.service.UserService;
//import com.project.LWBS.service.UserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//    @Controller
//    @RequestMapping("/user")
//    public class isgUserController {
//
//        private final KakaoService kakaoService;
//
//        private UserService userService;
//
//        @Autowired
//        public UserController(KakaoService kakaoService, UserService userService) {
//            this.kakaoService = kakaoService;
//            this.userService = userService;
//        }
//
//        @GetMapping("/login")
//        public String getLogin(Model model)
//        {
//            model.addAttribute("kakaoUrl",kakaoService.getKakaoLogin());
//            return "user/login";
//        }
//
//        @GetMapping("/register")
//        public void register(Model model)
//        {
//
//        }
//
//        @PostMapping("/register")
//        public String registerOk(@RequestParam("kind") String kind,@RequestParam(name = "LMSID", required = false) String LMSID,
//                                 @RequestParam(name = "LMSPW",required = false) String LMSPW,
//                                 @RequestParam(name = "publisherId",required = false) String publisherId){
//
//            System.out.println("종류"+kind);
//            System.out.println("lmsid"+LMSID);
//            System.out.println("lmspw"+LMSPW);
//            System.out.println("가맹점아이디"+publisherId);
//            KakaoDTO kakaoDTO = userService.getKakaoDTO();
//            System.out.println("!!!!카카오아이디!!!!" + kakaoDTO);
//            userService.registerUser(kind,LMSID,LMSPW,publisherId,kakaoDTO);
//
//
//            return "user/registerOk";
//        }
//    }


//import com.project.LWBS.common.MsgEntity;
//import com.project.LWBS.config.PrincipalDetailsService;
//import com.project.LWBS.domain.DTO.KakaoDTO;
//import com.project.LWBS.domain.User;
//import com.project.LWBS.service.KakaoService;
//import com.project.LWBS.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//    @RestController
//    @RequiredArgsConstructor
//    @RequestMapping("kakao")
//    public class khsKakaoController {
//
//        private final KakaoService kakaoService;
//
//        private UserService userService;
//
//        private final PrincipalDetailsService principalDetailsService;
//
//        @Autowired
//        public KakaoController(KakaoService kakaoService, UserService userService, PrincipalDetailsService principalDetailsService) {
//            this.kakaoService = kakaoService;
//            this.userService = userService;
//            this.principalDetailsService = principalDetailsService;
//        }
//
//
//
//
//        //    @GetMapping("/callback")
////    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception{
////        KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));
////
////
////    }
//
//
//        @GetMapping("/callback")
//        public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
//            KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(request.getParameter("code"));
//            String kakaoId = String.valueOf(kakaoDTO.getId());
//            User user = userService.findByKakaoId(kakaoId);
//            String redirectUrl = "";
//            if(user == null)
//            {
//                userService.setKakaoDTO(kakaoDTO);
//                // 다음 페이지로의 URL을 클라이언트에게 전달
//                redirectUrl = "http://localhost:8093/user/register";  // 실제 다음 페이지의 URL로 대체해야 합니다.
//                HttpHeaders headers = new HttpHeaders();
//                headers.add("Location", redirectUrl);
//
//                // 302 Found 상태 코드와 함께 리디렉션 URL을 클라이언트에게 전달
//                return new ResponseEntity<>(headers, HttpStatus.FOUND);
//            }
//
//            // 카카오 로그인 성공 시, PrincipalDetails를 사용하여 인증 정보를 설정
//            UserDetails userDetails = principalDetailsService.loadUserByUsername(kakaoId);
//            System.out.println(userDetails+"유저디테일");
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println(SecurityContextHolder.getContext().getAuthentication()+"권한");
//
//            if(user.getAuthority().getName().equals("ROLE_STUDENT"))
//            {
//                redirectUrl = "http://localhost:8093/home/student";
//            }
//            else if(user.getAuthority().getName().equals("ROLE_BOOKSTORE"))
//            {
//                redirectUrl = "http://localhost:8093/home/bookStore";
//            }
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Location", redirectUrl);
//
//            return new ResponseEntity<>(headers, HttpStatus.FOUND);
//
//        }
//    }
}