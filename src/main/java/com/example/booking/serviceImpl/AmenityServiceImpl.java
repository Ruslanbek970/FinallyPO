package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.AmenityRequestDto;
import com.example.booking.Dto.response.AmenityResponseDto;
import com.example.booking.Entity.Amenity;
import com.example.booking.Mapper.AmenityMapper;
import com.example.booking.Service.AmenityService;
import com.example.booking.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;

    @Override
    public AmenityResponseDto create(AmenityRequestDto dto) {
        Amenity entity = amenityMapper.toEntity(dto);
        return amenityMapper.toDto(amenityRepository.save(entity));
    }

    @Override
    public AmenityResponseDto update(Long id, AmenityRequestDto dto) {
        Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Amenity not found"));

        amenity.setCode(dto.getCode());
        amenity.setTitle(dto.getTitle());

        return amenityMapper.toDto(amenityRepository.save(amenity));
    }

    @Override
    public AmenityResponseDto getById(Long id) {
        Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Amenity not found"));
        return amenityMapper.toDto(amenity);
    }

    @Override
    public List<AmenityResponseDto> getAll() {
        return amenityMapper.toDtoList(amenityRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (!amenityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Amenity not found");
        }
        amenityRepository.deleteById(id);
    }
}
