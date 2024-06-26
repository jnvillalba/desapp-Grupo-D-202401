package ar.edu.unq.desapp.grupod.backenddesappapi.services;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.ExpressIntentionDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.IntentionRepository;
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
        user.addIntention(intention);
        return intentionRepository.save(intention);
    }

    public List<IntentionDTO> getAllIntentions() {
        List<Intention> intentionList = intentionRepository.findAll();
        return intentionList.stream()
                .map(IntentionDTO::toDto).toList();
    }
}
