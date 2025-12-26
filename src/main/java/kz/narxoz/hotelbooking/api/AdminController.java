package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.AdminCreateUserRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody AdminCreateUserRequestDto dto) {
        return ResponseEntity.ok(adminService.createUser(dto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PutMapping("/users/{id}/block")
    public ResponseEntity<Boolean> blockUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.blockUser(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteUser(id));
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<Boolean> setRole(@PathVariable Long id, @RequestParam String roleName) {
        return ResponseEntity.ok(adminService.setRole(id, roleName));
    }
}
