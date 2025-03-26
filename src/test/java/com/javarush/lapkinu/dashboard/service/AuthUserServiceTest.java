
package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.entity.User;
import com.javarush.lapkinu.dashboard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthUserService authUserService;

    @Test
    void loadUserByUsername_userExists_returnsUserDetails() {

        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("hashedPassword");
        user.setEnabled(true);
        user.setName("Test User");
        user.setId(UUID.randomUUID());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserDetails userDetails = authUserService.loadUserByUsername(email);
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("hashedPassword", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void loadUserByUsername_userDoesNotExist_throwsException() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> authUserService.loadUserByUsername(email));
        verify(userRepository).findByEmail(email);
    }
}