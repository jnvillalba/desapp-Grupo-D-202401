package ar.edu.unq.desapp.grupod.backenddesappapi.dto;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DTOsTests {

    @Mock
    private Validator validator;

    @InjectMocks
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        loginDTO = new LoginDTO();
    }

    @Test
    void whenEmailIsNull_thenValidationFails() {
        loginDTO.setEmail(null);
        loginDTO.setPassword("Password1!");

        ConstraintViolation<LoginDTO> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("must not be blank");
        when(validator.validate(any(LoginDTO.class))).thenReturn(Collections.singleton(violation));

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailIsInvalid_thenValidationFails() {
        loginDTO.setEmail("invalid-email");
        loginDTO.setPassword("Password1!");

        ConstraintViolation<LoginDTO> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("must be a well-formed email address");
        when(validator.validate(any(LoginDTO.class))).thenReturn(Collections.singleton(violation));

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
        assertEquals(1, violations.size());
        assertEquals("must be a well-formed email address", violations.iterator().next().getMessage());
    }

    @Test
    void whenPasswordIsNull_thenValidationFails() {
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword(null);

        ConstraintViolation<LoginDTO> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("must not be blank");
        when(validator.validate(any(LoginDTO.class))).thenReturn(Collections.singleton(violation));

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenPasswordIsInvalid_thenValidationFails() {
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("invalid");

        ConstraintViolation<LoginDTO> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("must match \"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$\"");
        when(validator.validate(any(LoginDTO.class))).thenReturn(Collections.singleton(violation));

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
        assertEquals(1, violations.size());
        assertEquals("must match \"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$\"", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailAndPasswordAreValid_thenValidationSucceeds() {
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("Password1!");

        when(validator.validate(any(LoginDTO.class))).thenReturn(Collections.emptySet());

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
        assertTrue(violations.isEmpty());
    }

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


    @Test
    void testJwtDTOAllArgsConstructor() {
        String token = "testToken";
        String email = "test@example.com";
        List<GrantedAuthority> authorities = Collections.singletonList(mock(GrantedAuthority.class));

        JwtDTO jwtDTO = new JwtDTO(token, email, authorities);

        assertEquals(token, jwtDTO.getToken());
        assertEquals(email, jwtDTO.getEmail());
        assertEquals(authorities, jwtDTO.getAuthorities());
    }

    @Test
    public void testJwtDTOGettersAndSetters() {
        String token = "testToken";
        String email = "test@example.com";
        Collection<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

        JwtDTO jwtDTO = new JwtDTO(token, email, authorities);

        jwtDTO.setToken(token);
        jwtDTO.setEmail(email);
        jwtDTO.setAuthorities(authorities);

        assertEquals(token, jwtDTO.getToken());
        assertEquals(email, jwtDTO.getEmail());
        assertEquals(authorities, jwtDTO.getAuthorities());
    }

    @Test
    public void testFormattedCreationDateTime() {
        LocalDateTime creationDateTime = LocalDateTime.of(2024, 6, 24, 10, 30, 0);
        IntentionDTO dto = new IntentionDTO();
        dto.setCreationDateTime(creationDateTime);

        String expectedFormattedDateTime = "2024-06-24T10:30:00";
        assertEquals(expectedFormattedDateTime, dto.getFormattedCreationDateTime());
    }

    @Test
    void testEmailGetter() {
        String email = "test@example.com";
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);

        assertEquals(email, loginDTO.getEmail());
    }

    @Test
    void testPasswordGetter() {
        String password = "Password1!";
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword(password);

        assertEquals(password, loginDTO.getPassword());
    }

    @Test
    void testValidLoginDTO() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("Password1!");

        var violations = validator.validate(loginDTO);

        assertTrue(violations.isEmpty());
    }
}



