package example.com.paymanagementnoa.Service;

import example.com.paymanagementnoa.Dto.BudgetRequestDto;
import example.com.paymanagementnoa.Dto.BudgetUpdateDto;
import example.com.paymanagementnoa.Entity.Budget;
import example.com.paymanagementnoa.Entity.User;
import example.com.paymanagementnoa.Repository.BudgetRepository;
import example.com.paymanagementnoa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;


    public void saveOrUpdateBudget(Long userId,
                                   BudgetRequestDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        // 이미 해당 월 예산이 있는지 확인
        Budget budget = budgetRepository
                .findByUserIdAndMonth(userId, request.getMonth())
                .orElse(null);

        if (budget != null) {

            budget.setTotalBudget(request.getTotalBudget());
        } else {

            Budget newBudget = Budget.builder()
                    .month(request.getMonth())
                    .totalBudget(request.getTotalBudget())
                    .user(user)
                    .build();

            budgetRepository.save(newBudget);
        }
    }


    public void updateBudget(Long userId,
                             Long budgetId,
                             BudgetUpdateDto request) {

        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("예산을 찾을 수 없습니다."));

        // 🔥 본인 예산인지 체크
        if (!budget.getUser().getId().equals(userId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        budget.setTotalBudget(request.getTotalBudget());
    }




}