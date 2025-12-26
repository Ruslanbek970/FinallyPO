package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.CityRequestDto;
import kz.narxoz.hotelbooking.dto.response.CityResponseDto;
import kz.narxoz.hotelbooking.mapper.CityMapper;
import kz.narxoz.hotelbooking.model.City;
import kz.narxoz.hotelbooking.repository.CityRepository;
import kz.narxoz.hotelbooking.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public List<CityResponseDto> getAll() {
        return cityMapper.toDtoList(cityRepository.findAll());
    }

    @Override
    public CityResponseDto getById(Long id) {
        City city = cityRepository.findById(id).orElse(null);
        if (Objects.isNull(city)) return null;
        return cityMapper.toDto(city);
    }

    @Override
    public CityResponseDto create(CityRequestDto dto) {
        City city = cityMapper.toEntity(dto);
        City saved = cityRepository.save(city);
        return cityMapper.toDto(saved);
    }

    @Override
    public CityResponseDto update(Long id, CityRequestDto dto) {
        City city = cityRepository.findById(id).orElse(null);
        if (Objects.isNull(city)) return null;

        city.setName(dto.getName());
        city.setCountry(dto.getCountry());
        City saved = cityRepository.save(city);
        return cityMapper.toDto(saved);
    }

    @Override
    public boolean delete(Long id) {
        City city = cityRepository.findById(id).orElse(null);
        if (Objects.isNull(city)) return false;
        cityRepository.deleteById(id);
        return true;
    }
}
