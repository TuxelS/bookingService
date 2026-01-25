package com.booking_service.controller;

import com.booking_service.model.LoginRequestDTO;
import com.booking_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO requestDTO) {
        return userService.getAccessToken(requestDTO);
    }
}
