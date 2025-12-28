package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.RoomRequestDto;
import kz.narxoz.hotelbooking.dto.response.RoomResponseDto;
import kz.narxoz.hotelbooking.model.Amenity;
import kz.narxoz.hotelbooking.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "hotel.name", target = "hotelName")
    @Mapping(source = "amenities", target = "amenities")
    RoomResponseDto toDto(Room room);

    List<RoomResponseDto> toDtoList(List<Room> rooms);

    Room toEntity(RoomRequestDto dto);

    default List<String> mapAmenities(List<Amenity> amenities) {
        if (amenities == null) {
            return List.of();
        }
        return amenities.stream()
                .map(a -> a == null ? null : a.getName())
                .toList();
    }
}