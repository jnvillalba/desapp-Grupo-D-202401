package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {
    @Autowired
    private Validator validator;
    private User user;
    private List<Operation> operationsList;
    private List<Transaction> transactionsList;
    private Transaction transaction;
    private Transaction transactionSuccesful;
    @BeforeEach
    void setUp() {
        user = new User();
        operationsList = new ArrayList<>();
        transactionsList = new ArrayList<>();
        transaction = new Transaction();
        transactionSuccesful = new Transaction();
    }

    @Test
    public void testCreateUser() {
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
    public void nameMinAndMaxLength() {

        user.setName("Noe");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "name");
        assertTrue(violations.isEmpty());
        //cumple con el minimo de 3

        user.setName("No");
        violations = validator.validateProperty(user, "name");
        assertFalse(violations.isEmpty());
        //no cumple con el minimo de 3

        user.setName("Nominchuluunukhaanzayamunkherdeneenkhtuguldur");
        violations = validator.validateProperty(user, "name");
        assertFalse(violations.isEmpty());
        //se pasa del maximo de 30
    }

    @Test
    public void lastnameMinAndMaxLength() {

        user.setLastName("Pie");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "lastName");
        assertTrue(violations.isEmpty());
        //cumple con el minimo de 3

        user.setLastName("Pi");
        violations = validator.validateProperty(user, "lastName");
        assertFalse(violations.isEmpty());
        //no cumple con el minimo de 3

        user.setLastName("Keihanaikukauakahihuliheekahaunaele");
        violations = validator.validateProperty(user, "lastName");
        assertFalse(violations.isEmpty());
        //se pasa del maximo de 30

    }

    @Test
    public void testInvalidEmail() {
        user.setEmail("camiruiz@hotmail.com");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "email");
        assertTrue(violations.isEmpty());

        user.setEmail("invalid_email");
        violations = validator.validateProperty(user, "email");
        assertFalse(violations.isEmpty());
    }

    @Test
    public void directionMinAndMaxLength() {

        user.setDirection("Calle Falsa 123");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "direction");
        assertTrue(violations.isEmpty());
        //cumple con el minimo de 10

        user.setDirection("Calle");
        violations = validator.validateProperty(user, "direction");
        assertFalse(violations.isEmpty());
        //no cumple con el minimo de 10

        user.setDirection("Calle Falsa 12345678901234567890");
        violations = validator.validateProperty(user, "direction");
        assertFalse(violations.isEmpty());
        //se pasa del maximo de 30
    }

    @Test
    public void testPasswordValidation() {
        user.setPassword("Password123!");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertTrue(violations.isEmpty());
        // contraseña que cumple con los requisitos

        user.setPassword("A5!");
        violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty());
        // contraseña que no llega al min necesario

        user.setPassword("password123");
        violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty());
        // constraseña que no tiene caracteres especiales
    }

    @Test
    public void testCVUMinAndMaxLength() {

        user.setCvuMercadoPago("0123456789012345678901");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "cvuMercadoPago");
        assertTrue(violations.isEmpty());
        //cumple con 22 min/max

        user.setCvuMercadoPago("01234567890123456789012");
        violations = validator.validateProperty(user, "cvuMercadoPago");
        assertFalse(violations.isEmpty());
        //se pasa del max de 22

        user.setCvuMercadoPago("012345678901234567890");
        violations = validator.validateProperty(user, "cvuMercadoPago");
        assertFalse(violations.isEmpty());
        //no llega al min de 22
    }

    @Test
    public void testWalletCryptoMinAndMaxLength() {


        user.setWalletCrypto("12345678");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "walletCrypto");
        assertTrue(violations.isEmpty());
        //cumple con 8 min/max

        user.setWalletCrypto("123456789");
        violations = validator.validateProperty(user, "walletCrypto");
        assertFalse(violations.isEmpty());
        //se pasa del max de 8

        user.setWalletCrypto("1234567");
        violations = validator.validateProperty(user, "walletCrypto");
        assertFalse(violations.isEmpty());
        //no llega al min de 8
    }

    @Test
    void testEmptyTransactionsList() {
        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }


//    @Test
//    void testSingleSuccessfulOperation() {
//        Operation mockOperation = Mockito.mock(Operation.class);
//        Mockito.when(mockOperation.isSuccess()).thenReturn(true);
//        operationsList.add(mockOperation);
//        user.setOperationsList(operationsList);
//        double reputation = user.getReputation();
//        assertEquals(100.0, reputation);
//    }

    @Test
    void testSingleSuccessfulTransaction() {
        Transaction mockTransaction = Mockito.mock(Transaction.class);
        Mockito.when(mockTransaction.isSuccessful()).thenReturn(true);
        transactionsList.add(mockTransaction);
        user.setTransactionsList(transactionsList);
        double reputation = user.getReputation();
        assertEquals(100.0, reputation);
    }


//    @Test
//    void testSingleFailedOperation() {
//        Operation mockOperation = Mockito.mock(Operation.class);
//        Mockito.when(mockOperation.isSuccess()).thenReturn(false);
//        operationsList.add(mockOperation);
//        user.setOperationsList(operationsList);
//        double reputation = user.getReputation();
//        assertEquals(0.0, reputation);
//    }

    @Test
    void testSingleFailedTransaction() {
        Transaction mockTransaction = Mockito.mock(Transaction.class);
        Mockito.when(mockTransaction.isSuccessful()).thenReturn(false);
        transactionsList.add(mockTransaction);
        user.setTransactionsList(transactionsList);
        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }

//    @Test
//    void testMixedOperations() {
//        Operation mockSuccessfulOperation = Mockito.mock(Operation.class);
//        Mockito.when(mockSuccessfulOperation.isSuccess()).thenReturn(true);
//
//        Operation mockFailedOperation = Mockito.mock(Operation.class);
//        Mockito.when(mockFailedOperation.isSuccess()).thenReturn(false);
//
//        operationsList.add(mockSuccessfulOperation);
//        operationsList.add(mockFailedOperation);
//        operationsList.add(mockSuccessfulOperation);
//        user.setOperationsList(operationsList);
//
//        double reputation = user.getReputation();
//        assertEquals(66.66, reputation,0.1);
//    }

    @Test
    void testMixedTransactions() {
        Transaction mockSuccessfulTransaction = Mockito.mock(Transaction.class);
        Mockito.when(mockSuccessfulTransaction.isSuccessful()).thenReturn(true);

        Transaction mockFailedTransaction = Mockito.mock(Transaction.class);
        Mockito.when(mockFailedTransaction.isCancelled()).thenReturn(true);

        transactionsList.add(mockSuccessfulTransaction);
        transactionsList.add(mockFailedTransaction);
        transactionsList.add(mockSuccessfulTransaction);
        user.setTransactionsList(transactionsList);

        double reputation = user.getReputation();
        assertEquals(66.66, reputation,0.1);
    }
//
//    @Test
//    void testNoSuccessfulOperations() {
//        Operation mockFailedOperation1 = Mockito.mock(Operation.class);
//        Mockito.when(mockFailedOperation1.isSuccess()).thenReturn(false);
//
//        Operation mockFailedOperation2 = Mockito.mock(Operation.class);
//        Mockito.when(mockFailedOperation2.isSuccess()).thenReturn(false);
//
//        operationsList.add(mockFailedOperation1);
//        operationsList.add(mockFailedOperation2);
//        user.setOperationsList(operationsList);
//
//        double reputation = user.getReputation();
//        assertEquals(0.0, reputation);
//    }


    @Test
    void testNoSuccessfulTransactions() {
        Transaction mockFailedTransaction1 = Mockito.mock(Transaction.class);
        Mockito.when(mockFailedTransaction1.isSuccessful()).thenReturn(false);

        Transaction mockFailedTransaction2 = Mockito.mock(Transaction.class);
        Mockito.when(mockFailedTransaction2.isSuccessful()).thenReturn(false);

        transactionsList.add(mockFailedTransaction1);
        transactionsList.add(mockFailedTransaction2);
        user.setTransactionsList(transactionsList);

        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }

    @Test
    public void testUserCreation() {

        User user = new User(
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
                new ArrayList<>(),
                new ArrayList<>(),
                95.5
        );


        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("user@example.com", user.getEmail());

        User user2 =User.builder()
                .id(1L)
                .name("John")
                .lastName("Doe")
                .email("user@example.com")
                .direction("123 Main St")
                .password("Password123")
                .cvuMercadoPago("0123456789012345678901")
                .walletCrypto("12345678")
                .roles(new ArrayList<>())
                .intentionsList(new ArrayList<>())
                .operationsList(new ArrayList<>())
                .transactionsList(new ArrayList<>())
                .reputation(95.5)
                .build();

        assertEquals(user, user2);
        assertEquals(user.hashCode(), user2.hashCode());
    }

    @Test
    public void testWhenATransactionIsCancelledAndTheUserHasAnEmptyListOfTransactionsHisReputationIs0Points() {
        transaction.setStatus(Transaction.TransactionStatus.CANCELED);
        transaction.setCancelledBy(user);

        user.handleCancelledTransaction(transaction);

        assertEquals(0.0, user.findReputation(), 0.01);
    }

    @Test
    public void testWhenATransactionIsCancelledTheUserWhoCancelledItHisReputationIsLoweredBy20Points() {
        transaction.setStatus(Transaction.TransactionStatus.CANCELED);
        transactionSuccesful.setStatus(Transaction.TransactionStatus.CONFIRMED);
        transaction.setCancelledBy(user);
        // Le agrego una transaccion a la lista porque si su lista es vacia devuelve 0
        user.getTransactionsList().add(transactionSuccesful);

        user.handleCancelledTransaction(transaction);

        assertFalse(user.getTransactionsList().isEmpty());
        assertEquals(80.0, user.findReputation(), 0.01);
    }

    @Test
    public void testWhenATransactionIsCancelledTheUsersReputationCantGoBelow0Points() {
        transaction.setStatus(Transaction.TransactionStatus.CANCELED);
        transaction.setCancelledBy(user);
        // Le agrego una transaccion a la lista porque si su lista es vacia devuelve 0
        user.getTransactionsList().add(transaction);
        user.setReputation(5.0);

        user.handleCancelledTransaction(transaction);

        assertEquals(0.0, user.findReputation(), 0.01);
    }

    @Test
    public void testWhenATransactionIsNotCancelledByAnUserItsReputationIsNotAffected() {
        transaction.setStatus(Transaction.TransactionStatus.CANCELED);
        // Le agrego una transaccion a la lista porque si su lista es vacia devuelve 0
        user.getTransactionsList().add(transaction);
        user.setReputation(100.0);

        user.handleCancelledTransaction(transaction);

        assertEquals(100.0, user.findReputation(), 0.01);
    }
}
