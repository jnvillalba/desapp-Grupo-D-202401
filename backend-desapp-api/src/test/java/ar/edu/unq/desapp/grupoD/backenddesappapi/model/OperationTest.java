package ar.edu.unq.desapp.grupoD.backenddesappapi.model;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class OperationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testNotNullAnnotations() {
        Operation operation = new Operation();
        operation.setUser(null);
        operation.setCryptoActive(null);

        Set<ConstraintViolation<Operation>> violations = validator.validate(operation);

        assertEquals(2, violations.size());
        for (ConstraintViolation<Operation> violation : violations) {
            if ("user".equals(violation.getPropertyPath().toString())) {
                assertEquals("The user cannot be null.", violation.getMessage());
            } else if ("cryptoActive".equals(violation.getPropertyPath().toString())) {
                assertEquals("The crypto active cannot be null.", violation.getMessage());
            }
        }
    }

    @Test
    public void testIsSuccess() {
        Operation operation = new Operation();
        operation.setStatus(Operation.Status.SUCCESSFUL);

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
        operation.setStatus(Operation.Status.SUCCESSFUL);
        assertEquals(OperationType.BUY, operation.getOperationType());
        assertEquals(Operation.Status.SUCCESSFUL, operation.getStatus());
    }

    @Test
    public void testNotNullAnnotationsWithMockito() {
        User mockedUser = Mockito.mock(User.class);
        Operation operation = new Operation();
        operation.setUser(mockedUser);
        operation.setCryptoActive(null);

        Set<ConstraintViolation<Operation>> violations = validator.validate(operation);

        assertEquals(1, violations.size());
        ConstraintViolation<Operation> violation = violations.iterator().next();
        assertEquals("The crypto active cannot be null.", violation.getMessage());
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