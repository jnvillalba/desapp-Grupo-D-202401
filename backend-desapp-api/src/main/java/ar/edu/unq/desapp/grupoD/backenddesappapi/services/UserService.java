package ar.edu.unq.desapp.grupoD.backenddesappapi.services;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(UserDTO userDTO) {
        User user = userDTO.toModel();
        return userRepository.save(user);
    }
}
