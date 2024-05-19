package ar.edu.unq.desapp.grupoD.backenddesappapi.service;

import ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions.BinancePriceFetchException;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.BinanceAPIService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class BinanceAPIServiceTest {
    //TODO: arreglar
    @Mock
    private RestTemplate restTemplate;

//    @Test
//     void testGetPriceOfCoinSymbol_PositiveCase() {
//        BinanceAPIService binanceAPIService = new BinanceAPIService(restTemplate);
//        String symbol = "BTCUSDT";
//        String expectedUrl = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
//        CryptoActive expectedResponse = CryptoActive.builder()
//                .symbol("BTCUSDT")
//                .price(50000f)
//                .amount(0.5)
//                .lastUpdateDateAndTime(LocalDateTime.now())
//                .build();
//
//        ResponseEntity<CryptoActive> responseEntity = ResponseEntity.ok(expectedResponse);
//        when(restTemplate.getForEntity(expectedUrl, CryptoActive.class)).thenReturn(responseEntity);
//
//        BinancePriceDTO actualResponse = binanceAPIService.getPriceOfCoinSymbol(symbol);
//
//        assertEquals(expectedResponse.getSymbol(), actualResponse.getSymbol());
//        verify(restTemplate, times(1)).getForEntity(expectedUrl, CryptoActive.class);
//    }

//    @Test
//    void testGetPriceOfCoinSymbol_NegativeCase() {
//        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
//        BinanceAPIService binanceAPIService = new BinanceAPIService(restTemplate);
//        String symbol = "DAPPSUNQ";
//        String expectedUrl = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
//
//        CryptoActive cryptoActive = new CryptoActive();
//        ResponseEntity<CryptoActive> responseEntity = ResponseEntity.ok(cryptoActive);
//        Mockito.when(restTemplate.getForEntity(expectedUrl, CryptoActive.class)).thenReturn(responseEntity);
//
//        assertThrows(BinancePriceFetchException.class, () -> binanceAPIService.getPriceOfCoinSymbol(symbol));
//
//        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(expectedUrl, CryptoActive.class);
//    }

}
