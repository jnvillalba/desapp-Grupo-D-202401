package ar.edu.unq.desapp.grupoD.backenddesappapi.model;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TransactionTest {

    @Test
    public void testId() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        assertEquals(1L, transaction.getId());
    }

    @Test
    public void testCryptoActive() {
        Transaction transaction = new Transaction();
        CryptoActive cryptoActiveMock = mock(CryptoActive.class);
        transaction.setCriptoActive(cryptoActiveMock);
        assertEquals(cryptoActiveMock, transaction.getCriptoActive());
    }

    @Test
    public void testOperationType() {
        Transaction transaction = new Transaction();
        OperationType operationTypeMock = mock(OperationType.class);
        transaction.setOperationType(operationTypeMock);
        assertEquals(operationTypeMock, transaction.getOperationType());
    }

    @Test
    public void testOperationAmount() {
        Transaction transaction = new Transaction();
        transaction.setOperationAmount(100.0);
        assertEquals(100.0, transaction.getOperationAmount());
    }

    @Test
    public void testUser() {
        Transaction transaction = new Transaction();
        User userMock = mock(User.class);
        transaction.setUser(userMock);
        assertEquals(userMock, transaction.getUser());
    }

    @Test
    public void testAddress() {
        Transaction transaction = new Transaction();
        transaction.setAddress("0123456789012345678901");
        assertEquals("0123456789012345678901", transaction.getAddress());
    }

    @Test
    public void testCVU() {
        Transaction transaction = new Transaction();
        transaction.setCvu("1234567890123456");
        assertEquals("1234567890123456", transaction.getCvu());
    }

    @Test
    public void testStatus() {
        Transaction transaction = new Transaction();
        transaction.setStatus(Transaction.TransactionStatus.PENDING);
        assertEquals(Transaction.TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    public void testValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Transaction transaction = new Transaction();
        transaction.setOperationAmount(-100.0);
        transaction.setAddress("");
        var violations = validator.validate(transaction);
        assertEquals(2, violations.size());
    }
}
