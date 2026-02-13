package example.com.paymanagementnoa.Controller;

import example.com.paymanagementnoa.Service.ChartService;
import example.com.paymanagementnoa.apiPayload.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expense")
public class ChartController {


    private final ChartService chartService;

    @GetMapping
    public ApiResponse<?> getDashboard(@RequestParam int year,
                                       @RequestParam int month,
                                       HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure("401", "로그인이 필요합니다.", null);
        }

        return ApiResponse.onSuccess(
                chartService.getMonthlyDashboard(userId, year, month)
        );
    }

    // 특정 날짜 상세 조회
    @GetMapping("/{date}")
    public ApiResponse<?> getDailyDetail(@PathVariable String date,
                                         HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure("401", "로그인이 필요합니다.", null);
        }

        return ApiResponse.onSuccess(
                chartService.getDailyPayments(userId, LocalDate.parse(date))
        );
    }

    // 🔹 월간 통계
    @GetMapping("/monthly")
    public ApiResponse<?> getMonthlyStats(@RequestParam int year,
                                          @RequestParam int month,
                                          HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure("401", "로그인이 필요합니다.", null);
        }

        return ApiResponse.onSuccess(
                chartService.getMonthlyStats(userId, year, month)
        );
    }

    // 🔹 주간 통계
    @GetMapping("/weekly")
    public ApiResponse<?> getWeeklyStats(@RequestParam String date,
                                         HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure("401", "로그인이 필요합니다.", null);
        }

        return ApiResponse.onSuccess(
                chartService.getWeeklyStats(userId, LocalDate.parse(date))
        );
    }


}
