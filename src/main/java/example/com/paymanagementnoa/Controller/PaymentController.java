package example.com.paymanagementnoa.Controller;

import example.com.paymanagementnoa.Dto.PaymentRequestDto;
import example.com.paymanagementnoa.Entity.Payment;
import example.com.paymanagementnoa.Service.PaymentService;
import example.com.paymanagementnoa.apiPayload.ApiResponse;
import example.com.paymanagementnoa.apiPayload.status.ErrorStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(
            summary = "지출기입",
            description = "카테고리,지출양,내용 지출기입 해주세요."
    )
    public ApiResponse<?> createPayment(@RequestBody PaymentRequestDto request,
                                        HttpSession session) {

        // 1️⃣ 로그인 확인
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure(
                    ErrorStatus._UNAUTHORIZED.getCode(),
                    "로그인이 필요합니다.",
                    null
            );
        }

        // 2️⃣ 지출 등록
        Payment payment = paymentService.createPayment(userId, request);

        return ApiResponse.onSuccess(payment);
    }


    @PutMapping("/{id}")
    public ApiResponse<?> updatePayment(@PathVariable Long id,
                                        @RequestBody PaymentRequestDto request,
                                        HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure("401", "로그인이 필요합니다.", null);
        }

        paymentService.updatePayment(userId, id, request);

        return ApiResponse.onSuccess("수정 완료");
    }


    @DeleteMapping("/{id}")
    public ApiResponse<?> deletePayment(@PathVariable Long id,
                                        HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure("401", "로그인이 필요합니다.", null);
        }

        paymentService.deletePayment(userId, id);

        return ApiResponse.onSuccess("삭제 완료");
    }
}
