package example.com.paymanagementnoa.Service;


import example.com.paymanagementnoa.Dto.LoginRequestDto;
import example.com.paymanagementnoa.Dto.RegisterRequestDto;
import example.com.paymanagementnoa.Entity.User;
import example.com.paymanagementnoa.Repository.UserRepository;
import example.com.paymanagementnoa.apiPayload.ApiResponse;
import example.com.paymanagementnoa.apiPayload.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    static UserRepository userRepository;

    public ApiResponse<?> register(RegisterRequestDto request) {

        //userId 중복 체크
        if (userRepository.existsByUserId(request.getUserId())) {
            return ApiResponse.onFailure(
                    ErrorStatus._BAD_REQUEST.getCode(),
                    "이미 존재하는 userId 입니다.",
                    null
            );
        }

        //회원 저장
        User user = User.builder()
                .userId(request.getUserId())
                .password(request.getPassword()) // ⚠ 실제로는 암호화 필요
                .email(request.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        // 3️⃣ 성공 응답
        return ApiResponse.onSuccess("회원가입이 완료되었습니다.");
    }

    public User login(LoginRequestDto request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElse(null);

        if (user == null) return null;

        if (!user.getPassword().equals(request.getPassword())) return null;

        return user;

    }
}
