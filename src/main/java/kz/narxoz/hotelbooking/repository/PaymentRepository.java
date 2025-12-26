package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBooking(Booking booking);
}
