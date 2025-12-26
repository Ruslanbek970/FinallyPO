package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.Guest;
import kz.narxoz.hotelbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findAllByBooking(Booking booking);
}
