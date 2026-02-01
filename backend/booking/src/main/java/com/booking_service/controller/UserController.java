package com.booking_service.controller;

import com.booking_service.model.LoginRequestDTO;
import com.booking_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
        String token = userService.getAccessToken(requestDTO);
        return ResponseEntity
                .ok(Collections.singletonMap("token", token));
    }
}
