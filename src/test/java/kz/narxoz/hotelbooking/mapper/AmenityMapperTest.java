package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.AmenityRequestDto;
import kz.narxoz.hotelbooking.dto.response.AmenityResponseDto;
import kz.narxoz.hotelbooking.model.Amenity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class AmenityMapperTest {

    private final AmenityMapper amenityMapper = Mappers.getMapper(AmenityMapper.class);

    @Test
    void convertEntityToDto() {
        Amenity amenity = new Amenity();
        amenity.setId(1L);
        amenity.setName("WiFi");

        AmenityResponseDto dto = amenityMapper.toDto(amenity);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getName());

        Assertions.assertEquals(amenity.getId(), dto.getId());
        Assertions.assertEquals(amenity.getName(), dto.getName());
    }

    @Test
    void convertDtoToEntity() {
        AmenityRequestDto dto = new AmenityRequestDto();
        dto.setName("Parking");

        Amenity amenity = amenityMapper.toEntity(dto);

        Assertions.assertNotNull(amenity);
        Assertions.assertNotNull(amenity.getName());

        Assertions.assertEquals(dto.getName(), amenity.getName());
    }

    @Test
    void convertEntityListToDtoList() {
        Amenity a1 = new Amenity();
        a1.setId(1L);
        a1.setName("WiFi");

        Amenity a2 = new Amenity();
        a2.setId(2L);
        a2.setName("Breakfast");

        List<Amenity> amenities = new ArrayList<>();
        amenities.add(a1);
        amenities.add(a2);

        List<AmenityResponseDto> dtoList = amenityMapper.toDtoList(amenities);

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(amenities.size(), dtoList.size());

        for (int i = 0; i < dtoList.size(); i++) {
            AmenityResponseDto dto = dtoList.get(i);
            Amenity entity = amenities.get(i);

            Assertions.assertNotNull(dto);
            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getName());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getName(), dto.getName());
        }
    }
}
