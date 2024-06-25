package ar.edu.unq.desapp.grupod.backenddesappapi.service;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.Role;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        String email = "Jane@example.com";
        User user2 = new User(
                2L,
                "Jane",
                "Does",
                "Jane@example.com",
                "123 Calle Falsa",
                "Password$123",
                "0101234567890123456789",
                "0x654321",
                new ArrayList<>(),
                new ArrayList<>(),
                0,
                Collections.singletonList(Role.USER)
        );

        when(userRepository.findByEmail(email)).thenReturn(user2);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        verify(userRepository, times(1)).findByEmail(email);

        assertEquals(email, userDetails.getUsername());
        assertEquals("Password$123", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        try {
            customUserDetailsService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            assertEquals("User not found with email: " + email, e.getMessage());
        }

        verify(userRepository, times(1)).findByEmail(email);
    }
}
