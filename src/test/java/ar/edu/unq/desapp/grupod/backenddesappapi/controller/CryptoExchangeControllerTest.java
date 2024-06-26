package ar.edu.unq.desapp.grupod.backenddesappapi.controller;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.BinanceAPIService;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.IntentionService;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.TransactionService;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.UserService;
import ar.edu.unq.desapp.grupod.backenddesappapi.webservice.CryptoExchangeController;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.cache.CacheManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext
public class CryptoExchangeControllerTest {
    @Autowired
    private CryptoExchangeController cryptoExchangeController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BinanceAPIService binanceAPIService;
    @SpyBean
    private UserService userService;

    @MockBean
    private TransactionService transactionService;

    @SpyBean
    private IntentionService intentionService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void clearCache() {
        cacheManager.getCache("cryptoCache").clear();
    }

    @Test
    void testGetCryptoCurrencyValue() throws Exception {
        BinancePriceDTO priceDTO = new BinancePriceDTO("BTC", 50000.0F, LocalDateTime.now());
        when(binanceAPIService.getPriceOfCoinSymbol("BTC")).thenReturn(priceDTO);

        mvc.perform(get("/api/crypto/crypto/BTC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("BTC"))
                .andExpect(jsonPath("$.price").value(50000.0));
    }

    @Test
    void testGetPricesOfCoins() throws Exception {
        BinancePriceDTO price1 = new BinancePriceDTO("BTC", 50000.0F, LocalDateTime.now());
        BinancePriceDTO price2 = new BinancePriceDTO("ETH", 3000.0F, LocalDateTime.now());
        List<BinancePriceDTO> prices = Arrays.asList(price1, price2);

        when(binanceAPIService.getPricesOfCoins()).thenReturn(prices);

        mvc.perform(get("/api/crypto/crypto/prices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("BTC"))
                .andExpect(jsonPath("$[0].price").value(50000.0))
                .andExpect(jsonPath("$[1].symbol").value("ETH"))
                .andExpect(jsonPath("$[1].price").value(3000.0));
    }

    @Test
    void testProcessTransaction() throws Exception {
        ProcessTransactionDTO trx = new ProcessTransactionDTO();

        Operation operation = new Operation();
        operation.setStatus(Operation.TransactionStatus.CONFIRMED);

        when(transactionService.processTransaction(any(ProcessTransactionDTO.class)))
                .thenReturn(operation);

        mvc.perform(post("/api/crypto/operation/processTransaction")
                        .content(asJsonString(trx))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Operation.TransactionStatus.CONFIRMED.toString()));
    }

    @Test
    void expressIntention() throws Exception {
        ExpressIntentionDTO expressIntentionDTO = new ExpressIntentionDTO();
        expressIntentionDTO.setUserId(1L);
        expressIntentionDTO.setOperationType(OperationType.BUY);
        expressIntentionDTO.setActiveId(1L);
        expressIntentionDTO.setPesosAmount(2);
        mvc.perform(
                        post("/api/crypto/intention")
                                .content(asJsonString(expressIntentionDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("Intention of BUY expressed successfully"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllIntentions() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        IntentionDTO intention1 = new IntentionDTO(1L,
                now,
                1L,
                OperationType.BUY,
                1L,
                1000D);

        IntentionDTO intention2 = new IntentionDTO(2L,
                now,
                1L,
                OperationType.SELL,
                1L,
                1000D);

        List<IntentionDTO> intentions = Arrays.asList(intention1, intention2);
        when(intentionService.getAllIntentions()).thenReturn(intentions);

        mvc.perform(get("/api/crypto/intentions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].intentionId").value(1L))
                .andExpect(jsonPath("$[0].creationDateTime").value(formattedNow))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].operationType").value("BUY"))
                .andExpect(jsonPath("$[0].cryptoActiveId").value(1L))
                .andExpect(jsonPath("$[0].pesosAmount").value(1000D))
                .andExpect(jsonPath("$[1].intentionId").value(2L))
                .andExpect(jsonPath("$[1].creationDateTime").value(formattedNow))
                .andExpect(jsonPath("$[1].userId").value(1L))
                .andExpect(jsonPath("$[1].operationType").value("SELL"))
                .andExpect(jsonPath("$[1].cryptoActiveId").value(1L))
                .andExpect(jsonPath("$[1].pesosAmount").value(1000D));
    }
    @Test
    void testLast24HrsPrices() throws Exception {
        String symbol = "BTCUSDT";
        BinancePriceDTO priceDTO = new BinancePriceDTO(symbol, 50000.0F, LocalDateTime.now());
        List<BinancePriceDTO> prices = Collections.singletonList(priceDTO);
        Mockito.when(binanceAPIService.last24HrsPrices(symbol)).thenReturn(prices);

        mvc.perform(get("/api/crypto/crypto/last24HrsPrices/" + symbol))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value(symbol))
                .andExpect(jsonPath("$[0].price").value(50000.0));
    }

    @Test
    void testLast10MinPrices() throws Exception {
        String symbol = "BTCUSDT";
        BinancePriceDTO priceDTO = new BinancePriceDTO(symbol, 50000.0F, LocalDateTime.now());
        List<BinancePriceDTO> prices = Collections.singletonList(priceDTO);
        Mockito.when(binanceAPIService.last10MinPrices(symbol)).thenReturn(prices);

        mvc.perform(get("/api/crypto/crypto/last10MinPrices/" + symbol))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value(symbol))
                .andExpect(jsonPath("$[0].price").value(50000.0));
    }
}
