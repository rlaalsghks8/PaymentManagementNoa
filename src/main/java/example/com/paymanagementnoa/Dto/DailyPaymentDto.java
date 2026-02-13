package example.com.paymanagementnoa.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DailyPaymentDto {

    private Long id;
    private String category;
    private Integer amount;
    private String description;
}
