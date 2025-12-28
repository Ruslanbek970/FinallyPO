package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.Review;
import kz.narxoz.hotelbooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByHotel(Hotel hotel);
}
