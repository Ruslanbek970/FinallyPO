package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.GuestRequestDto;
import kz.narxoz.hotelbooking.dto.response.GuestResponseDto;
import kz.narxoz.hotelbooking.mapper.GuestMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Guest;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.GuestRepository;
import kz.narxoz.hotelbooking.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;
    private final GuestMapper guestMapper;

    @Override
    public GuestResponseDto create(GuestRequestDto dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId()).orElse(null);
        if (Objects.isNull(booking)) return null;

        Guest g = new Guest();
        g.setFirstName(dto.getFirstName());
        g.setLastName(dto.getLastName());
        g.setDocumentNumber(dto.getDocumentNumber());
        g.setBooking(booking);

        Guest saved = guestRepository.save(g);
        return guestMapper.toDto(saved);
    }

    @Override
    public List<GuestResponseDto> getByBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return List.of();
        return guestMapper.toDtoList(guestRepository.findAllByBooking(booking));
    }
}
