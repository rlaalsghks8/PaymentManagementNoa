package example.com.paymanagementnoa.Repository;

import example.com.paymanagementnoa.Entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
    Optional<Budget> findByUserIdAndMonth(Long userId, String month);
}
