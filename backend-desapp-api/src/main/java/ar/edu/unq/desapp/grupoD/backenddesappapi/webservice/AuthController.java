package ar.edu.unq.desapp.grupoD.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    private final UserRepository userRepository;
    @Operation(summary = "Register account")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO.toString());
    }

    //TODO: borrar
    @GetMapping("/c")
    public ResponseEntity<String> c() {
        User user = User.builder()
                .name("Nombre")
                .lastName("Apellido")
                .email("correo@example.com")
                .direction("CalleFalsaAAAAAAAAAA")
                .password("Password123!")
                .cvuMercadoPago("CVU1234567890123456789")
                .walletCrypto("12345678")
                .build();
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.toString());
    }
}
