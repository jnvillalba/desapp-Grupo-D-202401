package ar.edu.unq.desapp.grupoD.backenddesappapi.service;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ExpressIntentionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.CryptoActiveRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.IntentionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Test
    void expressIntention() {
        User user = userRepository.save(User.builder()
                .name("John")
                .lastName("Doe")
                .email("user@example.com")
                .direction("123 Main St")
                .password("Password123!")
                .cvuMercadoPago("0123456789012345678901")
                .walletCrypto("12345678").build());

        CryptoActive cryptoActive = cryptoActiveRepository.save(CryptoActive.builder()
                .symbol("BTC")
                .price(0.1f)
                .amount(1)
                .lastUpdateDateAndTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)).build());

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
        //TODO hacer setups para tests
        User user = userRepository.save(User.builder()
                .name("John")
                .lastName("Doe")
                .email("user@example.com")
                .direction("123 Main St")
                .password("Password123!")
                .cvuMercadoPago("0123456789012345678901")
                .walletCrypto("12345678").build());

        CryptoActive cryptoActive = cryptoActiveRepository.save(CryptoActive.builder()
                .symbol("BTC")
                .price(0.1f)
                .amount(1)
                .lastUpdateDateAndTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)).build());

        ExpressIntentionDTO input = new ExpressIntentionDTO();
        input.setActiveId(cryptoActive.getActiveId());
        input.setOperationType(OperationType.BUY);
        input.setPesosAmount(1);
        Intention intention = intentionService.expressIntention(user, input);

        ExpressIntentionDTO input2 = new ExpressIntentionDTO();
        input2.setActiveId(cryptoActive.getActiveId());
        input2.setOperationType(OperationType.SELL);
        input2.setPesosAmount(1);
        Intention intention2 = intentionService.expressIntention(user, input2);

        List<Intention> intentions = intentionService.getAllIntentions();

        assertEquals(2, intentions.size());
    }

    @Test
    void getAllIntentionsWithDifferentsCryptoActives() {
        //TODO hacer setups para tests
        User user2 = userRepository.save(User.builder()
                .name("Jane")
                .lastName("Dam")
                .email("user2@example.com")
                .direction("123 Street")
                .password("Password123!")
                .cvuMercadoPago("0123456789012345678902")
                .walletCrypto("87654321").build());

        CryptoActive cryptoActive = cryptoActiveRepository.save(CryptoActive.builder()
                .symbol("BTC")
                .price(0.1f)
                .amount(1)
                .lastUpdateDateAndTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)).build());

        CryptoActive cryptoActive2 = cryptoActiveRepository.save(CryptoActive.builder()
                .symbol("USD")
                .price(0.1f)
                .amount(1)
                .lastUpdateDateAndTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)).build());

        ExpressIntentionDTO input = new ExpressIntentionDTO();
        input.setActiveId(cryptoActive.getActiveId());
        input.setOperationType(OperationType.BUY);
        input.setPesosAmount(1);
        Intention intention = intentionService.expressIntention(user2, input);

        ExpressIntentionDTO input2 = new ExpressIntentionDTO();
        input2.setActiveId(cryptoActive2.getActiveId());
        input2.setOperationType(OperationType.SELL);
        input2.setPesosAmount(1);
        Intention intention2 = intentionService.expressIntention(user2, input2);

        List<Intention> intentions = intentionService.getAllIntentions();

        assertEquals(2, intentions.size());
    }
}