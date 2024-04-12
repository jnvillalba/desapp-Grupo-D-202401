package ar.edu.unq.desapp.grupoD.backenddesappapi.service;

import ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions.BinancePriceFetchException;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CriptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.service.binance.BinanceAPIService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BinanceAPIServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testGetPriceOfCoinSymbol_PositiveCase() {
        BinanceAPIService binanceAPIService = new BinanceAPIService(restTemplate);
        String symbol = "BTCUSDT";
        String expectedUrl = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
        CriptoActive expectedResponse = new CriptoActive(symbol, 50000.00f, null);

        ResponseEntity<CriptoActive> responseEntity = ResponseEntity.ok(expectedResponse);
        when(restTemplate.getForEntity(expectedUrl, CriptoActive.class)).thenReturn(responseEntity);

        CriptoActive actualResponse = binanceAPIService.getPriceOfCoinSymbol(symbol);

        assertEquals(expectedResponse.getSymbol(), actualResponse.getSymbol());
        verify(restTemplate, times(1)).getForEntity(expectedUrl, CriptoActive.class);
    }

    @Test
    public void testGetPriceOfCoinSymbol_NegativeCase() {

        BinanceAPIService binanceAPIService = new BinanceAPIService(restTemplate);
        String symbol = "DAPPSUNQ";
        String expectedUrl = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
        ResponseEntity<CriptoActive> responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        when(restTemplate.getForEntity(expectedUrl, CriptoActive.class)).thenReturn(responseEntity);

        assertThrows(BinancePriceFetchException.class, () -> binanceAPIService.getPriceOfCoinSymbol(symbol));
        verify(restTemplate, times(1)).getForEntity(expectedUrl, CriptoActive.class);
    }
}
