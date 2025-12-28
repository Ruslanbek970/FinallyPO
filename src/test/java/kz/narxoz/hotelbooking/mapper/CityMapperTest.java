package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.CityRequestDto;
import kz.narxoz.hotelbooking.dto.response.CityResponseDto;
import kz.narxoz.hotelbooking.model.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class CityMapperTest {

    private final CityMapper cityMapper = Mappers.getMapper(CityMapper.class);

    @Test
    void convertEntityToDto() {
        City city = new City();
        city.setId(1L);
        city.setName("Almaty");
        city.setCountry("KZ");

        CityResponseDto dto = cityMapper.toDto(city);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getName());
        Assertions.assertNotNull(dto.getCountry());

        Assertions.assertEquals(city.getId(), dto.getId());
        Assertions.assertEquals(city.getName(), dto.getName());
        Assertions.assertEquals(city.getCountry(), dto.getCountry());
    }

    @Test
    void convertDtoToEntity() {
        CityRequestDto dto = new CityRequestDto();
        dto.setName("Astana");
        dto.setCountry("KZ");

        City city = cityMapper.toEntity(dto);

        Assertions.assertNotNull(city);

        Assertions.assertNotNull(city.getName());
        Assertions.assertNotNull(city.getCountry());

        Assertions.assertEquals(dto.getName(), city.getName());
        Assertions.assertEquals(dto.getCountry(), city.getCountry());
    }

    @Test
    void convertEntityListToDtoList() {
        City c1 = new City();
        c1.setId(1L);
        c1.setName("Almaty");
        c1.setCountry("KZ");

        City c2 = new City();
        c2.setId(2L);
        c2.setName("Tokyo");
        c2.setCountry("JP");

        List<City> cities = new ArrayList<>();
        cities.add(c1);
        cities.add(c2);

        List<CityResponseDto> dtoList = cityMapper.toDtoList(cities);

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(cities.size(), dtoList.size());

        for (int i = 0; i < dtoList.size(); i++) {
            CityResponseDto dto = dtoList.get(i);
            City entity = cities.get(i);

            Assertions.assertNotNull(dto);

            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getName());
            Assertions.assertNotNull(dto.getCountry());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getName(), dto.getName());
            Assertions.assertEquals(entity.getCountry(), dto.getCountry());
        }
    }
}
