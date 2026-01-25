package com.booking_service.model;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
