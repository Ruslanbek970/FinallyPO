package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.HotelRequestDto;
import kz.narxoz.hotelbooking.dto.response.HotelResponseDto;
import kz.narxoz.hotelbooking.mapper.HotelMapper;
import kz.narxoz.hotelbooking.model.City;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.CityRepository;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import kz.narxoz.hotelbooking.service.UserService;
import kz.narxoz.hotelbooking.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {

    private HotelRepository hotelRepository;
    private CityRepository cityRepository;
    private UserRepository userRepository;
    private UserService userService;
    private HotelMapper hotelMapper;

    private HotelServiceImpl hotelService;

    @BeforeEach
    void setUp() {
        hotelRepository = mock(HotelRepository.class);
        cityRepository = mock(CityRepository.class);
        userRepository = mock(UserRepository.class);
        userService = mock(UserService.class);
        hotelMapper = mock(HotelMapper.class);

        hotelService = new HotelServiceImpl(hotelRepository, cityRepository, userRepository, userService, hotelMapper);
    }

    @Test
    void create_shouldReturnNullIfCityNotFound() {
        HotelRequestDto dto = new HotelRequestDto();
        dto.setCityId(1L);

        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(hotelService.create(dto));
        verify(hotelRepository, never()).save(any());
    }

    @Test
    void create_shouldReturnNullIfManagerNull() {
        City city = new City();
        city.setId(1L);

        HotelRequestDto dto = new HotelRequestDto();
        dto.setCityId(1L);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(userService.getCurrentUserEntity()).thenReturn(null);

        assertNull(hotelService.create(dto));
        verify(hotelRepository, never()).save(any());
    }

    @Test
    void getMyHotels_shouldReturnEmptyIfNotAuth() {
        when(userService.getCurrentUserEntity()).thenReturn(null);

        assertTrue(hotelService.getMyHotels().isEmpty());
    }

    @Test
    void getMyHotels_shouldReturnHotels() {
        User manager = new User();
        manager.setId(1L);

        when(userService.getCurrentUserEntity()).thenReturn(manager);
        when(hotelRepository.findAllByManager(manager)).thenReturn(List.of(new Hotel()));
        when(hotelMapper.toDtoList(anyList())).thenReturn(List.of(new HotelResponseDto()));

        List<HotelResponseDto> res = hotelService.getMyHotels();

        assertEquals(1, res.size());
        verify(hotelRepository).findAllByManager(manager);
    }
}
