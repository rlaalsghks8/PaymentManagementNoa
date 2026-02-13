package example.com.paymanagementnoa.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CalendarDayDto {

    private LocalDate date;
    private Integer totalAmount;
}