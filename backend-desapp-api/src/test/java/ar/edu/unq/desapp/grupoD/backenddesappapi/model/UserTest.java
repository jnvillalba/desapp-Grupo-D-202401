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
    @BeforeEach
    void setUp() {
        user = new User();
        operationsList = new ArrayList<>();
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
    void testEmptyOperationsList() {
        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }


    @Test
    void testSingleSuccessfulOperation() {
        Operation mockOperation = Mockito.mock(Operation.class);
        Mockito.when(mockOperation.isSuccess()).thenReturn(true);
        operationsList.add(mockOperation);
        user.setOperationsList(operationsList);
        double reputation = user.getReputation();
        assertEquals(100.0, reputation);
    }

    @Test
    void testSingleFailedOperation() {
        Operation mockOperation = Mockito.mock(Operation.class);
        Mockito.when(mockOperation.isSuccess()).thenReturn(false);
        operationsList.add(mockOperation);
        user.setOperationsList(operationsList);
        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }

    @Test
    void testMixedOperations() {
        Operation mockSuccessfulOperation = Mockito.mock(Operation.class);
        Mockito.when(mockSuccessfulOperation.isSuccess()).thenReturn(true);

        Operation mockFailedOperation = Mockito.mock(Operation.class);
        Mockito.when(mockFailedOperation.isSuccess()).thenReturn(false);

        operationsList.add(mockSuccessfulOperation);
        operationsList.add(mockFailedOperation);
        operationsList.add(mockSuccessfulOperation);
        user.setOperationsList(operationsList);

        double reputation = user.getReputation();
        assertEquals(66.66, reputation);
    }

    @Test
    void testNoSuccessfulOperations() {
        Operation mockFailedOperation1 = Mockito.mock(Operation.class);
        Mockito.when(mockFailedOperation1.isSuccess()).thenReturn(false);

        Operation mockFailedOperation2 = Mockito.mock(Operation.class);
        Mockito.when(mockFailedOperation2.isSuccess()).thenReturn(false);

        operationsList.add(mockFailedOperation1);
        operationsList.add(mockFailedOperation2);
        user.setOperationsList(operationsList);

        double reputation = user.getReputation();
        assertEquals(0.0, reputation);
    }
}
