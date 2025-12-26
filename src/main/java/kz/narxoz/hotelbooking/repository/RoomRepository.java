package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByHotel(Hotel hotel);
}
