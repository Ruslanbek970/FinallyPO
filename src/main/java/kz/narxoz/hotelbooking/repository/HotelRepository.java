package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAllByManager(User manager);
}
