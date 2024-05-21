package ar.edu.unq.desapp.grupoD.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "users")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @Operation(summary = "Register a user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO.getEmail());
    }

}
