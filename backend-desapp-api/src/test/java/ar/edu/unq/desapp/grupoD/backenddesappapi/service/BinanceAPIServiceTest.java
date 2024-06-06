package ar.edu.unq.desapp.grupod.backenddesappapi.service;
import ar.edu.unq.desapp.grupod.backenddesappapi.exceptions.BinancePriceFetchException;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.BinanceAPIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class BinanceAPIServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BinanceAPIService binanceAPIService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        binanceAPIService = new BinanceAPIService(restTemplate);
    }
    @Test
    void testGetPriceOfCoinSymbol() {
        BinancePriceDTO mockPriceDTO = new BinancePriceDTO("BTC",100f,LocalDateTime.now());
        ResponseEntity<BinancePriceDTO> mockResponseEntity = ResponseEntity.ok(mockPriceDTO);

        when(restTemplate.getForEntity(anyString(), eq(BinancePriceDTO.class))).thenReturn(mockResponseEntity);

        BinancePriceDTO actualPriceDTO = binanceAPIService.getPriceOfCoinSymbol("BTC");

        assertEquals(100.0f, actualPriceDTO.getPrice());
    }

    @Test
    void testGetPricesOfCoins_Success() {
        String symbol = "BTCUSDT";
        BinancePriceDTO mockPrice = new BinancePriceDTO(symbol, 45000.0f, LocalDateTime.now());
        when(restTemplate.getForEntity(anyString(), eq(BinancePriceDTO.class)))
                .thenReturn(new ResponseEntity<>(mockPrice, HttpStatus.OK));

        List<BinancePriceDTO> result = binanceAPIService.getPricesOfCoins();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(14, result.size());
    }

    @Test
    void testLast24HrsPrices_Success() {
        String symbol = "BTCUSDT";
        String[][] mockResponse = new String[][]{
                {"", "", "", "", "45000.0", "", String.valueOf(System.currentTimeMillis())}
        };
        when(restTemplate.getForObject(anyString(), eq(String[][].class)))
                .thenReturn(mockResponse);

        List<BinancePriceDTO> result = binanceAPIService.last24HrsPrices(symbol);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(symbol, result.get(0).getSymbol());
        assertEquals(45000.0f, result.get(0).getPrice());
    }

    @Test
    void testLast24HrsPrices_Failure() {
        String symbol = "BTCUSDT";
        when(restTemplate.getForObject(anyString(), eq(String[][].class)))
                .thenReturn(null);

        assertThrows(BinancePriceFetchException.class, () -> binanceAPIService.last24HrsPrices(symbol));
    }
}
