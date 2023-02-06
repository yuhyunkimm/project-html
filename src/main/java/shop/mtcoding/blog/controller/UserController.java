package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {
        // System.out.println(joinReqDto.getUsername());
        // System.out.println(joinReqDto.getPassword());
        // System.out.println(joinReqDto.getEmail());

        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");

        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password 작성해주세요");

        }
        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("email 작성해주세요");

        }
        int result = userService.회원가입(joinReqDto);
        if (result != 1) {
            throw new CustomException("회원가입실패");

        }
        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto) {
        // controlloer 책임 확인
        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password 작성해주세요");
        }
        User principal = userService.로그인(loginReqDto);
        session.setAttribute("principal", principal);
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}
