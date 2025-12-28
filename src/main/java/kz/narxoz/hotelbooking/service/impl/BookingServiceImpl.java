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
    private final UserService userService;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto create(BookingRequestDto dto) {
        User user = userService.getCurrentUserEntity();
        if (user == null) return null;

        Room room = roomRepository.findById(dto.getRoomId()).orElse(null);
        if (room == null || !room.isAvailable()) return null;

        long days = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        if (days <= 0) return null;

        int totalPrice = (int) days * room.getPricePerNight();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setStatus("PENDING");
        booking.setTotalPrice(totalPrice);

        Booking saved = bookingRepository.save(booking);

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

        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return false;

        if (!booking.getUser().getId().equals(user.getId())) return false;

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        Room room = booking.getRoom();
        room.setAvailable(true);
        roomRepository.save(room);

        return true;
    }

    @Override
    public List<BookingResponseDto> getManagerBookings() {
        User manager = userService.getCurrentUserEntity();
        if (manager == null) return List.of();

        return bookingMapper.toDtoList(
                bookingRepository.findAllByRoom_Hotel_Manager(manager)
        );
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
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return null;

        return bookingMapper.toDto(booking);
    }

    @Override
    public boolean delete(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return false;

        Room room = booking.getRoom();
        if (room != null) {
            room.setAvailable(true);
            roomRepository.save(room);
        }

        bookingRepository.deleteById(bookingId);
        return true;
    }
}