package com.divinaprovidencia.api.service;

import com.divinaprovidencia.api.model.User;
import com.divinaprovidencia.api.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void userExistsShouldReturnTrueWhenUserExists() {

        when(userRepository.existsByUsername("nicolas"))
                .thenReturn(true);

        boolean result =
                authService.userExists("  nicolas  ");

        assertTrue(result);

        verify(userRepository)
                .existsByUsername("nicolas");
    }

    @Test
    void registerShouldEncodePasswordAndSaveUser() {

        User savedUser = mock(User.class);

        when(passwordEncoder.encode("ClaveSegura123"))
                .thenReturn("hashed-password");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        User result =
                authService.register(
                        "  nicolas  ",
                        "ClaveSegura123"
                );

        assertSame(savedUser, result);

        verify(passwordEncoder)
                .encode("ClaveSegura123");

        verify(userRepository)
                .save(any(User.class));
    }

    @Test
    void authenticateShouldReturnTrueWithCorrectPassword() {

        User user = mock(User.class);

        when(user.getPasswordHash())
                .thenReturn("hashed-password");

        when(userRepository.findByUsername("nicolas"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "ClaveSegura123",
                "hashed-password"
        )).thenReturn(true);

        boolean result =
                authService.authenticate(
                        "  nicolas  ",
                        "ClaveSegura123"
                );

        assertTrue(result);
    }

    @Test
    void authenticateShouldReturnFalseWhenUserDoesNotExist() {

        when(userRepository.findByUsername("inexistente"))
                .thenReturn(Optional.empty());

        boolean result =
                authService.authenticate(
                        "inexistente",
                        "ClaveSegura123"
                );

        assertFalse(result);

        verify(passwordEncoder, never())
                .matches(anyString(), anyString());
    }

    @Test
    void authenticateShouldReturnFalseWithWrongPassword() {

        User user = mock(User.class);

        when(user.getPasswordHash())
                .thenReturn("hashed-password");

        when(userRepository.findByUsername("nicolas"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "ClaveIncorrecta",
                "hashed-password"
        )).thenReturn(false);

        boolean result =
                authService.authenticate(
                        "nicolas",
                        "ClaveIncorrecta"
                );

        assertFalse(result);
    }
}