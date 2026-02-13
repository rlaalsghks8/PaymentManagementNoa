package example.com.paymanagementnoa.Dto;

import lombok.Data;

@Data
public class BudgetRequestDto {

    private String month;      // 예: 2026-02
    private Integer totalBudget;
}
