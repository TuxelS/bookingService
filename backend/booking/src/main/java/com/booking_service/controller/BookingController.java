package com.booking_service.controller;

import com.booking_service.entity.Booking;
import com.booking_service.model.BookingDTO;
import com.booking_service.model.CreateBookingRequestDTO;
import com.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/week")
    public List<BookingDTO> bookingListForWeek(@RequestParam(name = "startDate", required = true) LocalDate startDate) {
        return bookingService.getBookingListForWeek(startDate);
    }

    @PostMapping
    public ResponseEntity<?> createNewBooking(@RequestBody CreateBookingRequestDTO requestDTO, Authentication authentication)
            throws Exception {
        return bookingService.createNewBooking(requestDTO, authentication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id, Authentication authentication) {
       return bookingService.deleteBookingById(id, authentication);
    }

}
