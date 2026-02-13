package example.com.paymanagementnoa.Repository;

import example.com.paymanagementnoa.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByUserIdAndDateBetween(Long userId,
                                             LocalDate start,
                                             LocalDate end);

    List<Payment> findByUserIdAndDate(Long userId, LocalDate date);

    @Query("""
        SELECT p.category, SUM(p.amount)
        FROM Payment p
        WHERE p.user.id = :userId
        AND p.date BETWEEN :start AND :end
        GROUP BY p.category
    """)
    List<Object[]> findCategoryStats(Long userId,
                                     LocalDate start,
                                     LocalDate end);
}
