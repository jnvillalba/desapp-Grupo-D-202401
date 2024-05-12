package ar.edu.unq.desapp.grupoD.backenddesappapi.services;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public User registerUser(UserDTO userDTO) {
        User user = userDTO.toModel();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUser(){
        return userRepository.findAll().stream().findFirst().orElseThrow();
    }
}
