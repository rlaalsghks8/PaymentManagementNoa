package example.com.paymanagementnoa.Controller;

import example.com.paymanagementnoa.Dto.BudgetRequestDto;
import example.com.paymanagementnoa.Dto.BudgetUpdateDto;
import example.com.paymanagementnoa.Service.BudgetService;
import example.com.paymanagementnoa.apiPayload.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ApiResponse<?> createOrUpdateBudget(
            @RequestBody BudgetRequestDto request,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure("401", "로그인이 필요합니다.", null);
        }

        budgetService.saveOrUpdateBudget(userId, request);

        return ApiResponse.onSuccess("예산 저장 완료");
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateBudget(@PathVariable Long id,
                                       @RequestBody BudgetUpdateDto request,
                                       HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return ApiResponse.onFailure("401", "로그인이 필요합니다.", null);
        }

        budgetService.updateBudget(userId, id, request);

        return ApiResponse.onSuccess("예산 수정 완료");
    }


}
