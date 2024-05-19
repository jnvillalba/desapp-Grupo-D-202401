package ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionsTests {
    @Test
    void testConstructor() {
        Long id = 123L;
        UserNotFoundException exception = new UserNotFoundException(id);
        String expectedMessage = "User with ID: " + id + " not found";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

}
