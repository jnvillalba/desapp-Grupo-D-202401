package ar.edu.unq.desapp.grupod.backenddesappapi.service;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.ExpressIntentionDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.CryptoActiveRepository;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.IntentionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.cache.CacheManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class IntentionServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IntentionService intentionService;
    @Autowired
    private CryptoActiveRepository cryptoActiveRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void clearCache() {
        cacheManager.getCache("cryptoCache").clear();
    }

    @Test
    void expressIntention() {
        CryptoActive cryptoActive = cryptoActiveRepository.findBySymbol("BTCUSDT");
        User user = userRepository.findByEmail("John@example.com");
        ExpressIntentionDTO input = new ExpressIntentionDTO();
        input.setActiveId(cryptoActive.getActiveId());
        input.setOperationType(OperationType.BUY);
        input.setPesosAmount(1);
        Intention intention = intentionService.expressIntention(user, input);
        assertEquals(user, intention.getUser());
        assertEquals(cryptoActive, intention.getCryptoActive());
        assertEquals(1, intention.getPesosAmount());
        assertNotNull(intention.getCreationDateTime());
        assertNotNull(intention.getIntentionId());
    }

    @Test
    void getAllIntentionsWithTheSameCryptoActive() {
        CryptoActive cryptoActive = cryptoActiveRepository.findBySymbol("BTCUSDT");
        User user = userRepository.findByEmail("John@example.com");

        ExpressIntentionDTO input = new ExpressIntentionDTO();
        input.setActiveId(cryptoActive.getActiveId());
        input.setOperationType(OperationType.BUY);
        input.setPesosAmount(1);
        intentionService.expressIntention(user, input);

        ExpressIntentionDTO input2 = new ExpressIntentionDTO();
        input2.setActiveId(cryptoActive.getActiveId());
        input2.setOperationType(OperationType.SELL);
        input2.setPesosAmount(1);
        intentionService.expressIntention(user, input2);

        List<IntentionDTO> intentions = intentionService.getAllIntentions();

        assertEquals(4, intentions.size());
    }

    @Test
    void getAllIntentionsWithDifferentsCryptoActives() {
        CryptoActive cryptoActive = cryptoActiveRepository.findBySymbol("BTCUSDT");
        CryptoActive cryptoActive2 = cryptoActiveRepository.findBySymbol("DOGEUSDT");

        User user2 = userRepository.findByEmail("Jane@example.com");

        ExpressIntentionDTO input = new ExpressIntentionDTO();
        input.setActiveId(cryptoActive.getActiveId());
        input.setOperationType(OperationType.BUY);
        input.setPesosAmount(1);
        intentionService.expressIntention(user2, input);

        ExpressIntentionDTO input2 = new ExpressIntentionDTO();
        input2.setActiveId(cryptoActive2.getActiveId());
        input2.setOperationType(OperationType.SELL);
        input2.setPesosAmount(1);
        intentionService.expressIntention(user2, input2);

        List<IntentionDTO> intentions = intentionService.getAllIntentions();

        assertEquals(4, intentions.size());
    }
}