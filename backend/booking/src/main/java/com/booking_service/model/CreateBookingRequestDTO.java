package com.booking_service.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateBookingRequestDTO {
    private String title;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
