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

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomResponseDto> getByHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        return roomMapper.toDtoList(roomRepository.findAllByHotel(hotel));
    }

    @Override
    public RoomResponseDto create(RoomRequestDto dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        List<Amenity> amenities = List.of();
        if (dto.getAmenityIds() != null && !dto.getAmenityIds().isEmpty()) {
            amenities = amenityRepository.findAllById(dto.getAmenityIds());
            if (amenities.size() != dto.getAmenityIds().size()) {
                throw new RuntimeException("Some amenities not found");
            }
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
        Room r = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        List<Amenity> amenities = List.of();
        if (dto.getAmenityIds() != null && !dto.getAmenityIds().isEmpty()) {
            amenities = amenityRepository.findAllById(dto.getAmenityIds());
            if (amenities.size() != dto.getAmenityIds().size()) {
                throw new RuntimeException("Some amenities not found");
            }
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
        Room r = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        r.setAvailable(available);
        roomRepository.save(r);
        return true;
    }
}
