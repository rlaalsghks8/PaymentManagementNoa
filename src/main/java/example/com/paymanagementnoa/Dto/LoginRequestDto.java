package example.com.paymanagementnoa.Dto;

import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequestDto {
    private String userId;
    private String password;
}
