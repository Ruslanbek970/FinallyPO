package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.UnitRequestDto;
import com.example.booking.Dto.response.AmenityResponseDto;
import com.example.booking.Dto.response.UnitResponseDto;
import com.example.booking.Entity.Amenity;
import com.example.booking.Entity.Property;
import com.example.booking.Entity.Unit;
import com.example.booking.Mapper.UnitMapper;
import com.example.booking.Service.UnitService;
import com.example.booking.repository.AmenityRepository;
import com.example.booking.repository.PropertyRepository;
import com.example.booking.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final PropertyRepository propertyRepository;
    private final AmenityRepository amenityRepository;
    private final UnitMapper unitMapper;

    @Override
    public UnitResponseDto create(UnitRequestDto dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        Unit unit = unitMapper.toEntity(dto);
        unit.setProperty(property);

        if (dto.getAmenities() != null) {
            Set<Long> ids = dto.getAmenities().stream()
                    .map(AmenityResponseDto::getId)
                    .collect(Collectors.toSet());
            Set<Amenity> amenities = new HashSet<>(amenityRepository.findAllById(ids));
            unit.setAmenities(amenities);
        }

        return unitMapper.toDto(unitRepository.save(unit));
    }

    @Override
    public UnitResponseDto update(Long id, UnitRequestDto dto) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        unit.setProperty(property);
        unit.setUnitType(dto.getUnitType());
        unit.setNameOrNumber(dto.getNameOrNumber());
        unit.setCapacityAdults(dto.getCapacityAdults());
        unit.setCapacityChildren(dto.getCapacityChildren());
        unit.setBedsCount(dto.getBedsCount());
        unit.setBaseNightPrice(dto.getBaseNightPrice());
        unit.setActive(dto.isActive());

        if (dto.getAmenities() != null) {
            Set<Long> ids = dto.getAmenities().stream()
                    .map(AmenityResponseDto::getId)
                    .collect(Collectors.toSet());
            unit.setAmenities(new HashSet<>(amenityRepository.findAllById(ids)));
        }

        return unitMapper.toDto(unitRepository.save(unit));
    }

    @Override
    public UnitResponseDto updateAmenities(Long unitId, Set<Long> amenityIds) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));

        Set<Amenity> amenities = new HashSet<>(amenityRepository.findAllById(amenityIds));
        unit.setAmenities(amenities);

        return unitMapper.toDto(unitRepository.save(unit));
    }

    @Override
    public UnitResponseDto getById(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));
        return unitMapper.toDto(unit);
    }

    @Override
    public List<UnitResponseDto> getAll() {
        return unitMapper.toDtoList(unitRepository.findAll());
    }

    @Override
    public List<UnitResponseDto> getByPropertyId(Long propertyId) {
        return unitMapper.toDtoList(unitRepository.findByPropertyId(propertyId));
    }

    @Override
    public void deleteById(Long id) {
        if (!unitRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found");
        }
        unitRepository.deleteById(id);
    }
}
