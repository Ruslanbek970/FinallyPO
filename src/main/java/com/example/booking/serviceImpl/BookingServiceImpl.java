package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.BookingRequestDto;
import com.example.booking.Dto.response.BookingResponseDto;
import com.example.booking.Entity.*;
import com.example.booking.Enum.BookingStatus;
import com.example.booking.Mapper.BookingMapper;
import com.example.booking.Service.BookingService;
import com.example.booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final RatePlanRepository ratePlanRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto create(BookingRequestDto dto) {
        User guest = userRepository.findById(dto.getGuestId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found"));
        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));
        RatePlan ratePlan = ratePlanRepository.findById(dto.getRatePlanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate plan not found"));

        Booking booking = bookingMapper.toEntity(dto);
        booking.setGuest(guest);
        booking.setUnit(unit);
        booking.setRatePlan(ratePlan);

        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDto getById(Long id) {
        return bookingMapper.toDto(
                bookingRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"))
        );
    }

    @Override
    public List<BookingResponseDto> getAll() {
        return bookingMapper.toDtoList(bookingRepository.findAll());
    }

    @Override
    public List<BookingResponseDto> getByGuestId(Long guestId) {
        return bookingMapper.toDtoList(bookingRepository.findByGuestId(guestId));
    }

    @Override
    public List<BookingResponseDto> getByUnitId(Long unitId) {
        return bookingMapper.toDtoList(bookingRepository.findByUnitId(unitId));
    }

    @Override
    public BookingResponseDto changeStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        booking.setStatus(status);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public void deleteById(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }
        bookingRepository.deleteById(id);
    }
}
