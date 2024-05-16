package ar.edu.unq.desapp.grupoD.backenddesappapi.services;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.CryptoActiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CryptoActiveService {
    private final CryptoActiveRepository cryptoActiveRepository;

    public CryptoActive getCryptoActive(Long id){
        return cryptoActiveRepository.findById(id).orElseThrow();
    }
}
