package com.booking_service.service;

import com.booking_service.entity.Booking;
import com.booking_service.entity.User;
import com.booking_service.model.BookingDTO;
import com.booking_service.model.CreateBookingRequestDTO;
import com.booking_service.repository.BookingRepository;
import com.booking_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public List<BookingDTO> getBookingListForWeek(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        return bookingRepository.findByDateBetween(startDate, endDate);
    }

    public ResponseEntity<?> createNewBooking(CreateBookingRequestDTO requestDTO, Authentication authentication) throws Exception {
        LocalTime newStartTime = requestDTO.getStartTime();
        LocalTime newEndTime = requestDTO.getEndTime();

        if (!isCorrectTimeInterval(newStartTime, newEndTime))
            return ResponseEntity
                    .badRequest()
                    .body("Некорректное значение");

        List<Booking> bookingListOfDay = bookingRepository.findByDate(requestDTO.getDate());
        User creator = userRepository.findByEmail(authentication.getName());
        Booking newBooking = Booking.builder()
                .user(creator)
                .title(requestDTO.getTitle())
                .date(requestDTO.getDate())
                .startTime(String.valueOf(requestDTO.getStartTime()))
                .endTime(String.valueOf(requestDTO.getEndTime()))
                .build();

        for (Booking existingBooking : bookingListOfDay) {
            if (!hasAvailableTime(existingBooking, newBooking)) {
                return ResponseEntity
                        .badRequest()
                        .body("Время занято");
            }
        }
        Booking savedBooking = bookingRepository.save(newBooking);
        return ResponseEntity.ok(savedBooking);
    }

    private boolean isCorrectTimeInterval(LocalTime startTime, LocalTime endTime) {
        return endTime.isAfter(startTime);
    }

    private LocalTime fromStrToLocalTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private boolean hasAvailableTime(Booking existingBooking, Booking newBooking) {
        LocalTime existingStartTime = fromStrToLocalTime(existingBooking.getStartTime());
        LocalTime existingEndTime = fromStrToLocalTime(existingBooking.getEndTime());
        LocalTime newStartTime = fromStrToLocalTime(newBooking.getStartTime());
        LocalTime newEndTime = fromStrToLocalTime(newBooking.getEndTime());

        boolean availableBeforeExistingTimeSlot = newStartTime.isBefore(existingStartTime)
                && (newEndTime.isBefore(existingStartTime) || newEndTime.equals(existingStartTime));
        boolean availableAfterExistingTimeSlot =
                (newStartTime.isAfter(existingEndTime) || newStartTime.equals(existingEndTime))
                && newEndTime.isAfter(existingEndTime);

        return availableBeforeExistingTimeSlot || availableAfterExistingTimeSlot;
    }

    public ResponseEntity<?> deleteBookingById(Long id, Authentication authentication) {
        Optional<Booking> booking = bookingRepository.findById(id);
        User bookingOwner = booking.get().getUser();
        String userEmail = authentication.getName();

        if (bookingOwner.getEmail().equals(userEmail)) {
            bookingRepository.delete(booking.get());
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity
                .badRequest()
                .build();
    }
}
