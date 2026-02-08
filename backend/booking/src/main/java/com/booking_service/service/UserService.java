package com.booking_service.service;

import com.booking_service.entity.User;
import com.booking_service.model.LoginRequestDTO;
import com.booking_service.repository.UserRepository;
import com.booking_service.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostConstruct
    private void createUsers() {
        List<User> users = List.of(
            User.builder()
                    .email("first@email.com")
                    .fullName("Прокофьев Андрей Николаевич")
                    .password(new BCryptPasswordEncoder().encode("first"))
                    .build(),

            User.builder()
                    .email("second@email.com")
                    .fullName("Черышев Евгений Львович")
                    .password(new BCryptPasswordEncoder().encode("second"))
                    .build()
        );
        users.forEach(user -> {
            boolean exists = userRepository.existsByEmail(user.getEmail());
            if (!exists) userRepository.save(user);
        });
    }

    public String getAccessToken(LoginRequestDTO requestDTO) {
        Authentication authRequest = new UsernamePasswordAuthenticationToken(
                requestDTO.getEmail(),
                requestDTO.getPassword()
        );
        Authentication authentication = authManager.authenticate(authRequest);
        return jwtUtil.generateAccessToken(authentication.getName());
    }
}
