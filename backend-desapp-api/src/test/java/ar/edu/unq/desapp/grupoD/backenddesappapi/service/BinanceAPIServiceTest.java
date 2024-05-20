package ar.edu.unq.desapp.grupoD.backenddesappapi.service;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.BinanceAPIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
