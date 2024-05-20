package ar.edu.unq.desapp.grupoD.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ActiveDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ExpressIntentionDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DTOsTests {

    @Mock
    private Validator validator;


    @Test
    void testToActiveDTO() {
        CryptoActive cryptoActive = new CryptoActive();
        cryptoActive.setSymbol("BTC");
        cryptoActive.setAmount(1.0);
        cryptoActive.setPrice(50000f);

        ActiveDTO activeDTO = ActiveDTO.toActiveDTO(cryptoActive, 1000f);

        assertNotNull(activeDTO);
        assertEquals("BTC", activeDTO.getCrypto());
        assertEquals(1.0, activeDTO.getNominalCryptoAmount());
        assertEquals(50000f, activeDTO.getCurrentCryptoPrice());
        assertEquals(50000f * 1000f, activeDTO.getPriceInPesosARG());
    }

    @Test
    void testUserIdNotNull() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        ExpressIntentionDTO dto = new ExpressIntentionDTO();
        dto.setUserId(null);
        dto.setUserId(null);
        Set<ConstraintViolation<ExpressIntentionDTO>> violations = validator.validate(dto);

        assertEquals(2, violations.size());
    }

    @Test
    void testGetUserId() {
        ExpressIntentionDTO dto = new ExpressIntentionDTO();
        dto.setUserId(1L);
        dto.setActiveId(1L);
        dto.setPesosAmount(100);

        assertEquals(1L, dto.getUserId());
    }

    @Test
    void testGetterAndSetter() {
        String symbol = "BTCUSDT";
        Float price = 40000.0f;
        LocalDateTime lastUpdateDateAndTime = LocalDateTime.now();

        BinancePriceDTO binancePriceDTO = new BinancePriceDTO(symbol, price, lastUpdateDateAndTime);

        assertEquals(symbol, binancePriceDTO.getSymbol());
        assertEquals(price, binancePriceDTO.getPrice());
        assertEquals(lastUpdateDateAndTime, binancePriceDTO.getLastUpdateDateAndTime());
    }

    @Test
     void testAllArgsConstructor() {
        String symbol = "BTCUSDT";
        Float price = 40000.0f;
        LocalDateTime lastUpdateDateAndTime = LocalDateTime.now();

        BinancePriceDTO binancePriceDTO = new BinancePriceDTO(symbol, price, lastUpdateDateAndTime);

        // Assert
        assertEquals(symbol, binancePriceDTO.getSymbol());
        assertEquals(price, binancePriceDTO.getPrice());
        assertEquals(lastUpdateDateAndTime, binancePriceDTO.getLastUpdateDateAndTime());
    }

    @Test
     void testJsonIncludeNonNull() {
        String symbol = "BTCUSDT";
        Float price = 40000.0f;
        LocalDateTime lastUpdateDateAndTime = LocalDateTime.now();
        BinancePriceDTO binancePriceDTO = new BinancePriceDTO(symbol, price, lastUpdateDateAndTime);

        assertNotNull(binancePriceDTO.getSymbol());
        assertNotNull(binancePriceDTO.getPrice());
        assertNotNull(binancePriceDTO.getLastUpdateDateAndTime());
    }

    @Test
    void testJsonFormat() throws NoSuchFieldException {
        // Arrange
        String pattern = "dd/MM/yyyy HH:mm";

        // Act
        JsonFormat jsonFormatAnnotation = BinancePriceDTO.class.getDeclaredField("lastUpdateDateAndTime")
                .getAnnotation(JsonFormat.class);

        // Assert
        assertNotNull(jsonFormatAnnotation);
        assertEquals(pattern, jsonFormatAnnotation.pattern());
    }
}
