package ar.edu.unq.desapp.grupoD.backenddesappapi.services;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ExpressIntentionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.IntentionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class IntentionService {
    private final IntentionRepository intentionRepository;
    private final CryptoActiveService cryptoActiveService;

    public Intention expressIntention(User user, ExpressIntentionDTO expressIntentionDTO) {
        CryptoActive active = cryptoActiveService.getCryptoActive(expressIntentionDTO.getActiveId());
        Intention intention = new Intention();
        intention.setUser(user);
        intention.setCreationDateTime(LocalDateTime.now());
        intention.setOperationType(expressIntentionDTO.getOperationType());
        intention.setPesosAmount(expressIntentionDTO.getPesosAmount());
        intention.setCryptoActive(active);
        return intentionRepository.save(intention);
    }

    public List<Intention> getAllIntentions(){
        return intentionRepository.findAll();
    }
}
