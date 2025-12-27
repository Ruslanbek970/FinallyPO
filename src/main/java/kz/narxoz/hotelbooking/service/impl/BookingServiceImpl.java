package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.BookingRequestDto;
import kz.narxoz.hotelbooking.dto.response.BookingResponseDto;
import kz.narxoz.hotelbooking.mapper.BookingMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Room;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.RoomRepository;
import kz.narxoz.hotelbooking.service.BookingService;
import kz.narxoz.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final UserService userService;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto create(BookingRequestDto dto) {
        User user = userService.getCurrentUserEntity();
        if (user == null) return null;

        Room room = roomRepository.findById(dto.getRoomId()).orElse(null);
        if (room == null) return null;

        if (!room.isAvailable()) return null;

        long days = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        if (days <= 0) return null;

        int totalPrice = (int) days * room.getPricePerNight();

        Booking b = new Booking();
        b.setUser(user);
        b.setRoom(room);
        b.setCheckInDate(dto.getCheckInDate());
        b.setCheckOutDate(dto.getCheckOutDate());
        b.setStatus("PENDING");
        b.setTotalPrice(totalPrice);

        Booking saved = bookingRepository.save(b);

        room.setAvailable(false);
        roomRepository.save(room);

        return bookingMapper.toDto(saved);
    }

    @Override
    public List<BookingResponseDto> getMyBookings() {
        User user = userService.getCurrentUserEntity();
        if (user == null) return List.of();

        return bookingMapper.toDtoList(bookingRepository.findAllByUser(user));
    }

    @Override
    public Boolean cancel(Long bookingId) {
        User user = userService.getCurrentUserEntity();
        if (user == null) return false;

        Booking b = bookingRepository.findById(bookingId).orElse(null);
        if (b == null) return false;

        if (!b.getUser().getId().equals(user.getId())) return false;

        b.setStatus("CANCELLED");
        bookingRepository.save(b);

        Room room = b.getRoom();
        room.setAvailable(true);
        roomRepository.save(room);

        return true;
    }

    @Override
    public List<BookingResponseDto> getManagerBookings() {
        User manager = userService.getCurrentUserEntity();
        if (manager == null) return List.of();

        List<Hotel> hotels = hotelRepository.findAllByManager(manager);
        if (hotels.isEmpty()) return List.of();

        List<Booking> filtered = bookingRepository.findAll().stream()
                .filter(b -> hotels.stream().anyMatch(h -> h.getId().equals(b.getRoom().getHotel().getId())))
                .toList();

        return bookingMapper.toDtoList(filtered);
    }

    @Override
    public Boolean confirmByManager(Long bookingId) {
        User manager = userService.getCurrentUserEntity();
        if (manager == null) return false;

        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return false;

        if (!booking.getRoom().getHotel().getManager().getId().equals(manager.getId())) return false;

        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public Boolean cancelByManager(Long bookingId) {
        User manager = userService.getCurrentUserEntity();
        if (manager == null) return false;

        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return false;

        if (!booking.getRoom().getHotel().getManager().getId().equals(manager.getId())) return false;

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        Room room = booking.getRoom();
        room.setAvailable(true);
        roomRepository.save(room);

        return true;
    }

    @Override
    public BookingResponseDto getById(Long bookingId) {
        Booking b = bookingRepository.findById(bookingId).orElse(null);
        if (b == null) return null;
        return bookingMapper.toDto(b);
    }

    @Override
    public boolean delete(Long bookingId) {
        Booking b = bookingRepository.findById(bookingId).orElse(null);
        if (b == null) return false;

        // освобождаем комнату
        Room room = b.getRoom();
        if (room != null) {
            room.setAvailable(true);
            roomRepository.save(room);
        }

        bookingRepository.deleteById(bookingId);
        return true;
    }
}
