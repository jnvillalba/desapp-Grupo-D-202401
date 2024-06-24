package ar.edu.unq.desapp.grupod.backenddesappapi.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IntentionTest {
    private Validator validator;

    @Mock
    private User mockUser;

    @Mock
    private CryptoActive mockCryptoActive;

    private Intention intention;

    @BeforeEach
    void setup() {
        intention = new Intention();
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    @Test
    void testNullUser() {
        intention.setOperationType(OperationType.BUY);
        intention.setCryptoActive(mockCryptoActive);
        intention.setPesosAmount(100.0);

        assertFalse(validator.validate(intention).isEmpty());
    }

    @Test
    void testNegativePesosAmount() {
        Intention intention = new Intention();
        intention.setUser(mockUser);
        intention.setOperationType(OperationType.BUY);
        intention.setCryptoActive(mockCryptoActive);
        intention.setPesosAmount(-100.0);

        assertFalse(validator.validate(intention).isEmpty());
    }

    @Test
    void testGettersAndSetters() {
        intention.setIntentionId(1L);
        intention.setCreationDateTime(LocalDateTime.now());
        intention.setUser(mockUser);
        intention.setOperationType(OperationType.BUY);
        intention.setCryptoActive(mockCryptoActive);
        intention.setPesosAmount(100.0);

        assertEquals(1L, intention.getIntentionId());
        assertNotNull(intention.getCreationDateTime());
        assertEquals(mockUser, intention.getUser());
        assertEquals(OperationType.BUY, intention.getOperationType());
        assertEquals(mockCryptoActive, intention.getCryptoActive());
        assertEquals(100.0, intention.getPesosAmount());
    }

    @Test
    void testNullValues() {
        assertNull(intention.getIntentionId());
        assertNull(intention.getCreationDateTime());
        assertNull(intention.getUser());
        assertNull(intention.getOperationType());
        assertNull(intention.getCryptoActive());
        assertEquals(0.0, intention.getPesosAmount());
    }

    @Test
    void testNullCryptoActive() {

        intention.setUser(mockUser);
        intention.setOperationType(OperationType.BUY);
        intention.setPesosAmount(100.0);

        assertFalse(validator.validate(intention).isEmpty());
    }

    @Test
    void testNullOperationType() {
        intention.setUser(mockUser);
        intention.setCryptoActive(mockCryptoActive);
        intention.setPesosAmount(100.0);

        assertTrue(validator.validate(intention).isEmpty());
    }

    @Test
    void testNullPesosAmount() {
        intention.setUser(mockUser);
        intention.setOperationType(OperationType.BUY);
        intention.setCryptoActive(mockCryptoActive);

        assertTrue(validator.validate(intention).isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "BUY, true, true, 100.0",
            "BUY, true, true, 0.0"
    })
    void testIntentionValidation(OperationType operationType, boolean hasUser, boolean hasCryptoActive, double pesosAmount) {
        if (hasUser) {
            intention.setUser(mockUser);
        } else {
            intention.setUser(null);
        }

        if (hasCryptoActive) {
            intention.setCryptoActive(mockCryptoActive);
        } else {
            intention.setCryptoActive(null);
        }

        intention.setOperationType(operationType);
        intention.setPesosAmount(pesosAmount);

        assertTrue(validator.validate(intention).isEmpty());
    }
}