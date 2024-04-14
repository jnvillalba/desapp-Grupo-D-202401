package ar.edu.unq.desapp.grupoD.backenddesappapi.model;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class CryptoActiveTest {
    @Autowired
    private Validator validator;

    @Test
    public void testCreateCryptoActive() {
        CryptoActive cryptoActive = new CryptoActive();
        cryptoActive.setSymbol("BTC");
        cryptoActive.setPrice(1000.0f);
        cryptoActive.setLastUpdateDateAndTime("2024-01-01T00:00:00");

        assertEquals("BTC", cryptoActive.getSymbol());
        assertEquals(1000.0f, cryptoActive.getPrice());
        assertEquals("2024-01-01T00:00:00", cryptoActive.getLastUpdateDateAndTime());

        Set<ConstraintViolation<CryptoActive>> violations = validator.validate(cryptoActive);
        assertTrue(violations.isEmpty());
    }
}
