package ar.edu.unq.desapp.grupoD.backenddesappapi.service;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

   @Mock
   private UserRepository userRepository;

   @InjectMocks
   private UserService userService;

   @Test
   void testRegisterUser() {
      UserDTO userDto = new UserDTO();
      userDto.setName("Joe");
      userDto.setLastName("Doe");
      userDto.setEmail("joe@example.com");
      userDto.setDirection("123 Main St");
      userDto.setPassword("Password123!");
      userDto.setCvuMercadoPago("0123456789012345678901");
      userDto.setWalletCrypto("12345678");

      User user = new User();
      user.setName(userDto.getName());
      user.setLastName(userDto.getLastName());
      user.setEmail(userDto.getEmail());
      user.setDirection(userDto.getDirection());
      user.setPassword(userDto.getPassword());
      user.setCvuMercadoPago(userDto.getCvuMercadoPago());
      user.setWalletCrypto(userDto.getWalletCrypto());

      when(userRepository.save(any(User.class))).thenReturn(user);

      User registeredUser = userService.registerUser(userDto);

      assertEquals("Joe", registeredUser.getName());
      assertEquals("Doe", registeredUser.getLastName());
      assertEquals("joe@example.com", registeredUser.getEmail());
      assertEquals("123 Main St", registeredUser.getDirection());
      assertEquals("12345678", registeredUser.getWalletCrypto());
      assertEquals("0123456789012345678901", registeredUser.getCvuMercadoPago());
      assertEquals("Password123!", registeredUser.getPassword());
   }
}
