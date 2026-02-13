package example.com.paymanagementnoa.Dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RegisterRequestDto {
    private String userId;
    private String password;
    private String email;
}
