package ar.edu.unq.desapp.grupoD.backenddesappapi.service;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
     void testRegisterUser() {
        User user = User.builder()
                .name("John")
                .lastName("Doe")
                .email("user@example.com")
                .direction("123 Main St")
                .password("Password123")
                .cvuMercadoPago("0123456789012345678901")
                .walletCrypto("12345678").build();

      //  when(passwordEncoder.encode("Password123")).thenReturn("encodedPassword");

//        User registeredUser = userService.registerUser(user);
//
//        assertEquals("John", registeredUser.getName());
//        assertEquals("Doe", registeredUser.getLastName());
//        assertEquals("user@example.com", registeredUser.getEmail());
//        assertEquals("123 Main St", registeredUser.getDirection());
//        assertEquals("12345678", registeredUser.getWalletCrypto());
//        assertEquals("0123456789012345678901", registeredUser.getCvuMercadoPago());
//        assertEquals("encodedPassword", registeredUser.getPassword());

    //    verify(passwordEncoder, times(1)).encode("Password123");
    }
}
