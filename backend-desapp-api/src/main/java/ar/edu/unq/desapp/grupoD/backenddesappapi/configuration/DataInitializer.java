package ar.edu.unq.desapp.grupoD.backenddesappapi.configuration;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.CryptoActiveRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.IntentionRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.OperationRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
public class DataInitializer {

    @Bean
    public ApplicationRunner initializer(UserRepository userRepository,
                                         CryptoActiveRepository cryptoActiveRepository,
                                         IntentionRepository intentionRepository,
                                         OperationRepository operationRepository) {
        return args -> {
            clearDatabase(userRepository, cryptoActiveRepository,intentionRepository,operationRepository);
            initializeUsers(userRepository);
            initializeCryptoActives(cryptoActiveRepository);
            initializeOperations(operationRepository, userRepository, cryptoActiveRepository);
            initializeIntentions(intentionRepository, userRepository, cryptoActiveRepository);
        };
    }

    private void clearDatabase(UserRepository userRepository,
                               CryptoActiveRepository cryptoActiveRepository,
                               IntentionRepository intentionRepository,
                               OperationRepository operationRepository) {
        userRepository.deleteAll();
        cryptoActiveRepository.deleteAll();
        intentionRepository.deleteAll();
        operationRepository.deleteAll();
    }

    private void initializeUsers(UserRepository userRepository) {
        User user1 = new User(
                1L,
                "John",
                "Doe",
                "John@example.com",
                "123 Calle Falsa",
                "Password$123",
                "0123456789012345678901",
                "0x123456",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                100
        );

        User user2 = new User(
                2L,
                "Jane",
                "Does",
                "Jane@example.com",
                "123 Calle Falsa",
                "Password$123",
                "0101234567890123456789",
                "0x654321",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0
        );

        userRepository.save(user1);
        userRepository.save(user2);
    }

    private void initializeCryptoActives(CryptoActiveRepository cryptoActiveRepository) {
        CryptoActive btc = CryptoActive.builder()
                .symbol("BTCUSDT")
                .price(50000f)
                .amount(0.5)
                .lastUpdateDateAndTime(LocalDateTime.now())
                .build();
        CryptoActive usdt = CryptoActive.builder()
                .symbol("ETHUSDT")
                .price(1f)
                .amount(500)
                .lastUpdateDateAndTime(LocalDateTime.now())
                .build();
        CryptoActive doge = CryptoActive.builder()
                .symbol("DOGEUSDT")
                .price(0.00002458f)
                .amount(5000)
                .lastUpdateDateAndTime(LocalDateTime.now())
                .build();

        cryptoActiveRepository.save(btc);
        cryptoActiveRepository.save(usdt);
        cryptoActiveRepository.save(doge);
    }


    private void initializeOperations(OperationRepository operationRepository,
                                      UserRepository userRepository,
                                      CryptoActiveRepository cryptoActiveRepository
    ) {
        Operation operation1 = new Operation();
        operation1.setOperationId(1L);
        operation1.setStatus(Operation.TransactionStatus.PENDING);
        operation1.setCreatedAt(LocalDateTime.now());
        CryptoActive btc = cryptoActiveRepository.findBySymbol("BTCUSDT");
        operation1.setCryptoActive(btc);
        User user = userRepository.findByEmail("John@example.com");
        operation1.setAddress(user.getWalletCrypto());
        operation1.setUser(user);
        operation1.setOperationType(OperationType.BUY);
        operation1.setCvu(user.getCvuMercadoPago());
        operation1.setOperationAmount(0.0001);

        Operation operation2 = new Operation();
        operation2.setOperationId(2L);
        operation2.setStatus(Operation.TransactionStatus.PENDING);
        operation2.setCreatedAt(LocalDateTime.now());
        operation2.setUser(user);
        operation2.setAddress(user.getWalletCrypto());
        operation2.setCvu(user.getCvuMercadoPago());
        operation2.setOperationType(OperationType.SELL);
        operation2.setOperationAmount(1000.0);

        CryptoActive doge = cryptoActiveRepository.findBySymbol("DOGEUSDT");
        operation2.setCryptoActive(doge);

        operationRepository.save(operation1);
        operationRepository.save(operation2);
    }

    private void initializeIntentions(IntentionRepository intentionRepository, UserRepository userRepository, CryptoActiveRepository cryptoActiveRepository) {
        Intention intention = new Intention();
        User user = userRepository.findByEmail("John@example.com");
        intention.setUser(user);
        intention.setCreationDateTime(LocalDateTime.now());
        intention.setOperationType(OperationType.BUY);
        intention.setPesosAmount(1);
        CryptoActive btc = cryptoActiveRepository.findBySymbol("BTCUSDT");
        intention.setCryptoActive(btc);
        intentionRepository.save(intention);
    }
}
