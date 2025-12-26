package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.RoomRequestDto;
import kz.narxoz.hotelbooking.dto.response.RoomResponseDto;
import kz.narxoz.hotelbooking.model.Amenity;
import kz.narxoz.hotelbooking.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = Amenity.class)
public interface RoomMapper {

    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "hotel.name", target = "hotelName")
    @Mapping(target = "amenities",
            expression = "java(room.getAmenities() == null ? java.util.List.of() : room.getAmenities().stream().map(Amenity::getName).toList())")
    RoomResponseDto toDto(Room room);

    List<RoomResponseDto> toDtoList(List<Room> rooms);

    Room toEntity(RoomRequestDto dto);
}
