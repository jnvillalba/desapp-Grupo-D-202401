package ar.edu.unq.desapp.grupoD.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.*;
import com.fasterxml.jackson.annotation.JsonFormat;
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
        binancePriceDTO.setSymbol(symbol);
        binancePriceDTO.setPrice(price);
        binancePriceDTO.setLastUpdateDateAndTime(lastUpdateDateAndTime);

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
        String pattern = "dd/MM/yyyy HH:mm";

        JsonFormat jsonFormatAnnotation = BinancePriceDTO.class.getDeclaredField("lastUpdateDateAndTime")
                .getAnnotation(JsonFormat.class);

        assertNotNull(jsonFormatAnnotation);
        assertEquals(pattern, jsonFormatAnnotation.pattern());
    }

    @Test
    void testProcessAccionDescription() {
        assertEquals("Realize the transfer", ProcessTransactionDTO.ProcessAccion.TRANSFER.getDescription());
        assertEquals("Confirm reception", ProcessTransactionDTO.ProcessAccion.CONFIRM.getDescription());
        assertEquals("Cancel", ProcessTransactionDTO.ProcessAccion.CANCEL.getDescription());
    }

    @Test
    void testToDtoConversion() {
        Intention intention = new Intention();
        intention.setIntentionId(1L);
        intention.setCreationDateTime(LocalDateTime.now());
        User user = new User();
        user.setId(100L);
        intention.setUser(user);
        intention.setOperationType(OperationType.BUY);
        CryptoActive cryptoActive = new CryptoActive();
        cryptoActive.setActiveId(200L);
        intention.setCryptoActive(cryptoActive);
        intention.setPesosAmount(500.0);

        IntentionDTO dto = IntentionDTO.toDto(intention);

        assertEquals(intention.getIntentionId(), dto.getIntentionId());
        assertEquals(intention.getCreationDateTime(), dto.getCreationDateTime());
        assertEquals(user.getId(), dto.getUserId());
        assertEquals(intention.getOperationType(), dto.getOperationType());
        assertEquals(cryptoActive.getActiveId(), dto.getCryptoActiveId());
        assertEquals(intention.getPesosAmount(), dto.getPesosAmount());
    }

    @Test
    void testGetFormattedCreationDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2022, 10, 15, 12, 30);
        IntentionDTO dto = new IntentionDTO();
        dto.setCreationDateTime(dateTime);

        String formattedDateTime = dto.getFormattedCreationDateTime();

        assertEquals("2022-10-15T12:30:00", formattedDateTime);
    }
}
