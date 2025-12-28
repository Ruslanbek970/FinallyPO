package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.RoomRequestDto;
import kz.narxoz.hotelbooking.dto.response.RoomResponseDto;
import kz.narxoz.hotelbooking.mapper.RoomMapper;
import kz.narxoz.hotelbooking.model.Amenity;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Room;
import kz.narxoz.hotelbooking.repository.AmenityRepository;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.RoomRepository;
import kz.narxoz.hotelbooking.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class RoomServiceTest {

    @Test
    void createSuccess() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        RoomMapper roomMapper = mock(RoomMapper.class);

        RoomService roomService = new RoomServiceImpl(roomRepository, hotelRepository, amenityRepository, roomMapper);

        RoomRequestDto dto = new RoomRequestDto();
        dto.setRoomNumber("101");
        dto.setType("STANDARD");
        dto.setPricePerNight(100);
        dto.setCapacity(2);
        dto.setHotelId(10L);
        dto.setAmenityIds(List.of(1L, 2L));

        Hotel hotel = new Hotel();
        hotel.setId(10L);

        Amenity a1 = new Amenity();
        a1.setId(1L);
        Amenity a2 = new Amenity();
        a2.setId(2L);

        when(hotelRepository.findById(10L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findAllById(dto.getAmenityIds())).thenReturn(List.of(a1, a2));

        Room saved = new Room();
        saved.setId(1L);
        saved.setRoomNumber("101");
        saved.setHotel(hotel);

        when(roomRepository.save(any(Room.class))).thenReturn(saved);

        RoomResponseDto response = new RoomResponseDto();
        response.setId(1L);
        response.setRoomNumber("101");

        when(roomMapper.toDto(saved)).thenReturn(response);

        RoomResponseDto result = roomService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("101", result.getRoomNumber());

        verify(hotelRepository, times(1)).findById(10L);
        verify(amenityRepository, times(1)).findAllById(dto.getAmenityIds());
        verify(roomRepository, times(1)).save(any(Room.class));
        verify(roomMapper, times(1)).toDto(saved);
    }

    @Test
    void createWhenHotelNotFound() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        RoomMapper roomMapper = mock(RoomMapper.class);

        RoomService roomService = new RoomServiceImpl(roomRepository, hotelRepository, amenityRepository, roomMapper);

        RoomRequestDto dto = new RoomRequestDto();
        dto.setHotelId(10L);

        when(hotelRepository.findById(10L)).thenReturn(Optional.empty());

        RoomResponseDto result = roomService.create(dto);

        Assertions.assertNull(result);

        verify(hotelRepository, times(1)).findById(10L);
        verify(roomRepository, never()).save(any());
    }

    @Test
    void createWhenAmenitiesMissing() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        RoomMapper roomMapper = mock(RoomMapper.class);

        RoomService roomService = new RoomServiceImpl(roomRepository, hotelRepository, amenityRepository, roomMapper);

        RoomRequestDto dto = new RoomRequestDto();
        dto.setRoomNumber("101");
        dto.setType("STANDARD");
        dto.setPricePerNight(100);
        dto.setCapacity(2);
        dto.setHotelId(10L);
        dto.setAmenityIds(List.of(1L, 2L));

        Hotel hotel = new Hotel();
        hotel.setId(10L);

        Amenity a1 = new Amenity();
        a1.setId(1L);

        when(hotelRepository.findById(10L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findAllById(dto.getAmenityIds())).thenReturn(List.of(a1));

        RoomResponseDto result = roomService.create(dto);

        Assertions.assertNull(result);

        verify(roomRepository, never()).save(any());
    }

    @Test
    void setAvailabilityWhenRoomNotFound() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        RoomMapper roomMapper = mock(RoomMapper.class);

        RoomService roomService = new RoomServiceImpl(roomRepository, hotelRepository, amenityRepository, roomMapper);

        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        Boolean result = roomService.setAvailability(1L, false);

        Assertions.assertFalse(result);

        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, never()).save(any());
    }

    @Test
    void deleteWhenNotFound() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        AmenityRepository amenityRepository = mock(AmenityRepository.class);
        RoomMapper roomMapper = mock(RoomMapper.class);

        RoomService roomService = new RoomServiceImpl(roomRepository, hotelRepository, amenityRepository, roomMapper);

        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = roomService.delete(1L);

        Assertions.assertFalse(result);

        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, never()).deleteById(anyLong());
    }
}
