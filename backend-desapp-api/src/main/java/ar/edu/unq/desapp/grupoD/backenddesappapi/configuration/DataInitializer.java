package ar.edu.unq.desapp.grupoD.backenddesappapi.configuration;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void initializeData() {
        User user = User.builder()
                .name("Nombre")
                .lastName("Apellido")
                .email("correo@example.com")
                .direction("Dirección")
                .password("Password123!")
                .cvuMercadoPago("CVU1234567890123456789")
                .walletCrypto("12345678")
                .build();
        userRepository.save(user);
    }
}
