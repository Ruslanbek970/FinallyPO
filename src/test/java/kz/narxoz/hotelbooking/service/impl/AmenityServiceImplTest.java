package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.AmenityRequestDto;
import kz.narxoz.hotelbooking.dto.response.AmenityResponseDto;
import kz.narxoz.hotelbooking.mapper.AmenityMapper;
import kz.narxoz.hotelbooking.model.Amenity;
import kz.narxoz.hotelbooking.repository.AmenityRepository;
import kz.narxoz.hotelbooking.service.impl.AmenityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmenityServiceImplTest {

    private AmenityRepository amenityRepository;
    private AmenityMapper amenityMapper;
    private AmenityServiceImpl amenityService;

    @BeforeEach
    void setUp() {
        amenityRepository = mock(AmenityRepository.class);
        amenityMapper = mock(AmenityMapper.class);
        amenityService = new AmenityServiceImpl(amenityRepository, amenityMapper);
    }

    @Test
    void create_shouldReturnDto() {
        AmenityRequestDto req = new AmenityRequestDto();
        req.setName("Wi-Fi");

        Amenity entity = new Amenity();
        Amenity saved = new Amenity();
        saved.setId(1L);

        AmenityResponseDto dto = new AmenityResponseDto();
        dto.setId(1L);

        when(amenityMapper.toEntity(req)).thenReturn(entity);
        when(amenityRepository.save(entity)).thenReturn(saved);
        when(amenityMapper.toDto(saved)).thenReturn(dto);

        AmenityResponseDto res = amenityService.create(req);

        assertNotNull(res);
        assertEquals(1L, res.getId());
        verify(amenityRepository).save(entity);
    }

    @Test
    void update_shouldReturnNull_ifNotFound() {
        when(amenityRepository.findById(99L)).thenReturn(Optional.empty());

        AmenityRequestDto req = new AmenityRequestDto();
        req.setName("Pool");

        assertNull(amenityService.update(99L, req));
    }

    @Test
    void delete_shouldReturnFalse_ifNotFound() {
        when(amenityRepository.findById(99L)).thenReturn(Optional.empty());

        assertFalse(amenityService.delete(99L));
        verify(amenityRepository, never()).deleteById(anyLong());
    }
}
