package example.com.paymanagementnoa.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryStatsDto {

    private String category;
    private Integer totalAmount;
}
