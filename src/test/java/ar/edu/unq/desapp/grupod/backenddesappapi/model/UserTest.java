package ar.edu.unq.desapp.grupod.backenddesappapi.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    @Autowired
    private Validator validator;
    private User user;

    private Operation operation;
    private List<Operation> operationsList;

    @BeforeEach
    void setUp() {
        user = new User();
        operationsList = new ArrayList<>();
        operation = new Operation();
    }

    @Test
    void testCreateUser() {
        user.setName("Camila");
        user.setLastName("Ruiz");
        user.setEmail("camiruiz@hotmail.com");
        user.setDirection("123 Calle Falsa");
        user.setPassword("Cami123!");
        user.setCvuMercadoPago("0123456789012345678901");
        user.setWalletCrypto("12345678");

        assertEquals("Camila", user.getName());
        assertEquals("Ruiz", user.getLastName());
        assertEquals("camiruiz@hotmail.com", user.getEmail());
        assertEquals("123 Calle Falsa", user.getDirection());
        assertEquals("Cami123!", user.getPassword());
        assertEquals("0123456789012345678901", user.getCvuMercadoPago());
        assertEquals("12345678", user.getWalletCrypto());

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nameMinAndMaxLength() {

        user.setName("Noe");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "name");
        assertTrue(violations.isEmpty());

        user.setName("No");
        violations = validator.validateProperty(user, "name");
        assertFalse(violations.isEmpty());

        user.setName("Nominchuluunukhaanzayamunkherdeneenkhtuguldur");
        violations = validator.validateProperty(user, "name");
        assertFalse(violations.isEmpty());
    }

    @Test
    void lastnameMinAndMaxLength() {

        user.setLastName("Pie");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "lastName");
        assertTrue(violations.isEmpty());

        user.setLastName("Pi");
        violations = validator.validateProperty(user, "lastName");
        assertFalse(violations.isEmpty());
        user.setLastName("Keihanaikukauakahihuliheekahaunaele");
        violations = validator.validateProperty(user, "lastName");
        assertFalse(violations.isEmpty());

    }

    @Test
    void testInvalidEmail() {
        user.setEmail("camiruiz@hotmail.com");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "email");
        assertTrue(violations.isEmpty());

        user.setEmail("invalid_email");
        violations = validator.validateProperty(user, "email");
        assertFalse(violations.isEmpty());
    }

    @Test
    void directionMinAndMaxLength() {

        user.setDirection("Calle Falsa 123");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "direction");
        assertTrue(violations.isEmpty());

        user.setDirection("Calle");
        violations = validator.validateProperty(user, "direction");
        assertFalse(violations.isEmpty());

        user.setDirection("Calle Falsa 12345678901234567890");
        violations = validator.validateProperty(user, "direction");
        assertFalse(violations.isEmpty());
    }
    @Test
    void testCVUMinAndMaxLength() {

        user.setCvuMercadoPago("0123456789012345678901");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "cvuMercadoPago");
        assertTrue(violations.isEmpty());

        user.setCvuMercadoPago("01234567890123456789012");
        violations = validator.validateProperty(user, "cvuMercadoPago");
        assertFalse(violations.isEmpty());

        user.setCvuMercadoPago("012345678901234567890");
        violations = validator.validateProperty(user, "cvuMercadoPago");
        assertFalse(violations.isEmpty());
    }

    @Test
    void testWalletCryptoMinAndMaxLength() {


        user.setWalletCrypto("12345678");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "walletCrypto");
        assertTrue(violations.isEmpty());

        user.setWalletCrypto("123456789");
        violations = validator.validateProperty(user, "walletCrypto");
        assertFalse(violations.isEmpty());

        user.setWalletCrypto("1234567");
        violations = validator.validateProperty(user, "walletCrypto");
        assertFalse(violations.isEmpty());
    }

    @Test
    void testIntentionsListInitialization() {
        assertNotNull(user.getIntentionsList());
        assertTrue(user.getIntentionsList().isEmpty());
    }

    @Test
    void testAddIntentionToList() {
        Intention intention = new Intention();
        user.getIntentionsList().add(intention);
        assertFalse(user.getIntentionsList().isEmpty());
        assertEquals(1, user.getIntentionsList().size());
        assertTrue(user.getIntentionsList().contains(intention));
    }

    @Test
    void testRemoveIntentionFromList() {
        Intention intention = new Intention();
        user.getIntentionsList().add(intention);
        user.getIntentionsList().remove(intention);
        assertTrue(user.getIntentionsList().isEmpty());
    }

    @Test
    void testSetIntentionsList() {
        List<Intention> newIntentionsList = new ArrayList<>();
        Intention intention = new Intention();
        newIntentionsList.add(intention);
        user.setIntentionsList(newIntentionsList);
        assertEquals(newIntentionsList, user.getIntentionsList());
    }

    @Test
    void testEmptyTransactionsList() {
        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }

    @Test
    void testSingleSuccessfulTransaction() {
        Operation mockTransaction = Mockito.mock(Operation.class);
        Mockito.when(mockTransaction.isSuccess()).thenReturn(true);
        operationsList.add(mockTransaction);
        user.setOperationsList(operationsList);
        double reputation = user.getReputation();
        assertEquals(100.0, reputation);
    }

    @Test
    void testSingleFailedTransaction() {
        Operation mockTransaction = Mockito.mock(Operation.class);
        Mockito.when(mockTransaction.isSuccess()).thenReturn(false);
        operationsList.add(mockTransaction);
        user.setOperationsList(operationsList);
        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }

    @Test
    void testMixedTransactions() {
        Operation mockSuccessfulTransaction = Mockito.mock(Operation.class);
        Mockito.when(mockSuccessfulTransaction.isSuccess()).thenReturn(true);

        Operation mockFailedTransaction = Mockito.mock(Operation.class);
        Mockito.when(mockFailedTransaction.isCancelled()).thenReturn(true);

        operationsList.add(mockSuccessfulTransaction);
        operationsList.add(mockFailedTransaction);
        operationsList.add(mockSuccessfulTransaction);
        user.setOperationsList(operationsList);

        double reputation = user.getReputation();
        assertEquals(66.66, reputation, 0.1);
    }

    @Test
    void testNoSuccessfulTransactions() {
        Operation mockFailedTransaction1 = Mockito.mock(Operation.class);
        Mockito.when(mockFailedTransaction1.isSuccess()).thenReturn(false);

        Operation mockFailedTransaction2 = Mockito.mock(Operation.class);
        Mockito.when(mockFailedTransaction2.isSuccess()).thenReturn(false);

        operationsList.add(mockFailedTransaction1);
        operationsList.add(mockFailedTransaction2);
        user.setOperationsList(operationsList);

        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }

    @Test
    void testUserCreation() {

        User john = new User(
                1L,
                "John",
                "Doe",
                "user@example.com",
                "123 Main St",
                "Password123",
                "0123456789012345678901",
                "12345678",
                new ArrayList<>(),
                new ArrayList<>(),
                95.5,
                new ArrayList<>()
        );

        assertEquals(1L, john.getId());
        assertEquals("John", john.getName());
        assertEquals("user@example.com", john.getEmail());

        User user2 = User.builder()
                .id(2L)
                .name("John")
                .lastName("Doe")
                .email("user@example.com")
                .direction("123 Main St")
                .password("Password123")
                .cvuMercadoPago("0123456789012345678901")
                .walletCrypto("12345678")
                .intentionsList(new ArrayList<>())
                .operationsList(new ArrayList<>())
                .reputation(95.5)
                .build();

        assertEquals(2L, user2.getId());
        assertEquals("John", user2.getName());
        assertEquals("user@example.com", user2.getEmail());

    }

    @Test
    void testWhenATransactionIsCancelledAndTheUserHasAnEmptyListOfTransactionsHisReputationIs0Points() {
        operation.setStatus(Operation.TransactionStatus.CANCELED_BY_USER);
        operation.setUser(user);

        user.handleCancelledTransaction(operation);

        assertEquals(0.0, user.findReputation(), 0.01);
    }

    @Test
    void testWhenATransactionIsCancelledTheUserWhoCancelledItHisReputationIsLoweredBy20Points() {
        operation.setStatus(Operation.TransactionStatus.CANCELED_BY_USER);
        operation.setUser(user);
        Operation operationSuccesful = new Operation();
        operationSuccesful.setStatus(Operation.TransactionStatus.CONFIRMED);
        user.getOperationsList().add(operationSuccesful);

        user.handleCancelledTransaction(operation);

        assertFalse(user.getOperationsList().isEmpty());
        assertEquals(80.0, user.findReputation(), 0.01);
    }

    @Test
    void testWhenATransactionIsCancelledTheUsersReputationCantGoBelow0Points() {
        operation.setStatus(Operation.TransactionStatus.CANCELED_BY_USER);
        operation.setUser(user);
        user.getOperationsList().add(operation);
        user.setReputation(5.0);

        user.handleCancelledTransaction(operation);

        assertEquals(0.0, user.findReputation(), 0.01);
    }

    @Test
    void testWhenATransactionIsCancelledBySystemTheUserReputationIsNotAffected() {
        operation.setStatus(Operation.TransactionStatus.CANCELED_BY_SYSTEM);
        operation.setUser(user);

        user.getOperationsList().add(operation);
        user.setReputation(100.0);

        user.handleCancelledTransaction(operation);

        assertEquals(100.0, user.findReputation(), 0.01);
    }

    @Test
    void testWhenATransactionIsSuccessfulTheUserIsAdded5Points() {
        operation.setStatus(Operation.TransactionStatus.CONFIRMED);
        operation.setUser(user);
        operation.setCreatedAt(LocalDateTime.now().minusDays(1));

        Operation operationCancelled = new Operation();
        operationCancelled.setStatus(Operation.TransactionStatus.CANCELED_BY_USER);
        operationCancelled.setUser(user);

        user.getOperationsList().add(operation);
        user.getOperationsList().add(operationCancelled);

        user.handleSuccesfulTransaction(operation);

        assertEquals(55.0, user.findReputation(), 0.01);
    }

    @Test
    void testAUserKnowsHowToHandleASuccesfulTransaction() {
        operation.setStatus(Operation.TransactionStatus.CONFIRMED);
        operation.setUser(user);
        operation.setCreatedAt(LocalDateTime.now());

        double initialReputation = user.findReputation();

        user.processTransaction(operation);

        assertNotEquals(initialReputation, user.findReputation());
    }
}
