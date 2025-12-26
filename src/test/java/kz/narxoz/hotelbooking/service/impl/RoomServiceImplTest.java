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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
    private AmenityRepository amenityRepository;
    private RoomMapper roomMapper;

    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        roomRepository = mock(RoomRepository.class);
        hotelRepository = mock(HotelRepository.class);
        amenityRepository = mock(AmenityRepository.class);
        roomMapper = mock(RoomMapper.class);

        roomService = new RoomServiceImpl(roomRepository, hotelRepository, amenityRepository, roomMapper);
    }

    @Test
    void create_shouldThrowIfHotelNotFound() {
        RoomRequestDto dto = new RoomRequestDto();
        dto.setHotelId(1L);

        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> roomService.create(dto));
        assertEquals("Hotel not found", ex.getMessage());
    }

    @Test
    void create_shouldThrowIfSomeAmenitiesNotFound() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);

        RoomRequestDto dto = new RoomRequestDto();
        dto.setHotelId(1L);
        dto.setAmenityIds(List.of(1L, 2L)); // запросил 2

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(new Amenity())); // нашли 1

        RuntimeException ex = assertThrows(RuntimeException.class, () -> roomService.create(dto));
        assertEquals("Some amenities not found", ex.getMessage());
    }

    @Test
    void setAvailability_shouldThrowIfRoomNotFound() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> roomService.setAvailability(99L, true));
        assertEquals("Room not found", ex.getMessage());
    }

    @Test
    void setAvailability_shouldUpdateAndReturnTrue() {
        Room room = new Room();
        room.setId(1L);
        room.setAvailable(false);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        Boolean result = roomService.setAvailability(1L, true);

        assertTrue(result);
        assertTrue(room.isAvailable());
        verify(roomRepository).save(room);
    }

    @Test
    void getByHotel_shouldThrowIfHotelNotFound() {
        when(hotelRepository.findById(77L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> roomService.getByHotel(77L));
        assertEquals("Hotel not found", ex.getMessage());
    }

    @Test
    void getByHotel_shouldReturnRooms() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.findAllByHotel(hotel)).thenReturn(List.of(new Room()));

        when(roomMapper.toDtoList(anyList())).thenReturn(List.of(new RoomResponseDto()));

        List<RoomResponseDto> res = roomService.getByHotel(1L);

        assertEquals(1, res.size());
        verify(roomRepository).findAllByHotel(hotel);
    }
}
