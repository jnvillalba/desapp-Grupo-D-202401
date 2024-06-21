package ar.edu.unq.desapp.grupod.backenddesappapi.exceptions;

public class BinancePriceFetchException extends RuntimeException {
    public BinancePriceFetchException(String symbol, int statusCode) {
        super("Failed to get price for symbol " + symbol + " from Binance API. Status code: " + statusCode);
    }

    public BinancePriceFetchException(String symbol) {
        super("Failed to fetch data from Binance API for symbol " + symbol + " from Binance API.");
    }
}

