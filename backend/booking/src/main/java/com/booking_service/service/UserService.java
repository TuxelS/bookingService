package com.booking_service.service;

import com.booking_service.model.LoginRequestDTO;
import com.booking_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public String getAccessToken(LoginRequestDTO requestDTO) {
        Authentication authRequest = new UsernamePasswordAuthenticationToken(
                requestDTO.getEmail(),
                requestDTO.getPassword()
        );
        Authentication authentication = authManager.authenticate(authRequest);
        return jwtUtil.generateAccessToken(authentication.getName());
    }
}
