package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByHotel(Hotel hotel);
}
