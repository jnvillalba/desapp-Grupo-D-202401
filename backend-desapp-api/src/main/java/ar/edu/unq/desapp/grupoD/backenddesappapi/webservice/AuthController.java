package ar.edu.unq.desapp.grupod.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.JwtDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.LoginDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "users")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    @Operation(summary = "Register a user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {

        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> loginUser(@RequestBody LoginDTO loginRequest) {
        JwtDTO jwtDto = userService.loginUser(loginRequest);
        return ResponseEntity.ok(jwtDto);
    }

}
