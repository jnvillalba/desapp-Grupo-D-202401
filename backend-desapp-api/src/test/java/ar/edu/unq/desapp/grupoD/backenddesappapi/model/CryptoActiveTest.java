package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CryptoActiveTest {

    @Mock
    private Validator validator;

    public CryptoActiveTest() {
        MockitoAnnotations.initMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCryptoActive() {
        CryptoActive cryptoActive = CryptoActive.builder()
                .symbol("BTC")
                .price(50000f)
                .amount(0.5)
                .lastUpdateDateAndTime(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<CryptoActive>> violations = validator.validate(cryptoActive);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidSymbol() {
        CryptoActive cryptoActive = CryptoActive.builder()
                .symbol(null)
                .price(50000f)
                .amount(0.5)
                .lastUpdateDateAndTime(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<CryptoActive>> violations = validator.validate(cryptoActive);
        assertFalse(violations.isEmpty());
        assertEquals("Crypto symbol cannot be null.", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativePrice() {
        CryptoActive cryptoActive = CryptoActive.builder()
                .symbol("BTC")
                .price(-50000f)
                .amount(0.5)
                .lastUpdateDateAndTime(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<CryptoActive>> violations = validator.validate(cryptoActive);
        assertFalse(violations.isEmpty());
        assertEquals("Cripto price cannot be negative.", violations.iterator().next().getMessage());
    }

    @Test
    void testGettersAndSetters() {
        CryptoActive cryptoActive = new CryptoActive();
        cryptoActive.setSymbol("BTC");
        cryptoActive.setPrice(50000f);
        cryptoActive.setAmount(0.5);
        cryptoActive.setLastUpdateDateAndTime(LocalDateTime.now());

        assertEquals("BTC", cryptoActive.getSymbol());
        assertEquals(50000f, cryptoActive.getPrice());
        assertEquals(0.5, cryptoActive.getAmount());
        assertNotNull(cryptoActive.getLastUpdateDateAndTime());
    }
}
