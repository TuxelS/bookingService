package com.booking_service.repository;

import com.booking_service.entity.Booking;
import com.booking_service.model.BookingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT new com.booking_service.model.BookingDTO(
                b.title,
                b.date,
                b.startTime,
                b.endTime,
                u.email,
                u.fullName
            )
            FROM Booking b
            JOIN b.user u 
            WHERE b.date BETWEEN :start AND :end
            """)
    List<BookingDTO> findByDateBetween(@Param("start") LocalDate dateStart,
                                       @Param("end") LocalDate dateEnd);

    List<Booking> findByDate(LocalDate date);
}
