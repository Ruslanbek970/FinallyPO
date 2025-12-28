package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.HotelRequestDto;
import kz.narxoz.hotelbooking.dto.response.HotelResponseDto;
import kz.narxoz.hotelbooking.model.City;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class HotelMapperTest {

    private final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);

    @Test
    void convertEntityToDto() {
        City city = new City();
        city.setId(5L);
        city.setName("Almaty");

        User manager = new User();
        manager.setId(7L);
        manager.setFullName("Manager Name");

        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setAddress("Address");
        hotel.setDescription("Desc");
        hotel.setRating(4.5);
        hotel.setActive(true);
        hotel.setCity(city);
        hotel.setManager(manager);

        HotelResponseDto dto = hotelMapper.toDto(hotel);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getName());
        Assertions.assertNotNull(dto.getAddress());
        Assertions.assertNotNull(dto.getDescription());
        Assertions.assertNotNull(dto.getCityId());
        Assertions.assertNotNull(dto.getCityName());
        Assertions.assertNotNull(dto.getManagerId());
        Assertions.assertNotNull(dto.getManagerFullName());

        Assertions.assertEquals(hotel.getId(), dto.getId());
        Assertions.assertEquals(hotel.getName(), dto.getName());
        Assertions.assertEquals(hotel.getAddress(), dto.getAddress());
        Assertions.assertEquals(hotel.getDescription(), dto.getDescription());
        Assertions.assertEquals(hotel.getRating(), dto.getRating());
        Assertions.assertEquals(hotel.isActive(), dto.isActive());

        Assertions.assertEquals(city.getId(), dto.getCityId());
        Assertions.assertEquals(city.getName(), dto.getCityName());

        Assertions.assertEquals(manager.getId(), dto.getManagerId());
        Assertions.assertEquals(manager.getFullName(), dto.getManagerFullName());
    }

    @Test
    void convertDtoToEntity() {
        HotelRequestDto dto = new HotelRequestDto();
        dto.setName("Test Hotel");
        dto.setAddress("Address");
        dto.setDescription("Desc");
        dto.setCityId(5L);
        dto.setManagerId(7L);
        dto.setActive(true);

        Hotel hotel = hotelMapper.toEntity(dto);

        Assertions.assertNotNull(hotel);

        Assertions.assertNotNull(hotel.getName());
        Assertions.assertNotNull(hotel.getAddress());
        Assertions.assertNotNull(hotel.getDescription());

        Assertions.assertEquals(dto.getName(), hotel.getName());
        Assertions.assertEquals(dto.getAddress(), hotel.getAddress());
        Assertions.assertEquals(dto.getDescription(), hotel.getDescription());
        Assertions.assertEquals(dto.isActive(), hotel.isActive());

        // cityId/managerId are not mapped to City/User in mapper
        Assertions.assertNull(hotel.getCity());
        Assertions.assertNull(hotel.getManager());
    }

    @Test
    void convertEntityListToDtoList() {
        City city = new City();
        city.setId(5L);
        city.setName("Almaty");

        User manager = new User();
        manager.setId(7L);
        manager.setFullName("Manager Name");

        Hotel h1 = new Hotel();
        h1.setId(1L);
        h1.setName("H1");
        h1.setAddress("A1");
        h1.setDescription("D1");
        h1.setRating(4.0);
        h1.setActive(true);
        h1.setCity(city);
        h1.setManager(manager);

        Hotel h2 = new Hotel();
        h2.setId(2L);
        h2.setName("H2");
        h2.setAddress("A2");
        h2.setDescription("D2");
        h2.setRating(3.0);
        h2.setActive(false);
        h2.setCity(city);
        h2.setManager(manager);

        List<Hotel> hotels = new ArrayList<>();
        hotels.add(h1);
        hotels.add(h2);

        List<HotelResponseDto> dtoList = hotelMapper.toDtoList(hotels);

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(hotels.size(), dtoList.size());

        for (int i = 0; i < dtoList.size(); i++) {
            HotelResponseDto dto = dtoList.get(i);
            Hotel entity = hotels.get(i);

            Assertions.assertNotNull(dto);
            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getName());
            Assertions.assertNotNull(dto.getCityId());
            Assertions.assertNotNull(dto.getManagerId());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getName(), dto.getName());
            Assertions.assertEquals(entity.getCity().getId(), dto.getCityId());
            Assertions.assertEquals(entity.getManager().getId(), dto.getManagerId());
        }
    }
}
