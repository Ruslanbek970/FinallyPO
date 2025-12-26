package com.example.booking.repository;

import com.example.booking.Entity.Payment;
import com.example.booking.Enum.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByBooking_Id(Long bookingId);
    List<Payment> findAllByStatus(PaymentStatus status);
}
