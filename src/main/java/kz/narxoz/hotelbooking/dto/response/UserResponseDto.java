package kz.narxoz.hotelbooking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private boolean active;
    private LocalDateTime createdAt;
    private List<String> roles;
}
