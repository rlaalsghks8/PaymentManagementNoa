package example.com.paymanagementnoa.Service;

import example.com.paymanagementnoa.Dto.PaymentRequestDto;
import example.com.paymanagementnoa.Entity.Payment;
import example.com.paymanagementnoa.Entity.User;
import example.com.paymanagementnoa.Repository.PaymentRepository;
import example.com.paymanagementnoa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    static UserRepository userRepository;
    @Autowired
    static PaymentRepository paymentRepository;

    public Payment createPayment(Long userId, PaymentRequestDto request) {

        // 1️⃣ 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 2️⃣ Payment 생성
        Payment payment = Payment.builder()
                .date(request.getDate())
                .category(request.getCategory())
                .amount(request.getAmount())
                .description(request.getDescription())
                .user(user)   // 🔥 여기서 연관관계 설정
                .build();

        return paymentRepository.save(payment);
    }

    public void updatePayment(Long userId,
                              Long paymentId,
                              PaymentRequestDto request) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("지출 없음"));

        if (!payment.getUser().getId().equals(userId)) {
            throw new RuntimeException("권한 없음");
        }

        payment.setDate(request.getDate());
        payment.setCategory(request.getCategory());
        payment.setAmount(request.getAmount());
        payment.setDescription(request.getDescription());
    }

    public void deletePayment(Long userId, Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("지출 없음"));

        if (!payment.getUser().getId().equals(userId)) {
            throw new RuntimeException("권한 없음");
        }

        paymentRepository.delete(payment);
    }



}
