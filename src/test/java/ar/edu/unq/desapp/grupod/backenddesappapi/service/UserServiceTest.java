package ar.edu.unq.desapp.grupod.backenddesappapi.service;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.OperationReportDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.RequestReportDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupod.backenddesappapi.security.jwt.JWTTokenHelper;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.CustomUserDetailsService;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

   @Mock
   private UserRepository userRepository;

   @InjectMocks
   private UserService userService;

   @InjectMocks
   private CustomUserDetailsService customUserDetailsService;

   @Mock
   private JWTTokenHelper jwtTokenHelper;

   @Mock
   private BCryptPasswordEncoder passwordEncoder;

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
      String encodedPassword = "$2a$10$yourHashedPassword";
      when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);
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

   @Test
   void testGetUser() {
      Long userId = 1L;
      User user = new User();
      user.setId(userId);
      user.setName("John");

      when(userRepository.findById(userId)).thenReturn(Optional.of(user));

      User retrievedUser = userService.getUser(userId);

      assertEquals(userId, retrievedUser.getId());
      assertEquals("John", retrievedUser.getName());
   }
   @Test
   void testGenerateReport() {
      RequestReportDTO requestReportDTO = new RequestReportDTO();
      requestReportDTO.setUserId(1L);
      requestReportDTO.setStartDate(LocalDate.now().minusDays(7));
      requestReportDTO.setEndDate(LocalDate.now());
      requestReportDTO.setDolarBlue(100f);

      when(userRepository.findOperationsByUserIdAndDateRange(
              requestReportDTO.getUserId(),
              requestReportDTO.getStartDate().atStartOfDay(),
              requestReportDTO.getEndDate().atStartOfDay()))
              .thenReturn(new ArrayList<>());

      OperationReportDTO operationReportDTO = userService.generateReport(requestReportDTO);

      assertEquals(LocalDateTime.class, operationReportDTO.getRequestDateTime().getClass());
      assertEquals(0.0, operationReportDTO.getTotalValueInDollars());
      assertEquals(0.0, operationReportDTO.getTotalValueInPesosARG());
      assertEquals(0, operationReportDTO.getActives().size());
   }

   @Test
   void testGetAllUsers() {
      User user1 = new User();
      user1.setName("Alice");
      user1.setLastName("Smith");

      User user2 = new User();
      user2.setName("Bob");
      user2.setLastName("Brown");

      List<User> mockUsers = Arrays.asList(user1, user2);

      when(userRepository.findAll()).thenReturn(mockUsers);

      List<UserDTO> userDTOList = userService.getAllUsers();

      assertEquals(2, userDTOList.size());
      assertEquals("Alice", userDTOList.get(0).getName());
      assertEquals("Smith", userDTOList.get(0).getLastName());
      assertEquals("Bob", userDTOList.get(1).getName());
      assertEquals("Brown", userDTOList.get(1).getLastName());
   }
}
