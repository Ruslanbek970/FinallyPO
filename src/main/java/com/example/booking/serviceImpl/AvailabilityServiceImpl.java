package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.AvailabilityRequestDto;
import com.example.booking.Dto.response.AvailabilityResponseDto;
import com.example.booking.Entity.Availability;
import com.example.booking.Entity.Unit;
import com.example.booking.Mapper.AvailabilityMapper;
import com.example.booking.Service.AvailabilityService;
import com.example.booking.repository.AvailabilityRepository;
import com.example.booking.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final UnitRepository unitRepository;
    private final AvailabilityMapper availabilityMapper;

    @Override
    public AvailabilityResponseDto create(AvailabilityRequestDto dto) {
        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));

        Availability availability = availabilityMapper.toEntity(dto);
        availability.setUnit(unit);

        return availabilityMapper.toDto(availabilityRepository.save(availability));
    }

    @Override
    public AvailabilityResponseDto update(Long id, AvailabilityRequestDto dto) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Availability not found"));

        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));

        availability.setUnit(unit);
        availability.setDate(dto.getDate());
        availability.setAvailableCount(dto.getAvailableCount());
        availability.setOverridePrice(dto.getOverridePrice());
        availability.setStopSell(dto.isStopSell());

        return availabilityMapper.toDto(availabilityRepository.save(availability));
    }

    @Override
    public AvailabilityResponseDto getById(Long id) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Availability not found"));
        return availabilityMapper.toDto(availability);
    }

    @Override
    public List<AvailabilityResponseDto> getAll() {
        return availabilityMapper.toDtoList(availabilityRepository.findAll());
    }

    @Override
    public List<AvailabilityResponseDto> getByUnitAndDates(Long unitId, LocalDate from, LocalDate to) {
        return availabilityMapper.toDtoList(
                availabilityRepository.findByUnitIdAndDateBetween(unitId, from, to)
        );
    }

    @Override
    public void deleteById(Long id) {
        if (!availabilityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Availability not found");
        }
        availabilityRepository.deleteById(id);
    }
}
