package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.HotelRequestDto;
import kz.narxoz.hotelbooking.dto.response.HotelResponseDto;
import kz.narxoz.hotelbooking.mapper.HotelMapper;
import kz.narxoz.hotelbooking.model.City;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.CityRepository;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import kz.narxoz.hotelbooking.service.HotelService;
import kz.narxoz.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelResponseDto> getAll() {
        return hotelMapper.toDtoList(hotelRepository.findAll());
    }

    @Override
    public HotelResponseDto getById(Long id) {
        Hotel h = hotelRepository.findById(id).orElse(null);
        if (Objects.isNull(h)) return null;
        return hotelMapper.toDto(h);
    }

    @Override
    public HotelResponseDto create(HotelRequestDto dto) {
        City city = cityRepository.findById(dto.getCityId()).orElse(null);
        if (city == null) return null;

        User manager;
        if (dto.getManagerId() != null) {
            manager = userRepository.findById(dto.getManagerId()).orElse(null);
        } else {
            manager = userService.getCurrentUserEntity();
        }
        if (manager == null) return null;

        Hotel h = new Hotel();
        h.setName(dto.getName());
        h.setAddress(dto.getAddress());
        h.setDescription(dto.getDescription());
        h.setActive(dto.isActive());
        h.setCity(city);
        h.setManager(manager);

        Hotel saved = hotelRepository.save(h);
        return hotelMapper.toDto(saved);
    }

    @Override
    public HotelResponseDto update(Long id, HotelRequestDto dto) {
        Hotel h = hotelRepository.findById(id).orElse(null);
        if (Objects.isNull(h)) return null;

        City city = cityRepository.findById(dto.getCityId()).orElse(null);
        if (city == null) return null;

        h.setName(dto.getName());
        h.setAddress(dto.getAddress());
        h.setDescription(dto.getDescription());
        h.setActive(dto.isActive());
        h.setCity(city);

        if (dto.getManagerId() != null) {
            User manager = userRepository.findById(dto.getManagerId()).orElse(null);
            if (manager != null) h.setManager(manager);
        }

        Hotel saved = hotelRepository.save(h);
        return hotelMapper.toDto(saved);
    }

    @Override
    public List<HotelResponseDto> getMyHotels() {
        User manager = userService.getCurrentUserEntity();
        if (manager == null) return List.of();
        return hotelMapper.toDtoList(hotelRepository.findAllByManager(manager));
    }

    @Override
    public boolean delete(Long id) {
        Hotel h = hotelRepository.findById(id).orElse(null);
        if (Objects.isNull(h)) return false;
        hotelRepository.deleteById(id);
        return true;
    }
}
