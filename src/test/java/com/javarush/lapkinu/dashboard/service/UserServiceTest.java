
package com.javarush.lapkinu.dashboard.service;

import com.javarush.lapkinu.dashboard.entity.User;
import com.javarush.lapkinu.dashboard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_validInput_savesUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User savedUser = (User) i.getArgument(0);
            savedUser.setId(UUID.randomUUID());
            return savedUser;
        });

        User savedUser = userService.createUser(user.getEmail(), user.getPassword(), user.getName());
        assertNotNull(savedUser);
        assertEquals("hashedPassword", savedUser.getPassword());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("Test User", savedUser.getName());
        assertNotNull(savedUser.getId());
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findUserByEmail_userExists_returnsUser() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setName("Test User");
        user.setId(UUID.randomUUID());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findUserByEmail(email);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        assertEquals("Test User", foundUser.get().getName());
        assertEquals(user.getId(), foundUser.get().getId());
    }

    @Test
    void findUserByEmail_userDoesNotExist_returnsEmptyOptional() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        Optional<User> foundUser = userService.findUserByEmail(email);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void checkPassword_correctPassword_returnsTrue() {
        User user = new User();
        user.setPassword("hashedPassword");
        when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true);
        boolean result = userService.checkPassword(user, "password");
        assertTrue(result);
        verify(passwordEncoder).matches("password", "hashedPassword");
    }

    @Test
    void checkPassword_incorrectPassword_returnsFalse() {
        User user = new User();
        user.setPassword("hashedPassword");
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);
        boolean result = userService.checkPassword(user, "wrongPassword");
        assertFalse(result);
        verify(passwordEncoder).matches("wrongPassword", "hashedPassword");
    }
}