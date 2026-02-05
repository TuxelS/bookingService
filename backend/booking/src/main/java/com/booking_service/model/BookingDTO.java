package com.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BookingDTO {

    private String title;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String userEmail;
    private String userFullName;
}
