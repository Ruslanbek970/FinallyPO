package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.RoleRequestDto;
import com.example.booking.Dto.response.RoleResponseDto;
import com.example.booking.Entity.Role;
import com.example.booking.Mapper.RoleMapper;
import com.example.booking.Service.RoleService;
import com.example.booking.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponseDto create(RoleRequestDto dto) {
        Role role = roleMapper.toEntity(dto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public RoleResponseDto update(Long id, RoleRequestDto dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        role.setName(dto.getName());
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public RoleResponseDto getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        return roleMapper.toDto(role);
    }

    @Override
    public RoleResponseDto getByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleResponseDto> getAll() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }
        roleRepository.deleteById(id);
    }
}
