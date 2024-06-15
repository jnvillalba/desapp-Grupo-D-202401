package ar.edu.unq.desapp.grupod.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.JwtDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.LoginDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.security.jwt.JWTTokenHelper;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.authentication.AuthenticationManager;
@Tag(name = "users")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    private final JWTTokenHelper jwtTokenHelper;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder;


    @Operation(summary = "Register a user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO.getEmail());
    }

//    @PostMapping(value = "/login")
//    public ResponseEntity<JwtDTO> login(@RequestBody LoginDTO loginRequest) {
//        JwtDTO jwtDto = userService.loginUser(loginRequest);
//        return ResponseEntity.ok(jwtDto);
//    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> loginUser(@RequestBody LoginDTO request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        String token = jwtTokenHelper.generateToken(request.getEmail());
        return ResponseEntity.ok(new JwtDTO(token, request.getEmail()));
    }
    private void doAuthenticate(String emailId, String password) {

        log.info("Authentication of USer Credentils");

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(emailId, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid UserName and Password");
        }
    }

}
