package com.example.booking.Service;

import com.example.booking.Dto.request.RoleRequestDto;
import com.example.booking.Dto.response.RoleResponseDto;

import java.util.List;

public interface RoleService {

    RoleResponseDto create(RoleRequestDto dto);

    RoleResponseDto update(Long id, RoleRequestDto dto);

    RoleResponseDto getById(Long id);

    RoleResponseDto getByName(String name); // ADMIN/USER/MANAGER

    List<RoleResponseDto> getAll();

    void deleteById(Long id);
}
