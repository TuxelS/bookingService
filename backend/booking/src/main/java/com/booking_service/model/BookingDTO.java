package com.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    private String title;
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String userEmail;
    private String userFullName;
}
