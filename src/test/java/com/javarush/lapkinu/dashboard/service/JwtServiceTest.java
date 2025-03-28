package com.javarush.lapkinu.dashboard.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        jwtService.secretKeyString = Base64.getEncoder().encodeToString(keyBytes);
        jwtService.jwtExpiration = 3600; // 1 час
    }

    @Test
    void generateToken_success() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertEquals("test@example.com", jwtService.extractUsername(token));
    }

    @Test
    void isTokenValid_success() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        String token = jwtService.generateToken(userDetails);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValid_expiredToken_returnsFalse() throws Exception {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Field secretKeyField = JwtService.class.getDeclaredField("secretKeyString");
        secretKeyField.setAccessible(true);
        String secretKeyString = (String) secretKeyField.get(jwtService);
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));

        String expiredToken = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(new Date(System.currentTimeMillis() - 2 * 3600 * 1000))
                .setExpiration(new Date(System.currentTimeMillis() - 3600 * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        assertFalse(jwtService.isTokenValid(expiredToken, userDetails));
    }

    @Test
    void isTokenValid_wrongUser_returnsFalse() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        UserDetails wrongUserDetails = mock(UserDetails.class);
        when(wrongUserDetails.getUsername()).thenReturn("other@example.com");

        String token = jwtService.generateToken(wrongUserDetails);

        assertFalse(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void generateToken_minExpiration_success() throws InterruptedException {
        jwtService.jwtExpiration = 1; // 1 секунда
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));

        Thread.sleep(2000);
        assertFalse(jwtService.isTokenValid(token, userDetails));
    }
}