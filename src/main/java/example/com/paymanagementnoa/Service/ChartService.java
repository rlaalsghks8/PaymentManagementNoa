package example.com.paymanagementnoa.Service;

import example.com.paymanagementnoa.Dto.CalendarDayDto;
import example.com.paymanagementnoa.Dto.CategoryStatsDto;
import example.com.paymanagementnoa.Dto.DailyPaymentDto;
import example.com.paymanagementnoa.Dto.DashboardResponseDto;
import example.com.paymanagementnoa.Entity.Budget;
import example.com.paymanagementnoa.Entity.Payment;
import example.com.paymanagementnoa.Repository.BudgetRepository;
import example.com.paymanagementnoa.Repository.PaymentRepository;
import example.com.paymanagementnoa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChartService {
    @Autowired
    static BudgetRepository budgetRepository;
    @Autowired
    static UserRepository userRepository;
    @Autowired
    static PaymentRepository paymentRepository;

    public DashboardResponseDto getMonthlyDashboard(Long userId,
                                                    int year,
                                                    int month) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // 1️⃣ 지출 조회
        List<Payment> payments =
                paymentRepository.findByUserIdAndDateBetween(userId, start, end);

        // 2️⃣ 월 총 지출
        int totalExpense = payments.stream()
                .mapToInt(Payment::getAmount)
                .sum();

        // 3️⃣ 예산 조회
        String monthKey = year + "-" + String.format("%02d", month);

        Budget budget = budgetRepository
                .findByUserIdAndMonth(userId, monthKey)
                .orElse(null);

        Integer totalBudget = 0;
        Long budgetId = null;

        if (budget != null) {
            totalBudget = budget.getTotalBudget();
            budgetId = budget.getId();
        }

        // 4️⃣ 달력용 일별 집계
        Map<LocalDate, Integer> dailyMap = payments.stream()
                .collect(Collectors.groupingBy(
                        Payment::getDate,
                        Collectors.summingInt(Payment::getAmount)
                ));

        List<CalendarDayDto> calendar = dailyMap.entrySet()
                .stream()
                .map(e -> new CalendarDayDto(e.getKey(), e.getValue()))
                .toList();

        return DashboardResponseDto.builder()
                .budgetId(budgetId)
                .month(monthKey)
                .totalBudget(totalBudget)
                .totalExpense(totalExpense)
                .remainingBudget(totalBudget - totalExpense)
                .calendar(calendar)
                .build();
    }

    // 날짜 클릭 시 상세 조회
    public List<DailyPaymentDto> getDailyPayments(Long userId,
                                                  LocalDate date) {

        return paymentRepository.findByUserIdAndDate(userId, date)
                .stream()
                .map(p -> new DailyPaymentDto(
                        p.getId(),
                        p.getCategory(),
                        p.getAmount(),
                        p.getDescription()
                ))
                .toList();
    }

    // 월간 통계
    public List<CategoryStatsDto> getMonthlyStats(Long userId,
                                                  int year,
                                                  int month) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return getCategoryStats(userId, start, end);
    }

    // 🔹 주간 통계
    public List<CategoryStatsDto> getWeeklyStats(Long userId,
                                                 LocalDate date) {

        LocalDate start = date.with(DayOfWeek.MONDAY);
        LocalDate end = start.plusDays(6);

        return getCategoryStats(userId, start, end);
    }

    // 🔹 공통 로직
    private List<CategoryStatsDto> getCategoryStats(Long userId,
                                                    LocalDate start,
                                                    LocalDate end) {

        List<Object[]> result =
                paymentRepository.findCategoryStats(userId, start, end);

        return result.stream()
                .map(r -> CategoryStatsDto.builder()
                        .category((String) r[0])
                        .totalAmount(((Long) r[1]).intValue())
                        .build())
                .toList();
    }

}
