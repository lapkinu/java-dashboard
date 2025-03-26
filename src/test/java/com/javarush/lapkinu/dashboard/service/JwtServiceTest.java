package com.javarush.lapkinu.dashboard.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private static final String SECRET_KEY_STRING = "TlxlvNoxV9SUTNpK9oLaWx0rouiQFznMq9JK4lT/Mhkjhyr0efC6jd8uSdU/XfcQ9rtH8t7dv/1RhuENuE01PQ=="; // Длина >= 256 бит (HS512)
    private static final long EXPIRATION_TIME = 3600L;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        setPrivateField(jwtService, "secretKeyString", SECRET_KEY_STRING);
        setPrivateField(jwtService, "jwtExpiration", EXPIRATION_TIME);
    }

    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY_STRING);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void generateToken_validUserDetails_returnsToken() {
        UserDetails userDetails = new User("testuser@example.com", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token, "the generated token should not be null");
        assertFalse(token.isEmpty(), "the generated token should not be empty");
    }

    @Test
    void extractUsername_validToken_returnsCorrectUsername() {
        String expectedUsername = "testuser@example.com";
        UserDetails userDetails = new User(expectedUsername, "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        String actualUsername = jwtService.extractUsername(token);
        assertEquals(expectedUsername, actualUsername, "the user s name must match the expected");
    }

    @Test
    void isTokenValid_validTokenAndUserDetails_returnsTrue() {
        String username = "testuser@example.com";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid, "token should be valid");
    }

    @Test
    void isTokenValid_expiredToken_returnsFalse() {
        UserDetails userDetails = new User("testuser@example.com", "password", Collections.emptyList());
        String expiredToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() - 2000))
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
        boolean isValid = jwtService.isTokenValid(expiredToken, userDetails);
        assertFalse(isValid, "Expensible token should be unimportant");
    }

    @Test
    void isTokenValid_invalidUsername_returnsFalse() {
        UserDetails userDetails1 = new User("user1@example.com", "password", Collections.emptyList());
        UserDetails userDetails2 = new User("user2@example.com", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails1);
        boolean isValid = jwtService.isTokenValid(token, userDetails2);
        assertFalse(isValid, "token should be universal for another user");
    }

    @Test
    void extractClaim_validTokenAndClaim_returnsClaimValue() {
        String username = "test@user.com";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());
        String token = jwtService.generateToken(new HashMap<>(), userDetails);
        Date extractedExpiration = jwtService.extractClaim(token, Claims::getExpiration);
        assertNotNull(extractedExpiration);
    }

    @Test
    void extractAllClaims_validToken_returnsClaims() {
        UserDetails userDetails = new User("claimUser", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals("claimUser", claims.getSubject());
    }
}