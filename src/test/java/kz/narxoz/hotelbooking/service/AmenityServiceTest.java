package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.AmenityRequestDto;
import kz.narxoz.hotelbooking.dto.response.AmenityResponseDto;
import kz.narxoz.hotelbooking.mapper.AmenityMapper;
import kz.narxoz.hotelbooking.model.Amenity;
import kz.narxoz.hotelbooking.repository.AmenityRepository;
import kz.narxoz.hotelbooking.service.impl.AmenityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AmenityServiceTest {

    @Test
    void getAll() {
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        AmenityMapper amenityMapper = mock(AmenityMapper.class);

        AmenityService amenityService = new AmenityServiceImpl(amenityRepository, amenityMapper);

        Amenity a1 = new Amenity();
        Amenity a2 = new Amenity();

        List<Amenity> list = List.of(a1, a2);
        when(amenityRepository.findAll()).thenReturn(list);

        List<AmenityResponseDto> dtoList = List.of(new AmenityResponseDto(), new AmenityResponseDto());
        when(amenityMapper.toDtoList(list)).thenReturn(dtoList);

        List<AmenityResponseDto> result = amenityService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        verify(amenityRepository, times(1)).findAll();
        verify(amenityMapper, times(1)).toDtoList(list);
    }

    @Test
    void create() {
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        AmenityMapper amenityMapper = mock(AmenityMapper.class);

        AmenityService amenityService = new AmenityServiceImpl(amenityRepository, amenityMapper);

        AmenityRequestDto dto = new AmenityRequestDto();
        dto.setName("WiFi");

        Amenity entity = new Amenity();
        entity.setName("WiFi");

        Amenity saved = new Amenity();
        saved.setId(1L);
        saved.setName("WiFi");

        AmenityResponseDto response = new AmenityResponseDto();
        response.setId(1L);
        response.setName("WiFi");

        when(amenityMapper.toEntity(dto)).thenReturn(entity);
        when(amenityRepository.save(entity)).thenReturn(saved);
        when(amenityMapper.toDto(saved)).thenReturn(response);

        AmenityResponseDto result = amenityService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("WiFi", result.getName());

        verify(amenityMapper, times(1)).toEntity(dto);
        verify(amenityRepository, times(1)).save(entity);
        verify(amenityMapper, times(1)).toDto(saved);
    }

    @Test
    void updateWhenNotFound() {
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        AmenityMapper amenityMapper = mock(AmenityMapper.class);

        AmenityService amenityService = new AmenityServiceImpl(amenityRepository, amenityMapper);

        AmenityRequestDto dto = new AmenityRequestDto();
        dto.setName("WiFi");

        when(amenityRepository.findById(1L)).thenReturn(Optional.empty());

        AmenityResponseDto result = amenityService.update(1L, dto);

        Assertions.assertNull(result);

        verify(amenityRepository, times(1)).findById(1L);
        verify(amenityRepository, never()).save(any());
    }

    @Test
    void deleteWhenNotFound() {
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        AmenityMapper amenityMapper = mock(AmenityMapper.class);

        AmenityService amenityService = new AmenityServiceImpl(amenityRepository, amenityMapper);

        when(amenityRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = amenityService.delete(1L);

        Assertions.assertFalse(result);

        verify(amenityRepository, times(1)).findById(1L);
        verify(amenityRepository, never()).deleteById(anyLong());
    }
}
