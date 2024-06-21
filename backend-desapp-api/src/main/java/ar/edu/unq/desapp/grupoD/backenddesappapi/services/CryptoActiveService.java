package ar.edu.unq.desapp.grupod.backenddesappapi.services;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.CryptoActiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CryptoActiveService {
    private final CryptoActiveRepository cryptoActiveRepository;

    public CryptoActive getCryptoActive(Long id) {
        return cryptoActiveRepository.findById(id).orElseThrow();
    }
}
