package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.RoomRequestDto;
import kz.narxoz.hotelbooking.dto.response.RoomResponseDto;
import kz.narxoz.hotelbooking.mapper.RoomMapper;
import kz.narxoz.hotelbooking.model.Amenity;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Room;
import kz.narxoz.hotelbooking.repository.AmenityRepository;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.RoomRepository;
import kz.narxoz.hotelbooking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomResponseDto> getByHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        if (hotel == null) return List.of();

        return roomMapper.toDtoList(roomRepository.findAllByHotel(hotel));
    }

    @Override
    public RoomResponseDto getById(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) return null;
        return roomMapper.toDto(room);
    }

    @Override
    public RoomResponseDto create(RoomRequestDto dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId()).orElse(null);
        if (hotel == null) return null;

        List<Amenity> amenities = List.of();
        if (dto.getAmenityIds() != null && !dto.getAmenityIds().isEmpty()) {
            amenities = amenityRepository.findAllById(dto.getAmenityIds());
            if (amenities.size() != dto.getAmenityIds().size()) return null;
        }

        Room r = new Room();
        r.setRoomNumber(dto.getRoomNumber());
        r.setType(dto.getType());
        r.setCapacity(dto.getCapacity());
        r.setPricePerNight(dto.getPricePerNight());
        r.setAvailable(dto.isAvailable());
        r.setHotel(hotel);
        r.setAmenities(amenities);

        Room saved = roomRepository.save(r);
        return roomMapper.toDto(saved);
    }

    @Override
    public RoomResponseDto update(Long id, RoomRequestDto dto) {
        Room r = roomRepository.findById(id).orElse(null);
        if (r == null) return null;

        Hotel hotel = hotelRepository.findById(dto.getHotelId()).orElse(null);
        if (hotel == null) return null;

        List<Amenity> amenities = List.of();
        if (dto.getAmenityIds() != null && !dto.getAmenityIds().isEmpty()) {
            amenities = amenityRepository.findAllById(dto.getAmenityIds());
            if (amenities.size() != dto.getAmenityIds().size()) return null;
        }

        r.setRoomNumber(dto.getRoomNumber());
        r.setType(dto.getType());
        r.setCapacity(dto.getCapacity());
        r.setPricePerNight(dto.getPricePerNight());
        r.setAvailable(dto.isAvailable());
        r.setHotel(hotel);
        r.setAmenities(amenities);

        Room saved = roomRepository.save(r);
        return roomMapper.toDto(saved);
    }

    @Override
    public Boolean setAvailability(Long id, boolean available) {
        Room r = roomRepository.findById(id).orElse(null);
        if (r == null) return false;

        r.setAvailable(available);
        roomRepository.save(r);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Room r = roomRepository.findById(id).orElse(null);
        if (Objects.isNull(r)) return false;
        roomRepository.deleteById(id);
        return true;
    }
}
