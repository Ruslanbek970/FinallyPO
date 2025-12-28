package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.CityRequestDto;
import kz.narxoz.hotelbooking.dto.response.CityResponseDto;
import kz.narxoz.hotelbooking.mapper.CityMapper;
import kz.narxoz.hotelbooking.model.City;
import kz.narxoz.hotelbooking.repository.CityRepository;
import kz.narxoz.hotelbooking.service.impl.CityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CityServiceTest {

    @Test
    void getAll() {
        CityRepository cityRepository = mock(CityRepository.class);
        CityMapper cityMapper = mock(CityMapper.class);

        CityService cityService = new CityServiceImpl(cityRepository, cityMapper);

        City c1 = new City();
        City c2 = new City();

        List<City> cities = List.of(c1, c2);
        when(cityRepository.findAll()).thenReturn(cities);

        List<CityResponseDto> dtoList = List.of(new CityResponseDto(), new CityResponseDto());
        when(cityMapper.toDtoList(cities)).thenReturn(dtoList);

        List<CityResponseDto> result = cityService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        verify(cityRepository, times(1)).findAll();
        verify(cityMapper, times(1)).toDtoList(cities);
    }

    @Test
    void getByIdWhenNotFound() {
        CityRepository cityRepository = mock(CityRepository.class);
        CityMapper cityMapper = mock(CityMapper.class);

        CityService cityService = new CityServiceImpl(cityRepository, cityMapper);

        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        CityResponseDto result = cityService.getById(1L);

        Assertions.assertNull(result);

        verify(cityRepository, times(1)).findById(1L);
        verify(cityMapper, never()).toDto(any());
    }

    @Test
    void create() {
        CityRepository cityRepository = mock(CityRepository.class);
        CityMapper cityMapper = mock(CityMapper.class);

        CityService cityService = new CityServiceImpl(cityRepository, cityMapper);

        CityRequestDto dto = new CityRequestDto();
        dto.setName("Almaty");
        dto.setCountry("KZ");

        City entity = new City();
        entity.setName("Almaty");
        entity.setCountry("KZ");

        City saved = new City();
        saved.setId(1L);
        saved.setName("Almaty");
        saved.setCountry("KZ");

        CityResponseDto response = new CityResponseDto();
        response.setId(1L);
        response.setName("Almaty");
        response.setCountry("KZ");

        when(cityMapper.toEntity(dto)).thenReturn(entity);
        when(cityRepository.save(entity)).thenReturn(saved);
        when(cityMapper.toDto(saved)).thenReturn(response);

        CityResponseDto result = cityService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Almaty", result.getName());
        Assertions.assertEquals("KZ", result.getCountry());

        verify(cityMapper, times(1)).toEntity(dto);
        verify(cityRepository, times(1)).save(entity);
        verify(cityMapper, times(1)).toDto(saved);
    }

    @Test
    void deleteWhenNotFound() {
        CityRepository cityRepository = mock(CityRepository.class);
        CityMapper cityMapper = mock(CityMapper.class);

        CityService cityService = new CityServiceImpl(cityRepository, cityMapper);

        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = cityService.delete(1L);

        Assertions.assertFalse(result);

        verify(cityRepository, times(1)).findById(1L);
        verify(cityRepository, never()).deleteById(anyLong());
    }
}
