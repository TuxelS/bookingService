package com.booking_service.controller;

import com.booking_service.entity.Booking;
import com.booking_service.model.CreateBookingRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @GetMapping("/week")
    public List<Booking> bookingsList(@RequestParam(name = "startDate", required = true) LocalDate startDate) {
        return null;
    }

    @PostMapping
    public Booking createNewBooking(@RequestBody CreateBookingRequestDTO requestDTO) {
        return null;
    }

    //todo вероятно в заголовок надо пихать юзера, и здесь в header получать
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
    }



}
