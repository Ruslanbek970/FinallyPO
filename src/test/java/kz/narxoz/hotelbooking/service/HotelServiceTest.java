package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.HotelRequestDto;
import kz.narxoz.hotelbooking.dto.response.HotelResponseDto;
import kz.narxoz.hotelbooking.mapper.HotelMapper;
import kz.narxoz.hotelbooking.model.City;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.CityRepository;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import kz.narxoz.hotelbooking.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class HotelServiceTest {

    @Test
    void createSuccessWithManagerId() {
        HotelRepository hotelRepository = mock(HotelRepository.class);
        CityRepository cityRepository = mock(CityRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = mock(UserService.class);
        HotelMapper hotelMapper = mock(HotelMapper.class);

        HotelService hotelService = new HotelServiceImpl(hotelRepository, cityRepository, userRepository, userService, hotelMapper);

        HotelRequestDto dto = new HotelRequestDto();
        dto.setName("Hotel A");
        dto.setAddress("Address");
        dto.setDescription("Desc");
        dto.setCityId(1L);
        dto.setManagerId(2L);
        dto.setActive(true);

        City city = new City();
        city.setId(1L);

        User manager = new User();
        manager.setId(2L);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(userRepository.findById(2L)).thenReturn(Optional.of(manager));

        Hotel saved = new Hotel();
        saved.setId(10L);

        when(hotelRepository.save(any(Hotel.class))).thenReturn(saved);

        HotelResponseDto response = new HotelResponseDto();
        response.setId(10L);
        response.setName("Hotel A");

        when(hotelMapper.toDto(saved)).thenReturn(response);

        HotelResponseDto result = hotelService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10L, result.getId());

        verify(cityRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
        verify(hotelMapper, times(1)).toDto(saved);
    }

    @Test
    void createWhenCityNotFound() {
        HotelRepository hotelRepository = mock(HotelRepository.class);
        CityRepository cityRepository = mock(CityRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = mock(UserService.class);
        HotelMapper hotelMapper = mock(HotelMapper.class);

        HotelService hotelService = new HotelServiceImpl(hotelRepository, cityRepository, userRepository, userService, hotelMapper);

        HotelRequestDto dto = new HotelRequestDto();
        dto.setCityId(1L);

        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        HotelResponseDto result = hotelService.create(dto);

        Assertions.assertNull(result);

        verify(cityRepository, times(1)).findById(1L);
        verify(hotelRepository, never()).save(any());
    }

    @Test
    void getMyHotelsWhenManagerNull() {
        HotelRepository hotelRepository = mock(HotelRepository.class);
        CityRepository cityRepository = mock(CityRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = mock(UserService.class);
        HotelMapper hotelMapper = mock(HotelMapper.class);

        HotelService hotelService = new HotelServiceImpl(hotelRepository, cityRepository, userRepository, userService, hotelMapper);

        when(userService.getCurrentUserEntity()).thenReturn(null);

        List<HotelResponseDto> result = hotelService.getMyHotels();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        verify(userService, times(1)).getCurrentUserEntity();
        verify(hotelRepository, never()).findAllByManager(any());
    }

    @Test
    void deleteWhenNotFound() {
        HotelRepository hotelRepository = mock(HotelRepository.class);
        CityRepository cityRepository = mock(CityRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = mock(UserService.class);
        HotelMapper hotelMapper = mock(HotelMapper.class);

        HotelService hotelService = new HotelServiceImpl(hotelRepository, cityRepository, userRepository, userService, hotelMapper);

        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = hotelService.delete(1L);

        Assertions.assertFalse(result);

        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelRepository, never()).deleteById(anyLong());
    }
}
