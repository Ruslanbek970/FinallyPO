package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.PropertyRequestDto;
import com.example.booking.Dto.response.PropertyResponseDto;
import com.example.booking.Entity.Property;
import com.example.booking.Entity.User;
import com.example.booking.Mapper.PropertyMapper;
import com.example.booking.Service.PropertyService;
import com.example.booking.repository.PropertyRepository;
import com.example.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyMapper propertyMapper;

    @Override
    public PropertyResponseDto create(PropertyRequestDto dto) {
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));

        Property property = propertyMapper.toEntity(dto);
        property.setOwner(owner);

        return propertyMapper.toDto(propertyRepository.save(property));
    }

    @Override
    public PropertyResponseDto update(Long id, PropertyRequestDto dto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));

        property.setOwner(owner);
        property.setType(dto.getType());
        property.setName(dto.getName());
        property.setDescription(dto.getDescription());
        property.setCity(dto.getCity());
        property.setAddress(dto.getAddress());

        return propertyMapper.toDto(propertyRepository.save(property));
    }

    @Override
    public PropertyResponseDto getById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));
        return propertyMapper.toDto(property);
    }

    @Override
    public List<PropertyResponseDto> getAll() {
        return propertyMapper.toDtoList(propertyRepository.findAll());
    }

    @Override
    public List<PropertyResponseDto> getByOwnerId(Long ownerId) {
        return propertyMapper.toDtoList(propertyRepository.findByOwnerId(ownerId));
    }

    @Override
    public List<PropertyResponseDto> getByCity(String city) {
        return propertyMapper.toDtoList(propertyRepository.findByCityIgnoreCase(city));
    }

    @Override
    public void deleteById(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }
        propertyRepository.deleteById(id);
    }
}
