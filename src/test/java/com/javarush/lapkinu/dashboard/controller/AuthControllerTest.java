package com.javarush.lapkinu.dashboard.controller;

import com.javarush.lapkinu.dashboard.dto.AuthRequest;
import com.javarush.lapkinu.dashboard.dto.AuthResponse;
import com.javarush.lapkinu.dashboard.service.JwtService;
import com.javarush.lapkinu.dashboard.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthController authController;
    private UserService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        jwtService = mock(JwtService.class);
        authenticationManager = mock(AuthenticationManager.class);
        authController = new AuthController(userService, jwtService, authenticationManager);
    }

    @Test
    void registerUser_success() {
        AuthRequest request = new AuthRequest("test@example.com", "password", "Test User");

        ResponseEntity<String> response = authController.registerUser(request);

        verify(userService).createUser("Test User", "test@example.com", "password");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    void loginUser_success() {
        // Подготовка данных
        AuthRequest request = new AuthRequest("test@example.com", "password", null);

        // Мок UserDetails
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        // Мок Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Настройка authenticationManager
        when(authenticationManager.authenticate(
                argThat(token -> token.getPrincipal().equals("test@example.com") &&
                        token.getCredentials().equals("password"))
        )).thenReturn(authentication);

        // Настройка jwtService
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token123");

        // Выполнение
        ResponseEntity<AuthResponse> response = authController.loginUser(request);

        // Проверка
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token123", response.getBody().getToken());

        // Верификация вызовов
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(userDetails);
    }
}