package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.RoomRequestDto;
import kz.narxoz.hotelbooking.dto.response.RoomResponseDto;
import kz.narxoz.hotelbooking.model.Amenity;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class RoomMapperTest {

    private final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    @Test
    void convertEntityToDto() {
        Hotel hotel = new Hotel();
        hotel.setId(10L);
        hotel.setName("Hotel A");

        Amenity a1 = new Amenity();
        a1.setId(1L);
        a1.setName("WiFi");

        Amenity a2 = new Amenity();
        a2.setId(2L);
        a2.setName("Parking");

        Room room = new Room();
        room.setId(5L);
        room.setRoomNumber("101");
        room.setType("STANDARD");
        room.setPricePerNight(100);
        room.setCapacity(2);
        room.setAvailable(true);
        room.setHotel(hotel);
        room.setAmenities(List.of(a1, a2));

        RoomResponseDto dto = roomMapper.toDto(room);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getRoomNumber());
        Assertions.assertNotNull(dto.getType());
        Assertions.assertNotNull(dto.getHotelId());
        Assertions.assertNotNull(dto.getHotelName());
        Assertions.assertNotNull(dto.getAmenities());

        Assertions.assertEquals(room.getId(), dto.getId());
        Assertions.assertEquals(room.getRoomNumber(), dto.getRoomNumber());
        Assertions.assertEquals(room.getType(), dto.getType());
        Assertions.assertEquals(room.getPricePerNight(), dto.getPricePerNight());
        Assertions.assertEquals(room.getCapacity(), dto.getCapacity());
        Assertions.assertEquals(room.isAvailable(), dto.isAvailable());

        Assertions.assertEquals(hotel.getId(), dto.getHotelId());
        Assertions.assertEquals(hotel.getName(), dto.getHotelName());

        Assertions.assertEquals(2, dto.getAmenities().size());
        Assertions.assertEquals("WiFi", dto.getAmenities().get(0));
        Assertions.assertEquals("Parking", dto.getAmenities().get(1));
    }

    @Test
    void convertDtoToEntity() {
        RoomRequestDto dto = new RoomRequestDto();
        dto.setRoomNumber("202");
        dto.setType("DELUXE");
        dto.setPricePerNight(200);
        dto.setCapacity(3);
        dto.setHotelId(10L);
        dto.setAmenityIds(List.of(1L, 2L));

        Room room = roomMapper.toEntity(dto);

        Assertions.assertNotNull(room);

        Assertions.assertNotNull(room.getRoomNumber());
        Assertions.assertNotNull(room.getType());

        Assertions.assertEquals(dto.getRoomNumber(), room.getRoomNumber());
        Assertions.assertEquals(dto.getType(), room.getType());
        Assertions.assertEquals(dto.getPricePerNight(), room.getPricePerNight());
        Assertions.assertEquals(dto.getCapacity(), room.getCapacity());

        // hotelId/amenityIds are not mapped to Hotel/Amenities in mapper
        Assertions.assertNull(room.getHotel());
        Assertions.assertNull(room.getAmenities());
    }

    @Test
    void convertEntityListToDtoList() {
        Hotel hotel = new Hotel();
        hotel.setId(10L);
        hotel.setName("Hotel A");

        Room r1 = new Room();
        r1.setId(1L);
        r1.setRoomNumber("101");
        r1.setType("STANDARD");
        r1.setPricePerNight(100);
        r1.setCapacity(2);
        r1.setAvailable(true);
        r1.setHotel(hotel);

        Room r2 = new Room();
        r2.setId(2L);
        r2.setRoomNumber("102");
        r2.setType("STANDARD");
        r2.setPricePerNight(120);
        r2.setCapacity(2);
        r2.setAvailable(false);
        r2.setHotel(hotel);

        List<Room> rooms = new ArrayList<>();
        rooms.add(r1);
        rooms.add(r2);

        List<RoomResponseDto> dtoList = roomMapper.toDtoList(rooms);

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(rooms.size(), dtoList.size());

        for (int i = 0; i < dtoList.size(); i++) {
            RoomResponseDto dto = dtoList.get(i);
            Room entity = rooms.get(i);

            Assertions.assertNotNull(dto);
            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getRoomNumber());
            Assertions.assertNotNull(dto.getHotelId());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getRoomNumber(), dto.getRoomNumber());
            Assertions.assertEquals(entity.getHotel().getId(), dto.getHotelId());
        }
    }

    @Test
    void mapAmenitiesWhenNull() {
        List<String> names = roomMapper.mapAmenities(null);
        Assertions.assertNotNull(names);
        Assertions.assertEquals(0, names.size());
    }

    @Test
    void mapAmenitiesWhenHasNullElement() {
        Amenity a1 = new Amenity();
        a1.setName("WiFi");

        List<Amenity> amenities = new ArrayList<>();
        amenities.add(a1);
        amenities.add(null);

        List<String> names = roomMapper.mapAmenities(amenities);

        Assertions.assertNotNull(names);
        Assertions.assertEquals(2, names.size());
        Assertions.assertEquals("WiFi", names.get(0));
        Assertions.assertNull(names.get(1));
    }
}
