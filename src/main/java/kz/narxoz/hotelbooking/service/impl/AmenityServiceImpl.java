package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.AmenityRequestDto;
import kz.narxoz.hotelbooking.dto.response.AmenityResponseDto;
import kz.narxoz.hotelbooking.mapper.AmenityMapper;
import kz.narxoz.hotelbooking.model.Amenity;
import kz.narxoz.hotelbooking.repository.AmenityRepository;
import kz.narxoz.hotelbooking.service.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;

    @Override
    public List<AmenityResponseDto> getAll() {
        return amenityMapper.toDtoList(amenityRepository.findAll());
    }

    @Override
    public AmenityResponseDto create(AmenityRequestDto dto) {
        Amenity a = amenityMapper.toEntity(dto);
        Amenity saved = amenityRepository.save(a);
        return amenityMapper.toDto(saved);
    }

    @Override
    public AmenityResponseDto update(Long id, AmenityRequestDto dto) {
        Amenity a = amenityRepository.findById(id).orElse(null);
        if (Objects.isNull(a)) return null;
        a.setName(dto.getName());
        Amenity saved = amenityRepository.save(a);
        return amenityMapper.toDto(saved);
    }

    @Override
    public boolean delete(Long id) {
        Amenity a = amenityRepository.findById(id).orElse(null);
        if (Objects.isNull(a)) return false;
        amenityRepository.deleteById(id);
        return true;
    }
}
