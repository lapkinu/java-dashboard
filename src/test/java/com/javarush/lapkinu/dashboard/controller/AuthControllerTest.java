package com.javarush.lapkinu.dashboard.controller;

import com.javarush.lapkinu.dashboard.dto.AuthRequest;
import com.javarush.lapkinu.dashboard.dto.AuthResponse;
import com.javarush.lapkinu.dashboard.service.JwtService;
import com.javarush.lapkinu.dashboard.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
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
    void loginUser_invalidCredentials_throwsBadCredentialsException() {
        AuthRequest request = new AuthRequest("test@example.com", "wrongpassword", null);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));
        assertThrows(BadCredentialsException.class, () -> authController.loginUser(request));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtService);
    }

    @Test
    void loginUser_emptyEmail_throwsException() {
        AuthRequest request = new AuthRequest("", "password", null);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new IllegalArgumentException("Email cannot be empty"));
        assertThrows(IllegalArgumentException.class, () -> authController.loginUser(request));
    }

    @Test
    void loginUser_longEmail_success() {
        String longEmail = "a".repeat(200) + "@example.com"; // 200 символов + домен
        AuthRequest request = new AuthRequest(longEmail, "password", null);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(longEmail);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token123");
        ResponseEntity<AuthResponse> response = authController.loginUser(request);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token123", response.getBody().getToken());
    }
}