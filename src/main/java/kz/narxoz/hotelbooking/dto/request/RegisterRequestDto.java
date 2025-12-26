package kz.narxoz.hotelbooking.dto.request;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String password;
    private String fullName;
    private String phone;
}
