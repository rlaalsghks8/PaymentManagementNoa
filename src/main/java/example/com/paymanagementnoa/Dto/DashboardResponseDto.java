package example.com.paymanagementnoa.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DashboardResponseDto {

    private Long budgetId;
    private String month;
    private Integer totalBudget;
    private Integer totalExpense;
    private Integer remainingBudget;
    private List<CalendarDayDto> calendar;
}
