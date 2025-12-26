package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.CityRequestDto;
import kz.narxoz.hotelbooking.dto.response.CityResponseDto;
import kz.narxoz.hotelbooking.mapper.CityMapper;
import kz.narxoz.hotelbooking.model.City;
import kz.narxoz.hotelbooking.repository.CityRepository;
import kz.narxoz.hotelbooking.service.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceImplTest {

    private CityRepository cityRepository;
    private CityMapper cityMapper;
    private CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        cityRepository = mock(CityRepository.class);
        cityMapper = mock(CityMapper.class);
        cityService = new CityServiceImpl(cityRepository, cityMapper);
    }

    @Test
    void getAll_shouldReturnList() {
        City city = new City();
        CityResponseDto dto = new CityResponseDto();

        when(cityRepository.findAll()).thenReturn(List.of(city));
        when(cityMapper.toDtoList(List.of(city))).thenReturn(List.of(dto));

        List<CityResponseDto> res = cityService.getAll();

        assertEquals(1, res.size());
        verify(cityRepository).findAll();
    }

    @Test
    void getById_shouldReturnNullIfNotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(cityService.getById(1L));
    }

    @Test
    void delete_shouldReturnFalseIfNotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(cityService.delete(1L));
        verify(cityRepository, never()).deleteById(anyLong());
    }
}
