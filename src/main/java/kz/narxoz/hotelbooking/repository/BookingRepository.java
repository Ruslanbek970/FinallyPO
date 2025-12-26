package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Room;
import kz.narxoz.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User user);
    List<Booking> findAllByRoom(Room room);
}
