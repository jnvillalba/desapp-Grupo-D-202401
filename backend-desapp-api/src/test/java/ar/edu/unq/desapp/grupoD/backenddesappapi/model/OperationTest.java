package ar.edu.unq.desapp.grupoD.backenddesappapi.model;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class OperationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testNotNullAnnotations() {
        Operation operation = new Operation();
        operation.setOperationId(1);
        operation.setAddress("123 Main St");
        operation.setOperationAmount(100.0);
        operation.setStatus(Operation.TransactionStatus.PENDING);


        User mockUser = Mockito.mock(User.class);
        CryptoActive mockCryptoActive = Mockito.mock(CryptoActive.class);
        operation.setUser(mockUser);
        operation.setCryptoActive(mockCryptoActive);

        Set<ConstraintViolation<Operation>> violations = validator.validate(operation);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid Operation object");
    }

    @Test
    void testInvalidAddress() {
        Operation operation = new Operation();
        operation.setOperationId(2);
        operation.setAddress("");

        Set<ConstraintViolation<Operation>> violations = validator.validate(operation);
        assertFalse(violations.isEmpty(), "Empty address should result in a validation error");
    }

    @Test
    void testNegativeOperationAmount() {
        Operation operation = new Operation();
        operation.setOperationId(3);
        operation.setAddress("456 Elm St");
        operation.setOperationAmount(-50.0);

        Set<ConstraintViolation<Operation>> violations = validator.validate(operation);
        assertFalse(violations.isEmpty(), "Negative operation amount should result in a validation error");
    }



    @Test
    void testIsCancelledByUser() {
        Operation operation = new Operation();
        operation.setStatus(Operation.TransactionStatus.CANCELED_BY_USER);

        assertTrue(operation.isCancelledByUser(), "Operation should be cancelled by user");
    }

    @Test
    void testIsCancelledBySystem() {
        Operation operation = new Operation();
        operation.setStatus(Operation.TransactionStatus.CANCELED_BY_SYSTEM);

        assertTrue(operation.isCancelledBySystem(), "Operation should be cancelled by system");
    }

    @Test
    void testWasWithin30Minutes() {
        Operation operation = new Operation();
        operation.setCreatedAt(LocalDateTime.now().minusMinutes(29));

        assertTrue(operation.wasWithin30Minutes(), "Operation should have been created within the last 30 minutes");
    }
    @Test
    public void testIsSuccess() {
        Operation operation = new Operation();
        operation.setStatus(Operation.TransactionStatus.CONFIRMED);

        boolean success = operation.isSuccess();

        assertTrue(success);
    }

    @Test
    public void testOperationIdNotNull() {
        Operation operation = new Operation();
        operation.setOperationId(1);

        assertEquals(1, operation.getOperationId());
    }

    @Test
    public void testOperationTypeNotNull() {
        Operation operation = new Operation();
        operation.setOperationType(OperationType.BUY);
        operation.setStatus(Operation.TransactionStatus.CONFIRMED);
        assertEquals(OperationType.BUY, operation.getOperationType());
        assertEquals(Operation.TransactionStatus.CONFIRMED, operation.getStatus());
    }

    @Test
    public void testUser() {
        Operation operation = new Operation();
        User userMock = mock(User.class);
        operation.setUser(userMock);
        assertEquals(userMock, operation.getUser());
    }

    @Test
    public void testCriptoActive() {
        Operation operation = new Operation();
        CryptoActive cryptoActive = mock(CryptoActive.class);
        operation.setCryptoActive(cryptoActive);
        assertEquals(cryptoActive, operation.getCryptoActive());
    }
}