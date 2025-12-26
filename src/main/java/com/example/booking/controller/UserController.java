package com.example.booking.controller;

import com.example.booking.Dto.request.UserRequestDto;
import com.example.booking.Dto.response.UserResponseDto;
import com.example.booking.Enum.UserStatus;
import com.example.booking.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto dto) {
        return service.register(dto);
    }

    @PostMapping("/admin-create")
    public UserResponseDto createByAdmin(@RequestBody UserRequestDto dto, @RequestParam(required = false) Set<Long> roleIds) {
        return service.createByAdmin(dto, roleIds);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateProfile(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return service.updateProfile(id, dto);
    }

    @PatchMapping("/{id}/password")
    public void changePassword(@PathVariable Long id, @RequestParam String newPassword) {
        service.changePassword(id, newPassword);
    }

    @PatchMapping("/{id}/status")
    public UserResponseDto changeStatus(@PathVariable Long id, @RequestParam UserStatus status) {
        return service.changeStatus(id, status);
    }

    @PatchMapping("/{id}/roles")
    public UserResponseDto updateRoles(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        return service.updateRoles(id, roleIds);
    }

    @GetMapping("/{id}") public UserResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<UserResponseDto> all(){ return service.getAll(); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
