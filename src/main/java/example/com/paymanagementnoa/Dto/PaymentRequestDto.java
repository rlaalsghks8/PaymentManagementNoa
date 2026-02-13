package example.com.paymanagementnoa.Dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    private LocalDate date;
    private String category;
    private Integer amount;
    private String description;
}
