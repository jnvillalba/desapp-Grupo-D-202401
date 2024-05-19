package ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionsTests {
    @Test
    void testConstructor() {
        Long id = 123L;
        UserNotFoundException exception = new UserNotFoundException(id);
        String expectedMessage = "User with ID: " + id + " not found";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testConstructorWithSymbolAndStatusCode() {
        String symbol = "BTC";
        int statusCode = 404;
        BinancePriceFetchException exception = new BinancePriceFetchException(symbol, statusCode);
        assertEquals("Failed to get price for symbol BTC from Binance API. Status code: 404", exception.getMessage());
    }

    @Test
    void testConstructorWithSymbol() {
        String symbol = "ETH";
        BinancePriceFetchException exception = new BinancePriceFetchException(symbol);
        assertEquals("Failed to fetch data from Binance API for symbol ETH from Binance API.", exception.getMessage());
    }

    @Test
    void testConstructorOperationNotFoundException() {
        Long id = 123L;
        OperationNotFoundException exception = new OperationNotFoundException(id);
        String expectedMessage = "Operation with ID: " + id + " not found";
        assertEquals(expectedMessage, exception.getMessage());
    }

}
