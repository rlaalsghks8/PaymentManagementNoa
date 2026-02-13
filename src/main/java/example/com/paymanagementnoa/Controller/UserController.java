package example.com.paymanagementnoa.Controller;


import example.com.paymanagementnoa.Dto.LoginRequestDto;
import example.com.paymanagementnoa.Dto.RegisterRequestDto;
import example.com.paymanagementnoa.Entity.User;
import example.com.paymanagementnoa.Service.UserService;
import example.com.paymanagementnoa.apiPayload.ApiResponse;
import example.com.paymanagementnoa.apiPayload.status.ErrorStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequestDto request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequestDto request,
                                HttpSession session) {

        User user = userService.login(request);

        if (user == null) {
            return ApiResponse.onFailure(
                    ErrorStatus._UNAUTHORIZED.getCode(),
                    "아이디 또는 비밀번호가 올바르지 않습니다.",
                    null
            );
        }

        session.setAttribute("LOGIN_USER", user.getId());

        return ApiResponse.onSuccess("로그인 성공");
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpSession session) {

        if (session.getAttribute("LOGIN_USER") == null) {
            return ApiResponse.onFailure(
                    ErrorStatus._UNAUTHORIZED.getCode(),
                    "이미 로그아웃 상태입니다.",
                    null
            );
        }

        session.invalidate();
        return ApiResponse.onSuccess("로그아웃 성공");
    }
}

